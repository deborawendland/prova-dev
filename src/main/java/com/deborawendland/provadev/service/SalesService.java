package com.deborawendland.provadev.service;

import com.deborawendland.provadev.dao.DirectoryWatcherDAO;
import com.deborawendland.provadev.dao.SalesDAO;

public class SalesService {

    private SalesDAO salesDAO;
    private ReportService reportService;
    private DirectoryWatcherDAO directoryWatcher;

    public SalesService(SalesDAO salesDAO, ReportService reportService, DirectoryWatcherDAO directoryWatcher) {
        this.salesDAO = salesDAO;
        this.reportService = reportService;
        this.directoryWatcher = directoryWatcher;
    }

    public void getSalesInfo(){
        if (salesDAO.extractInitialFiles()){
            createReport();
            while (true){
                if (salesDAO.extractSalesInformation(directoryWatcher.directoryWatcher())){
                    createReport();
                }
            }
        }
    }

    public void createReport(){
        salesDAO.createReportFile(reportService.generateReport(salesDAO.getClients(), salesDAO.getSalespeople(), salesDAO.getSales()));
    }
}
