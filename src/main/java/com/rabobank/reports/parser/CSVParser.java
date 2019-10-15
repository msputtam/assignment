package com.rabobank.reports.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.rabobank.reports.model.CustomerStatement;

@Component
public class CSVParser {

	private static final Logger logger = LoggerFactory.getLogger(CSVParser.class);

	
	@Value("${report.basepath}")
	private String basePath;
	
	@Value("${report.csv.filename}")
	private String csvFileName;
	
	private static final String SAPERATOR = ",";
	
	
	
	public  List<CustomerStatement> getCustomerStatements() {
		
		logger.info(" Started CSVParser::getCustomerStatements");
		
		List<CustomerStatement> list=null;
		
		String fileName =  getFilePath();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream.skip(1)
					.map(CSVParser::lineMapper)
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception Occured  in CSVParser::getCustomerStatements "+ e.toString());
		}
		logger.info(" Ended CSVParser::getCustomerStatements, size :: "+ list.size() +" records are "+ list);
		
		return list;
	}
	
	private String getFilePath() throws RuntimeException{
		logger.info(" Started CSVParser::getFilePath ");
		
		String filePath  = null;
		try {
		if(null == basePath || null ==  csvFileName)
			throw new RuntimeException("Both BasePath and FileName should not be null");
		filePath = this.basePath+"\\"+this.csvFileName;
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		logger.info(" Ended CSVParser::getFilePath "+ filePath);
		return filePath;
	}

	private  static CustomerStatement lineMapper(String line) throws RuntimeException{
		if(null == line) return null;
		   String[] lineArray = line.split(SAPERATOR);
		   CustomerStatement  customerReport = new CustomerStatement();
		   customerReport.setReference(lineArray[0]);
		   customerReport.setAccountNumber(lineArray[1]);
		   customerReport.setDescription(lineArray[2]);
		   customerReport.setStartBalance(Double.parseDouble(lineArray[3]));
		   customerReport.setMutation(Double.parseDouble(lineArray[4]));
		   customerReport.setEndBalance(Double.parseDouble(lineArray[5]));
		   
		return customerReport;
	}
	
}
