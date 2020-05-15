package com.deborawendland.provadev.dao;

import com.deborawendland.provadev.exceptions.FileAccessException;
import com.deborawendland.provadev.model.Client;
import com.deborawendland.provadev.model.Report;
import com.deborawendland.provadev.model.Sale;
import com.deborawendland.provadev.model.Salesperson;
import com.deborawendland.provadev.service.FileService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesDAO {

    private Map<Double, Client> clients;
    private Map<Double, Salesperson> salespeople;
    private Map<Double, Sale> sales;
    private FileService parse;
    private Logger logger = Logger.getLogger(this.getClass());

    private Path homepath;
    private Path pathDataIn;
    private Path pathDataOut;
    private List<Path> accessedFilesDataIn = new ArrayList<>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SalesDAO(FileService parse, Path homepath, Path pathDataIn) {
        this.parse = parse;
        this.homepath = homepath;
        this.pathDataIn = pathDataIn;
        this.pathDataOut = Paths.get(homepath + "/data/out" + "/" + "report.json");
    }

    public boolean extractSalesInformation(Path filepath){
        List<String> content = new ArrayList<>();
        filepath = Paths.get(pathDataIn + "/" + filepath.getFileName());
        logger.info("Reading file: " + filepath);
        if (!accessedFilesDataIn.contains(filepath)){
            try {
                System.out.println(filepath.toAbsolutePath());
                content = (Files.readAllLines(filepath));
                accessedFilesDataIn.add(filepath);
            } catch (IOException e) {
                throw new FileAccessException("Error accessing file: " + filepath.toUri());
            }
        }
        if (content.isEmpty()){
            return false;
        }
        logger.info("Parsing file: " + filepath);
        parse.parseFile(content);
        this.clients = parse.getClients();
        this.sales = parse.getSales();
        this.salespeople = parse.getSalespeople();
        return true;
    }

    public boolean extractInitialFiles(){
        try {
            List<File> filesInFolder = Files.walk(pathDataIn).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
            filesInFolder.stream().forEach(file -> extractSalesInformation(file.toPath()));
        } catch (IOException e) {
            throw new FileAccessException("Error accessing file: " + pathDataIn.toUri());
        }
        return true;
    }

    public void createReportFile(Report report){
        logger.info("Writing report file: " + pathDataOut);
        try {
            Files.write(pathDataOut, gson.toJson(report).getBytes());
        } catch (IOException e) {
            throw new FileAccessException("Error: Unable to write on file: " + pathDataOut.toUri());
        }
        logger.info("Waiting for new files");
    }

    public Map<Double, Client> getClients() {
        return clients;
    }

    public Map<Double, Salesperson> getSalespeople() {
        return salespeople;
    }

    public Map<Double, Sale> getSales() {
        return sales;
    }
}
