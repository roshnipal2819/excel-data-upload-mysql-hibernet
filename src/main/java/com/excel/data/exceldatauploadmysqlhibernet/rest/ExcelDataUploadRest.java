package com.excel.data.exceldatauploadmysqlhibernet.rest;


import com.excel.data.exceldatauploadmysqlhibernet.Wrapper.ResponseMessage;
import com.excel.data.exceldatauploadmysqlhibernet.model.ExcelData;
import com.excel.data.exceldatauploadmysqlhibernet.service.ExcelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
public class ExcelDataUploadRest {

    private @Autowired
    ExcelDataService excelDataService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
            try {
                System.out.println("File upload started ");
                excelDataService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                System.out.println("Could not upload the file"+e.getMessage());
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
    }

    @GetMapping("/getAllData")
    public ResponseEntity<List<Map<String,String>>> getAllData() {
            try {
                List<Map<String,String>> excelData= excelDataService.getAllData();
                return ResponseEntity.status(HttpStatus.OK).body(excelData);
            } catch (Exception e) {
                System.out.println("Error while getting data"+e.getMessage());
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body((List<Map<String, String>>) new HashMap<>());
            }
        }
}
