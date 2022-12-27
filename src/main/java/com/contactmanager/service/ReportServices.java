package com.contactmanager.service;


import com.contactmanager.model.Contact;
import com.contactmanager.repository.ContactRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServices {

    @Autowired
    private ContactRepository contactRepository;

    public String exportReport(String format) throws FileNotFoundException, JRException {
        List<Contact> contactList = contactRepository.findAll();

        String path = "C:\\Users\\User\\OneDrive\\Desktop\\Others\\thesis";

        File file = ResourceUtils.getFile("classpath:reports/report.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(contactList);

        Map<String, Object> map = new HashMap<>();
        map.put("createdBy", "ABC");


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,dataSource);

        if (format.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\report.html");

        }else if(format.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\report.pdf");
        }

        return null;


    }


}
