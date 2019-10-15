package com.rabobank.reports.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.reports.model.CustomerStatement;
import com.rabobank.reports.parser.CSVParser;
import com.rabobank.reports.parser.XMLParser;
import com.rabobank.reports.util.StatementValidator;
import com.rabobank.reports.writer.StatementWriter;


@Service
public class ReportsService {

	private static final Logger logger = LoggerFactory.getLogger(ReportsService.class);
	
	@Autowired
	CSVParser csvParser;
	
	@Autowired
	XMLParser xmlParser;

	@Autowired
	StatementValidator validator;
	
	@Autowired
	StatementWriter writer;
	
	public void validateReports() {
		logger.info("Started ReportsService::validateReports ");
		try {
			    List<CustomerStatement> csvStaementList =  this.csvParser.getCustomerStatements();
			    List<CustomerStatement> xmlStaementList =  this.xmlParser.getCustomerStatements();
			    List<CustomerStatement> mergeStaementList = getTotalStatments(csvStaementList,xmlStaementList);
			    if(null != mergeStaementList && mergeStaementList.size() > 0) {
			    	List<CustomerStatement> failedStatementsList = this.validator.getInvalidStatements(mergeStaementList);	
			    	writer.writeInvalidStatements(failedStatementsList);
			    }
			    
		}catch(Exception ex) {
			logger.error("Exception Occred in  ReportsService::validateReports "+ ex.getMessage());
		}
		
		logger.info("Ended ReportsService::validateReports ");
	}
	
	private List<CustomerStatement> getTotalStatments(List<CustomerStatement> csvStaementList, List<CustomerStatement> xmlStaementList){
		
		List<CustomerStatement> merge = null;
		if(null != csvStaementList && null != xmlStaementList) {
			merge = csvStaementList.stream().collect(Collectors.toList());
		    merge.addAll(xmlStaementList);
	    }else if(null == csvStaementList) {
	    	return xmlStaementList;
	    }else {
	    	return csvStaementList;
	    }
		return merge;
	}
	
	
	
}
