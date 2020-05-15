package com.deborawendland.provadev.config;

import com.deborawendland.provadev.dao.DirectoryWatcherDAO;
import com.deborawendland.provadev.dao.SalesDAO;
import com.deborawendland.provadev.service.FileService;
import com.deborawendland.provadev.service.ReportService;
import com.deborawendland.provadev.service.SalesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ReportService reportService(){
        return new ReportService();
    }

    @Bean
    public FileService salesDAOParse(){
        return new FileService();
    }

    @Bean
    public SalesDAO salesDAO(){
        return new SalesDAO(salesDAOParse());
    }

    @Bean
    public SalesService salesService(){
        return new SalesService(salesDAO(), reportService(), directoryWatcherDAO());
    }

    @Bean
    public DirectoryWatcherDAO directoryWatcherDAO (){
        return new DirectoryWatcherDAO();
    }

}