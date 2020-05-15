package com.deborawendland.provadev.config;

import com.deborawendland.provadev.dao.DirectoryWatcherDAO;
import com.deborawendland.provadev.dao.SalesDAO;
import com.deborawendland.provadev.service.FileService;
import com.deborawendland.provadev.service.ReportService;
import com.deborawendland.provadev.service.SalesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

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
        return new SalesDAO(salesDAOParse(), getHomepath(), getDataInPath());
    }

    @Bean
    public SalesService salesService(){
        return new SalesService(salesDAO(), reportService(), directoryWatcherDAO());
    }

    @Bean
    public DirectoryWatcherDAO directoryWatcherDAO (){
        return new DirectoryWatcherDAO(getDataInPath());
    }

    public Path getDataInPath() {
        return Paths.get(getHomepath() + "/data/in");
    }

    public Path getHomepath(){
        return Paths.get("/home/debs/Documents");
    }

}