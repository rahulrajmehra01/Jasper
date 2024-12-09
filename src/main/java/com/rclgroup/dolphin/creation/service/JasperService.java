package com.rclgroup.dolphin.creation.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rclgroup.dolphin.creation.controller.JasperController;
import com.rclgroup.dolphin.creation.dto.JasperMainModel;
import com.rclgroup.dolphin.creation.repository.JasperIJSRepository;
import com.rclgroup.dolphin.creation.repository.JasperIPVRepository;
import com.rclgroup.dolphin.creation.repository.JasperMNRRepository;
import com.rclgroup.dolphin.creation.repository.JasperSTRRepository;
import com.rclgroup.dolphin.creation.repository.JasperSYSRepository;
import com.rclgroup.dolphin.creation.repository.JasperTOSRepository;
import com.rclgroup.dolphin.creation.repository.JasperVMSRepository;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.HtmlExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Service
public class JasperService {

	@Autowired
	private JasperSTRRepository jasperSTRRepository;
	
	@Autowired
	private JasperMNRRepository jasperMNRRepository;
	
	@Autowired
	private JasperIJSRepository jasperIJSRepository;
	
	@Autowired
	private JasperVMSRepository jasperVMSRepository;
	
	@Autowired
	private JasperSYSRepository jasperSYSRepository;
	
	@Autowired
	private JasperTOSRepository jasperTOSRepository;
	
	@Autowired
	private JasperIPVRepository jasperIPVRepository;

	private static final Logger log = LoggerFactory.getLogger(JasperController.class);

	@SuppressWarnings("unchecked")
	public byte[] exportJasperReport(JasperMainModel request) throws JRException, IOException, SQLException, IllegalArgumentException, IllegalAccessException {
		try (InputStream in = getClass().getResourceAsStream("/" + request.getReportName() + ".jrxml");
				InputStream logoStream = getClass().getResourceAsStream("/rclLogo.png");
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

			// report put parameters
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("logo", logoStream);
			String reportName = request.getReportName();

			List<?> list = null;
			ResponseEntity<String> response = null;
			
			if ("STR".equalsIgnoreCase(reportName.substring(0, 3))) {
				
				list = request.getStrList();
				response = jasperSTRRepository.getJasperReportList(request);
				
			} else if ("MNR".equalsIgnoreCase(reportName.substring(0, 3))) {
				
				list = request.getMnrList();
				response = jasperMNRRepository.getJasperReportList(request);
				
			} else if ("IJS".equalsIgnoreCase(reportName.substring(0, 3))) {
				
				list = request.getIjsList();
				response = jasperIJSRepository.getJasperReportList(request);
				
			} else if ("IPV".equalsIgnoreCase(reportName.substring(0, 3))) {
				
				list = request.getIpvList();
				response = jasperIPVRepository.getJasperReportList(request);
				
			} else if ("TOS".equalsIgnoreCase(reportName.substring(0, 3))) {
				
				list = request.getTosList();
				response = jasperTOSRepository.getJasperReportList(request);
				
			} else if ("SYS".equalsIgnoreCase(reportName.substring(0, 3))) {
				
				list = request.getSysList();
				response = jasperSYSRepository.getJasperReportList(request);
				
			} else if ("VMS".equalsIgnoreCase(reportName.substring(0, 3))) {
				
				list = request.getVmsList();
				response = jasperVMSRepository.getJasperReportList(request);
				
			}

			// Getting Data using Procedure from Repository
			String responseBody = response.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
			List<Map<String, Object>> jsonData = (List<Map<String, Object>>) jsonMap.get("resultContent");

			String resultStatus = (String) jsonMap.get("resultStatus");
			String resultMessage = (String) jsonMap.get("resultMessage");
			Object resultContent = jsonMap.get("resultContent");
			
			if(resultMessage.startsWith("ERROR")) {
				System.out.println("resultMessage : " + resultMessage + " for report " + request.getReportName());
			}
			
			// if gets Error or Ora from DB then not gets inside if condition generates empty report
			if ("F".equals(resultStatus) && resultContent == null && !(resultMessage != null && (resultMessage.startsWith("ERROR") || resultMessage.startsWith("ORA")))) {
				return responseBody.getBytes(StandardCharsets.UTF_8);
			}
			if (list != null && !list.isEmpty()) {

				Object firstItem = list.get(0);
				Field[] fields = firstItem.getClass().getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					try {
						Object value = field.get(firstItem);
						parameters.put(field.getName(), value);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						log.info("Exception in Service :: while reading json list" + e.getMessage());
					}
				}
			}

			JasperReport jasperReport = JasperCompileManager.compileReport(in);

			JRDataSource dataSource;
			if (jsonData == null || jsonData.isEmpty()) {
				dataSource = new JREmptyDataSource();
			} else {
				dataSource = new JRBeanCollectionDataSource(jsonData);
			}

			// getting report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

			String reportFormat = request.getReportFormat();

			if ("E".equals(reportFormat)) {
				
				Map<String, String> reportSheetMap = new HashMap<>();
				reportSheetMap.put("MNR102_RSE", "EOR_STATUS_RPT");
				reportSheetMap.put("MNR101_RSE", "EOR_SUMMARY_RPT");
				reportSheetMap.put("STR112_RSE", "DEPOT_FREEDAY_RPT");
				reportSheetMap.put("STR113_RSE", "DEPOT_TARIFF_RPT");
				reportSheetMap.put("STR115_RSE_EXCEL", "TERMINAL_FREEDAY_RPT");
				
//			    if ("MNR102_RSE".equals(reportName) || "MNR101_RSE".equals(reportName) || "STR112_RSE".equals(reportName) || "STR113_RSE".equals(reportName) || "STR115_RSE_EXCEL".equals(reportName)) {
				if (reportSheetMap.containsKey(reportName)) {   
			    	// Excel export configuration
					String sheetName = reportSheetMap.get(reportName); 
			        
			        JRXlsExporter xlsExporter = new JRXlsExporter();
			        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

			        SimpleXlsReportConfiguration xlsConfig = new SimpleXlsReportConfiguration();
			        xlsConfig.setOnePagePerSheet(false);
			        xlsConfig.setRemoveEmptySpaceBetweenRows(true);
			        xlsConfig.setDetectCellType(false);
			        xlsConfig.setWhitePageBackground(false);
			        xlsConfig.setSheetNames(new String[] { sheetName });
			        xlsExporter.setConfiguration(xlsConfig);

			        xlsExporter.exportReport();
			    } else {
			        // HTML export configuration
//			        HtmlExporter htmlExporter = new HtmlExporter();
//			        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//			        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArrayOutputStream));
//
//			        SimpleHtmlReportConfiguration htmlConfig = new SimpleHtmlReportConfiguration();
//			        htmlConfig.setZoomRatio(0.8f); // Adjust zoom ratio as needed
//			        htmlExporter.setConfiguration(htmlConfig);
//
//			        htmlExporter.exportReport();
			        
					HtmlExporter exporter = new HtmlExporter();
					ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
					ExporterOutput exporterOutput = new SimpleHtmlExporterOutput(byteArrayOutputStream);
					SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
					configuration.setZoomRatio(1f);
					exporter.setConfiguration(configuration);
					exporter.setExporterInput(exporterInput);
					exporter.setExporterOutput((HtmlExporterOutput) exporterOutput);
					exporter.exportReport();
			    }
			} else if ("P".equals(reportFormat)) {
			    // PDF export
			    JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
			} else {
			    throw new IllegalArgumentException("Unsupported format: " + reportFormat);
			}

			// Return the exported byte array
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			
	    	e.printStackTrace();
	    	log.info("JasperReport Exception from Service for ReportName : " + request.getReportName() + " Error "+ e.getMessage());
	    	return null;
		}
	}

}