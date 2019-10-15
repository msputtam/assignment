package com.rabobank.reports.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.rabobank.reports.model.CustomerStatement;

@Component
public class StatementValidator {

	private static final Logger logger = LoggerFactory.getLogger(StatementValidator.class);

	public List<CustomerStatement> getInvalidStatements(List<CustomerStatement> statementList) {
		logger.info("Started StatementValidator::getInvalidStatements ");
		List<CustomerStatement> failedStatementList = new ArrayList<>();
		try {
			
			Map<String, List<CustomerStatement>>groupByReference = 
					statementList.stream().collect(Collectors.groupingBy(CustomerStatement::getReference));
			
			groupByReference.keySet().forEach((k)->{
				List<CustomerStatement> groupList=  groupByReference.get(k);
				if(groupList.size()>1) {
					failedStatementList.addAll(groupList);
				}else {
					CustomerStatement statement  = groupList.get(0);
					if(statement.getEndBalance() < 0) {
						failedStatementList.add(statement);
					}
				}
			 });
		}catch(Exception ex) {
			logger.error("Exception Occured in  StatementValidator::getInvalidStatements,  "+ ex.getMessage());
		}
		logger.info("Started StatementValidator::getInvalidStatements, invalid stements size :: "+ failedStatementList.size() + " , records are "+ failedStatementList);
		return failedStatementList;
		
	}
}
