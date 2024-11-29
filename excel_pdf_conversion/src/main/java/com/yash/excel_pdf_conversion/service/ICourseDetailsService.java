package com.yash.excel_pdf_conversion.service;

import com.yash.excel_pdf_conversion.entity.CourseDetails;
import com.yash.excel_pdf_conversion.model.SearchInputs;
import com.yash.excel_pdf_conversion.model.SearchResults;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ICourseDetailsService {

    public Set<String> showAllCourseCategories();
    public Set<String> showAllCourseMode();
    public Set<String> showAllFaculties();
    public List<SearchResults> showCoursesByFilters(SearchInputs inputs);
    public void generatePdfReport(SearchInputs inputs, HttpServletResponse res) throws IOException;
    public void generateExcelReport(SearchInputs inputs, HttpServletResponse res) throws IOException;
}
