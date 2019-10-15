package com.rabobank.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.rabobank.reports.service.ReportsService;


@SpringBootApplication
public class ReportsApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(ReportsApplication.class);
    
	
	@Autowired
    ReportsService reportService;
	
    public static void main(String... args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(ReportsApplication.class, args);
        ctx.close();
    }

    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with option names : {}", args.getOptionNames());
        this.reportService.validateReports();
    }

}
