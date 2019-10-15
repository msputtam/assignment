package com.rabobank.reports.writer;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.rabobank.reports.model.CustomerStatement;


@Component
public class StatementWriter {

	
	private static final Logger logger = LoggerFactory.getLogger(StatementWriter.class);
	
	@Value("${report.basepath}")
	private String basePath;
	
	@Value("${report.csv.outputfilename}")
	private String outputFileName;
	
	
	@SuppressWarnings({ "unchecked", "null" })
	public void writeInvalidStatements(List<CustomerStatement> failedStatementsList) {
		
		logger.info("Started StatementWriter:: writeInvalidStatements");
		try(
			 FileWriter writer = new 
                     FileWriter(getFilePath())){
		   ColumnPositionMappingStrategy<CustomerStatement> mappingStrategy= 
                      new CustomMappingStrategy<CustomerStatement>(); 
          mappingStrategy.setType(CustomerStatement.class); 
          String[] columns = new String[]  
                  { "reference", "description"}; 
          mappingStrategy.setColumnMapping(columns); 
          StatefulBeanToCsvBuilder<CustomerStatement> builder= 
                      new StatefulBeanToCsvBuilder<CustomerStatement>(writer); 
          StatefulBeanToCsv beanWriter =  
          builder.withMappingStrategy(mappingStrategy).build();
          beanWriter.write(failedStatementsList); 
        
          logger.info("########### Statement generated and saved to  : ################## "+ getFilePath());
          
          
		}catch(Exception ex) {
			logger.error("Exception Occured in  StatementWriter:: writeInvalidStatements, exeption is "+ ex.getMessage());
		}
		logger.info("Ended StatementWriter:: writeInvalidStatements");
		
	}
	
	
	private String getFilePath() {
		logger.info(" Started CSVParser::getFilePath ");
		if(null == basePath || null ==  outputFileName)
			throw new RuntimeException("Both BasePath and FileName should not be null");
		
		String filePath = this.basePath+"\\"+this.outputFileName;
		
		logger.info(" Ended CSVParser::getFilePath "+ filePath);
		return filePath;
	}
}
