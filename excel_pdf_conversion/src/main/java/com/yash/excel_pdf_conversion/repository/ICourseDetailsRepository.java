package com.yash.excel_pdf_conversion.repository;

import com.yash.excel_pdf_conversion.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ICourseDetailsRepository extends JpaRepository<CourseDetails,Integer> {
    @Query("select distinct(courseCategory) from CourseDetails")
    public Set<String> getDistinctCourseCategories();
    @Query("select distinct(facultyName) from CourseDetails")
    public Set<String> getDistinctFacultyNames();
    @Query("select distinct(courseMode) from CourseDetails")
    public Set<String> getDistinctCourseModes();
}
