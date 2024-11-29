package com.yash.excel_pdf_conversion.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.yash.excel_pdf_conversion.entity.CourseDetails;
import com.yash.excel_pdf_conversion.model.SearchInputs;
import com.yash.excel_pdf_conversion.model.SearchResults;
import com.yash.excel_pdf_conversion.repository.ICourseDetailsRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.microsoft.schemas.vml.STStrokeJoinStyle.Enum.table;

@Service
public class ICourseDetailsServiceImpl implements ICourseDetailsService {
    @Autowired
    private ICourseDetailsRepository courseDetailsRepo;

    @Override
    public Set<String> showAllCourseCategories() {
        return courseDetailsRepo.getDistinctCourseCategories();
    }

    @Override
    public Set<String> showAllCourseMode() {
        return courseDetailsRepo.getDistinctTrainingModes();
    }

    @Override
    public Set<String> showAllFaculties() {
        return courseDetailsRepo.getDistinctFacultyNames();
    }

    @Override
    public List<SearchResults> showCoursesByFilters(SearchInputs inputs) {
        CourseDetails filteredEntity = new CourseDetails();
        String category = inputs.getCourseCategories();
        if(category!=null && !category.equals(" ") && !category.isEmpty()){
               filteredEntity.setCourseCategory(category);
        }
        String facultyName = inputs.getFacultyName();
        if(facultyName!=null && !facultyName.equals(" ") && !facultyName.isEmpty()){
            filteredEntity.setFacultyName(facultyName);
        }
        String courseMode = inputs.getCourseMode();
        if(courseMode!=null && !courseMode.equals(" ") && !courseMode.isEmpty()){
            filteredEntity.setCourseMode(courseMode);
        }
        LocalDateTime dateTime = inputs.getStartsOn();
        if(dateTime!=null){
            filteredEntity.setStartDate(dateTime);
        }
        Example<CourseDetails> example = Example.of(filteredEntity);

        List<CourseDetails> listEntities = courseDetailsRepo.findAll(example);
        List<SearchResults> listSearchEntities = new ArrayList<>();
        listEntities.forEach(course->{
            SearchResults result =new SearchResults();
            BeanUtils.copyProperties(course,result);
            listSearchEntities.add(result);
        });
        return listSearchEntities;
    }

    @Override
   public void generatePdfReport(SearchInputs inputs, HttpServletResponse res) throws IOException {
//        List<SearchResults> listResults = showCoursesByFilters(inputs);
//        Document document =new Document(PageSize.A4);
//        PdfWriter.getInstance(document,res.getOutputStream());
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
//        font.setSize(30);
//        font.setColor(Color.BLUE);
//
//        Paragraph para=new Paragraph("Search Report",font);
//        para.setAlignment(Paragraph.ALIGN_CENTER);
//        document.add(para);
//
//        // prepare heading row cells in the pdf table
//        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(Color.gray);
//        cell.setPadding(5);
//        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        cellFont.setColor(Color.BLACK);
//
//        cell.setPhrase(new Phrase("courseID", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("courseName", cellFont));
//        table.(cell);
//        cell.setPhrase(new Phrase("Category", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("facultyName", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("Location", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("fee", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("Course Status", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("CourseMode", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("adminContant", cellFont));
//        table.clone(cell);
//        cell.setPhrase(new Phrase("StartDate", cellFont));
//        table.clone(cell);
//
//
//        // add data cells to pdftable
//        listResults.forEach(result -> {
//            table.clone(String.valueOf(result.getCourseId()));
//            table.clone(result.getCourseName());
//            table.clone(result.getCourseCategory());
//            table.clone(result.getFacultyName());
//            table.clone(result.getLocation());
//            table.clone(String.valueOf(result.getFee()));
//            table.clone(result.getCourseStatus());
//            table.clone(result.getCourseMode());
//            table.clone(String.valueOf(result.getAdminContact()));
//            table.clone(result.getStartDate().toString());
//        });
//
//// add table to document
//        document.add(table);
//// close the document
//        document.close();
//
//
//
   }

    @Override
    public void generateExcelReport(SearchInputs inputs, HttpServletResponse res) throws IOException {
        //get the SearchResult
        List<SearchResults> listResults = showCoursesByFilters(inputs);

//Create Excel Workbook (apache poi api)
        HSSFWorkbook workbook = new HSSFWorkbook();
//create Sheet in the workbook
        HSSFSheet sheet1 = workbook.createSheet("CourseDetails");

//create Heading Row in sheet1
        HSSFRow headerRow = sheet1.createRow(0);
        headerRow.createCell(0).setCellValue("CourseID");
        headerRow.createCell(1).setCellValue("CourseName");
        headerRow.createCell(2).setCellValue("Location");
        headerRow.createCell(3).setCellValue("CourseCategory");
        headerRow.createCell(4).setCellValue("FacultyName");
        headerRow.createCell(5).setCellValue("Fee");
        headerRow.createCell(6).setCellValue("AdminContact");
        headerRow.createCell(7).setCellValue("CourseMode");
        headerRow.createCell(8).setCellValue("StartDate");
        headerRow.createCell(9).setCellValue("CourseStatus");

//add data rows to the sheet
        int i = 1;
        for (SearchResults result : listResults) {
            HSSFRow dataRow = sheet1.createRow(i);
            dataRow.createCell(0).setCellValue(result.getCourseId());
            dataRow.createCell(1).setCellValue(result.getCourseName());
            dataRow.createCell(2).setCellValue(result.getLocation());
            dataRow.createCell(3).setCellValue(result.getCourseCategory());
            dataRow.createCell(4).setCellValue(result.getFacultyName());
            dataRow.createCell(5).setCellValue(result.getFee());
            dataRow.createCell(6).setCellValue(result.getAdminContact());
            dataRow.createCell(7).setCellValue(result.getCourseMode());
            dataRow.createCell(8).setCellValue(result.getStartDate());
            dataRow.createCell(9).setCellValue(result.getCourseStatus());
            i++;
        }

//get OutputStream pointing to response object
        ServletOutputStream outputStream = res.getOutputStream();
//write the Excel workbook data to response object using the above stream
        workbook.write(outputStream);
//close the stream
        outputStream.close();
        workbook.close();


    }
}
