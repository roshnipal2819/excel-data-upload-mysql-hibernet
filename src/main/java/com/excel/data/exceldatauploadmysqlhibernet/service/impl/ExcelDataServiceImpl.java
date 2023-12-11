package com.excel.data.exceldatauploadmysqlhibernet.service.impl;

import com.excel.data.exceldatauploadmysqlhibernet.dao.ExcelDataServiceDao;
import com.excel.data.exceldatauploadmysqlhibernet.model.ExcelData;
import com.excel.data.exceldatauploadmysqlhibernet.service.ExcelDataService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class ExcelDataServiceImpl implements ExcelDataService {


    @Autowired
    ExcelDataServiceDao excelDataServiceDao;

    @Override
    public void save(MultipartFile file) throws IOException {
        byte[] byteArray = toByteArray(file.getInputStream());
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        try (Workbook workbook = getWorkbook(file.getOriginalFilename(),inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue;
                }
                ExcelData excelData = new ExcelData();
                Map<String, String> excelDataProperties = new HashMap<>();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 0) {
                        excelDataProperties.put(printCellValue(cell), "");
                    } else {
                        printCellValue(cell);
                        excelDataProperties.put(printCellValue(sheet.getRow(0).getCell(cell.getColumnIndex())),
                                printCellValue(cell));
                    }
                }
                excelData.setExcelDataProperties(excelDataProperties);
                excelDataServiceDao.save(excelData);
            }
        }catch (Exception e){
            System.out.println("Service"+e.getMessage());
        }
    }

    @Override
    public List<Map<String,String>> getAllData() {
        List<Map<String,String>> excelDataMap = new ArrayList<>();
        List<ExcelData> excelData = excelDataServiceDao.findAll();
        for (ExcelData e: excelData) {
            Map<String, String> excelDataRecords = e.getExcelDataProperties();
           // excelDataRecords.put("recods",String.valueOf(e.getId()));
            excelDataMap.add(excelDataRecords);
        }
        return excelDataMap;
    }

    public static byte[] toByteArray(InputStream is) throws IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        }catch (Exception e){
            System.out.println("Exetio "+e.getMessage());
             throw new RuntimeException(e.getLocalizedMessage());

        }
        finally {
            output.close();
        }

    }

    public static String printCellValue(Cell cell) {
        CellType cellType = cell.getCellType().equals(CellType.FORMULA)
                ? cell.getCachedFormulaResultType() : cell.getCellType();
        if (cellType.equals(CellType.STRING)) {
            return String.valueOf(cell.getStringCellValue());
        }
        if (cellType.equals(CellType.NUMERIC)) {
            if (DateUtil.isCellDateFormatted(cell)) {
               return String.valueOf(cell.getDateCellValue());
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        if (cellType.equals(CellType.BOOLEAN)) {
            return String.valueOf(cell.getBooleanCellValue());
        }
        return null;
    }

    public static Workbook getWorkbook(String fileExtensionName,InputStream inputStream) throws IOException, InvalidFormatException {
        if (fileExtensionName.endsWith(".xlsx")) {
           return new XSSFWorkbook(inputStream);
        }
        else if (fileExtensionName.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        }
        return new XSSFWorkbook(inputStream);
    }
}
