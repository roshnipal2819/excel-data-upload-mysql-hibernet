package com.excel.data.exceldatauploadmysqlhibernet.utils;




import org.springframework.web.multipart.MultipartFile;



public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


  public static boolean hasExcelFormat(MultipartFile file) {

    return true;
  }


}