package com.excel.data.exceldatauploadmysqlhibernet.dao;

import com.excel.data.exceldatauploadmysqlhibernet.model.ExcelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelDataServiceDao extends JpaRepository<ExcelData,Long> {
}
