package com.rclgroup.dolphin.creation.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rclgroup.dolphin.creation.dto.JasperMainModel;
import com.rclgroup.dolphin.creation.service.JasperService;
import com.rclgroup.dolphin.utils.Constants;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/jasper")
public class JasperController {
	
	private static final Logger log = LoggerFactory.getLogger(JasperController.class);
	
	@Autowired
	private JasperService jasperService;
	
	@PostMapping(value = "/jasperpdf/export", produces = "application/pdf", consumes = "application/json")
	public ResponseEntity<String> createPDF(@RequestBody JasperMainModel request,HttpServletResponse response) throws IOException, JRException, SQLException {
	    try {
	    	
	        log.info("Entry into ApiController JasperReport for ReportName : " + request.getReportName());
	        byte[] reportData = jasperService.exportJasperReport(request);
	        String base64EncodedReport = Base64.getEncoder().encodeToString(reportData);
	        log.info("Exit from ApiController JasperReport for ReportName : " + request.getReportName());
	        return ResponseEntity.ok(base64EncodedReport);
	        
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
	    	log.info("JasperController :: Report Generated from old code");
	    	log.info("JasperReport Exception in Controller for ReportName : " + request.getReportName() + " Error "+ e.getMessage());
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.JASPER_COMPILATION_ERROR);
	    }
	}

}