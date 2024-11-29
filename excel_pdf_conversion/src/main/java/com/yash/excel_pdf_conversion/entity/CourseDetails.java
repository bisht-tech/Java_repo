package com.yash.excel_pdf_conversion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="COURSE_DETAILS")
public class CourseDetails {
    @Id
    @GeneratedValue
    private Integer courseId;
    private String courseName;
    private String location;
    private String courseCategory;
    private String facultyName;
    private Double fee;
    private String adminName;
    private Long adminContact;
    private String courseMode;
    private LocalDateTime startDate;
    private String courseStatus;
    private LocalDateTime creationDate;
    private LocalDateTime updationDate;
    private String createdBy;
    private String updatedBy;


}
