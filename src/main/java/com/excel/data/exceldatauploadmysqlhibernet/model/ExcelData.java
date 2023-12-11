package com.excel.data.exceldatauploadmysqlhibernet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="excel_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelData {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "excel_data_properties", joinColumns = @JoinColumn(name = "excel_data_id"))
    @MapKeyColumn(name = "property_name")
    @Column(name = "property_value")
    private Map<String, String> excelDataProperties = new HashMap<>();
}
