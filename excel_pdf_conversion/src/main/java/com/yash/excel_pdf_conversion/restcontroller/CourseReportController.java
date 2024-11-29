package com.yash.excel_pdf_conversion.restcontroller;

import com.yash.excel_pdf_conversion.model.SearchInputs;
import com.yash.excel_pdf_conversion.model.SearchResults;
import com.yash.excel_pdf_conversion.service.ICourseDetailsService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reporting/api")
public class CourseReportController {
    @Autowired
    private ICourseDetailsService courseService;

    @GetMapping("/courses")
    public ResponseEntity<?> fetchCourseCategories() {
        try {
            // use service
            Set<String> coursesInfo = courseService.showAllCourseCategories();
            return new ResponseEntity<Set<String>>(coursesInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course-modes")
    public ResponseEntity<?> fetchCourseModes() {
        try {
            // use service
            Set<String> trainingModeInfo = courseService.showAllCourseMode();
            return new ResponseEntity<Set<String>>(trainingModeInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/faculties")
    public ResponseEntity<?> fetchFaculties(){
        try {
            // Use service
            Set<String> facultiesInfo = courseService.showAllFaculties();
            return new ResponseEntity<Set<String>>(facultiesInfo, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/search")
    public ResponseEntity<?> fetchCoursesByFilters(@RequestBody SearchInputs inputs){
        try {
            // Use service
            List<SearchResults> list = courseService.showCoursesByFilters(inputs);
            return new ResponseEntity<List<SearchResults>>(list, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pdf-report")
    public void showPdfReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
        try {
            // Set the response content type
            res.setContentType("application/pdf");
            // Set the content-disposition header to response content going to browser as downloadable file
            res.setHeader("Content-Disposition", "attachment;fileName=courses.pdf");
            // Use service
            courseService.generatePdfReport(inputs, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/excel-report")
    public void showExcelReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
        try {
            // Set the response content type
            res.setContentType("application/vnd.ms-excel");
            // Set the content-disposition header to response content going to browser as downloadable file
            res.setHeader("Content-Disposition", "attachment;fileName=courses.xls");
            // Use the service
            courseService.generateExcelReport(inputs, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
