/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/
package com.wavemaker.sampleapps.wavekart.invoicereportservice;

import java.io.*;
import java.sql.Connection;
import java.util.*;

import javax.annotation.*;
import javax.sql.DataSource;

import org.slf4j.Logger;                        
import org.slf4j.LoggerFactory;                     

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.commons.io.IOUtils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.wavemaker.runtime.*;   
import com.wavemaker.runtime.file.model.*;
import com.wavemaker.runtime.security.SecurityService;   
import com.wavemaker.runtime.service.annotations.ExposeToClient; 

//import com.wavemaker.sampleapps.wavekart.invoicereportservice.model.*;

/**
 * This is a singleton class wit h all its public methods exposed as REST APIs via generated controller class.
 * To avoid exposing an API for a particular public method, annotate it with @HideFromClient.
 *
 * Method names will play a major role in defining the Http Method for the generated APIs. For example, a method name
 * that starts with delete/remove, will make the API exposed as Http Method "DELETE".
 *
 * Method Parameters of type primitives (including java.lang.String) will be exposed as Query Parameters &
 * Complex Types/Objects will become part of the Request body in the generated API.
 */
@ExposeToClient
public class InvoiceReportService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceReportService.class);

    @Autowired
    private SecurityService securityService;

    /**
     * This is sample java operation that acc epts an input from the caller and responds with "Hello".
     *
     * SecurityService that is Autowired will provide access to the security context of the caller. It has methods like isAuthenticated(),
     * getUserName() and getUserId() etc which returns the information based on the caller context.
     *
     * Methods in this class can declare HttpServletRequest, HttpServletResponse as input parameters to access the
     * caller's request/response objects respectively. These parameters will be injected when request is made (during API invocation).
     */
    @Autowired
    @Qualifier("eshoppingDataSource")
    private DataSource datasource;
    
    private JasperReport jasperReport;
    
    @PostConstruct
    private void init() {
        InputStream jrxmlInput = null;
        try {
            jrxmlInput = this.getClass().getClassLoader().getResource("OrderInvoice.jrxml").openStream();
            JasperDesign design = JRXmlLoader.load(jrxmlInput);
            jasperReport = JasperCompileManager.compileReport(design);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(jrxmlInput);
        }
    }
    
    public DownloadResponse generatePdfReport(int orderId)
    {
        Connection conn = null;
        try 
        { 
            conn = datasource.getConnection();
            logger.info("Connection:" + conn);
      
            Map parametersMap = new HashMap();  
            parametersMap.put("orderId",orderId);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap, conn);
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
      
            DownloadResponse response = new DownloadResponse(new ByteArrayInputStream(pdfBytes), "application/pdf", "invoice_"+orderId+".pdf");
            
            logger.info("Completed Successfully: ");
            return response;
        } catch (Exception e)
        {
            logger.info("Error: ", e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                conn.close();
            } catch(Exception ignore) {}
        }
    }


}
