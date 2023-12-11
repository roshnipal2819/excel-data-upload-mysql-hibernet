package com.excel.data.exceldatauploadmysqlhibernet.service;

import com.excel.data.exceldatauploadmysqlhibernet.model.ExcelData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExcelDataService {
   void save(MultipartFile file) throws IOException;

   List<Map<String,String>> getAllData();
}
