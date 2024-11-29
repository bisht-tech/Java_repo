package com.yash.excel_pdf_conversion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInputs {
    private String courseCategories;
    private String courseMode;
    private String facultyName;
    private LocalDateTime startsOn;

}
