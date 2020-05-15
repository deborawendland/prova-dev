package com.deborawendland.provadev.dao;

import com.deborawendland.provadev.exceptions.FileAccessException;
import com.deborawendland.provadev.model.Client;
import com.deborawendland.provadev.model.Report;
import com.deborawendland.provadev.model.Sale;
import com.deborawendland.provadev.model.Salesperson;
import com.deborawendland.provadev.service.FileService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SalesDAO {

    private Map<Double, Client> clients;
    private Map<Double, Salesperson> salespeople;
    private Map<Double, Sale> sales;
    private FileService parse;

    private Path homepath = Paths.get("/home/debs/Documents");
    private Path pathDataIn = Paths.get(homepath + "/data/in");
    private Path pathDataOut = Paths.get(homepath + "/data/out" + "/" + "report.json");
    private List<Path> accessedFilesDataIn = new ArrayList<>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SalesDAO(FileService parse) {
        this.parse = parse;
        //this.homepath = environment.getProperty("file.homepath");
        /*this.pathDataIn =  homepath + environment.getProperty("file.data.in");
        this.pathDataOut = homepath + environment.getProperty("file.data.out") + "/" + environment.getProperty("file.data.out.report");*/
    }

    public boolean extractSalesInformation(Path filepath){
        List<String> content = new ArrayList<>();
        if (!accessedFilesDataIn.contains(filepath)){
            try {
                content = (Files.readAllLines(filepath));
                accessedFilesDataIn.add(filepath);
            } catch (IOException e) {
                throw new FileAccessException("Error accessing file: " + filepath.toUri());
            }
        }
        if (content.isEmpty()){
            return false;
        }
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
        try {
            Files.write(pathDataOut, gson.toJson(report).getBytes());
        } catch (IOException e) {
            throw new FileAccessException("Error: Unable to write on file: " + pathDataOut.toUri());
        }
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
