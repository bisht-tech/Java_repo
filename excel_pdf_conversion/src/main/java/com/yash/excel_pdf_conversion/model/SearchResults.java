package com.yash.excel_pdf_conversion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResults {
    private Integer courseId;
    private String courseName;
    private String location;
    private String courseCategory;
    private String facultyName;
    private double fee;
    private Long adminContact;
    private String CourseMode;
    private LocalDateTime startDate;
    private String courseStatus;

}
