create database excel_data_db;

CREATE TABLE excel_data (id BIGINT AUTO_INCREMENT PRIMARY KEY);

CREATE TABLE excel_data_properties (
    excel_data_id BIGINT,
    property_name VARCHAR(255),
    property_value VARCHAR(255),
    PRIMARY KEY (excel_data_id, PROPERTY_NAME),
    FOREIGN KEY (excel_data_id) REFERENCES excel_data(id)
);
