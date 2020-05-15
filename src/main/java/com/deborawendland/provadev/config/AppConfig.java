package com.deborawendland.provadev.config;

import com.deborawendland.provadev.dao.DirectoryWatcherDAO;
import com.deborawendland.provadev.dao.SalesDAO;
import com.deborawendland.provadev.service.FileService;
import com.deborawendland.provadev.service.ReportService;
import com.deborawendland.provadev.service.SalesService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Resource
    private Environment environment;

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
        return Paths.get(environment.getProperty("homepath"));
    }

}