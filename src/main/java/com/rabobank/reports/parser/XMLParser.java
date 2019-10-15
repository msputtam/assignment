package com.rabobank.reports.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabobank.reports.model.CustomerStatement;

@Component
public class XMLParser {
	
	private static final Logger logger = LoggerFactory.getLogger(XMLParser.class);


	static final String REFERENCE = "reference";
    static final String RECORD = "record";
    static final String ACCOUNT_NUMBER = "accountNumber";
    static final String DESCRIPTION = "description";
    static final String START_BALANCE = "startBalance";
    static final String MUTATION = "mutation";
    static final String END_BALANCE= "endBalance";
    
	
	@Value("${report.basepath}")
	private String basePath;
	
	@Value("${report.xml.filename}")
	private String xmlFileName;
	
	
	public  List<CustomerStatement> getCustomerStatements() {
	
		logger.info(" Started XMLParser::getCustomerStatements ");
		
        List<CustomerStatement> statementList = new ArrayList<CustomerStatement>();
        try {
        	
        	String fileName = getFilePath();
        	
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(fileName);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            CustomerStatement statement = null;
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart().equals(RECORD)) {
                    	statement = new CustomerStatement();
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(REFERENCE)) {
                            	statement.setReference(attribute.getValue());
                            }

                        }
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ACCOUNT_NUMBER)) {
                            event = eventReader.nextEvent();
                            statement.setAccountNumber(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(DESCRIPTION)) {
                        event = eventReader.nextEvent();
                        statement.setDescription(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(START_BALANCE)) {
                        event = eventReader.nextEvent();
                        statement.setStartBalance(Double.parseDouble(event.asCharacters().getData()));
                        continue;
                    }


                    if (event.asStartElement().getName().getLocalPart()
                            .equals(MUTATION)) {
                        event = eventReader.nextEvent();
                        statement.setMutation(Double.parseDouble(event.asCharacters().getData()));
                        continue;
                    }
                    
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(END_BALANCE)) {
                        event = eventReader.nextEvent();
                        statement.setEndBalance(Double.parseDouble(event.asCharacters().getData()));
                        continue;
                    }
                    
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(RECORD)) {
                    	statementList.add(statement);
                    }
                }

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
		logger.info(" Ended XMLParser::getCustomerStatements, size :: "+ statementList.size() +" records are "+ statementList);
        return statementList;
	}
	
	private String getFilePath() {
		logger.info(" Started XMLParser::getFilePath ");
		if(null == basePath || null ==  xmlFileName)
			throw new RuntimeException("Both BasePath and FileName should not be null");
		
		String filePath = this.basePath+"\\"+this.xmlFileName;
		logger.info(" Ended XMLParser::getFilePath "+ filePath);

		return filePath;
	}
	

}
