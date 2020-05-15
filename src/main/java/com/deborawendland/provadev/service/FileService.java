package com.deborawendland.provadev.service;

import com.deborawendland.provadev.model.Client;
import com.deborawendland.provadev.model.Sale;
import com.deborawendland.provadev.model.SaleItem;
import com.deborawendland.provadev.model.Salesperson;
import org.apache.log4j.Logger;

import java.util.*;

public class FileService {

    private Map<Double, Client> clients;
    private Map<Double, Salesperson> salespeople;
    private Map<Double, Sale> sales;
    private Logger logger = Logger.getLogger(this.getClass());

    private String dataColumnSeparator = "ç";
    private String saleItemsArraySeparator = ",";
    private String saleItemsInfoSeparator = "-";
    private int dataColumnCount = 4;
    private int saleItemsInfoCount = 3;
    private String saleItemsArrayBeginsWith = "[";
    private String saleItemsArrayEndsWith = "]";


    public FileService() {
        clients = new HashMap<>();
        salespeople = new HashMap<>();
        sales = new HashMap<>();
    }

    public void parseFile(List<String> file){
        logger.info("Parsing lines");
        List<String[]> linesParsedBySeparator = new ArrayList<>();
        file.stream().forEach(line -> {
            linesParsedBySeparator.add(line.split(dataColumnSeparator));});
        linesParsedBySeparator.stream().forEach(line -> {
            logger.info("Parsing line: " + line[0] + dataColumnSeparator + line[1] + dataColumnSeparator + line[2] + dataColumnSeparator + line[3]);
            if (validateSeparator(line, dataColumnCount, dataColumnSeparator)) {
                sortDataType(line);}});
    }

    public boolean validateSeparator (String[] line, int columnCount, String columnSeparator){
        if (line.length != columnCount) {
            logger.error("Error: Expected separator(" + columnSeparator + ") count: " + (columnCount - 1) + ". Found: " + (line.length - 1) + ". Entry: " + Arrays.toString(line));
            return false;
        }
        logger.info("Valid entry: " + Arrays.toString(line));
        return true;
    }

    public void sortDataType (String[] line){
        switch (line[0]){
            case "001":
                logger.info("Creating a salesperson object.");
                double cpf = Double.parseDouble(line[1]);
                salespeople.put(cpf, new Salesperson(cpf, line[2], Double.parseDouble(line[3])));
                break;
            case "002":
                logger.info("Creating a client object.");
                double cnpj = Double.parseDouble(line[1]);
                clients.put(cnpj, new Client(cnpj, line[2], line[3]));
                break;
            case "003":
                double saleID = Double.parseDouble(line[1]);
                Optional<List<SaleItem>> saleItems =  parseSaleItems(line[2]);
                if (saleItems.isPresent()){
                    logger.info("Creating a sale object.");
                    sales.put(saleID, new Sale(saleID, parseSaleItems(line[2]).get(), line[3]));
                } else {
                    logger.error("Invalid sale object");
                }
                break;
            default:
                logger.error("Error: Invalid identifier: " + line[0] + ". Expected: 001, 002 or 003.");
        }
    }

    public Optional<List<SaleItem>> parseSaleItems (String itemsLine){
        logger.info("Parsing sale items: " + itemsLine);
        if (itemsLine.startsWith(saleItemsArrayBeginsWith) && itemsLine.endsWith(saleItemsArrayEndsWith)) {
            itemsLine = itemsLine.replace(saleItemsArrayBeginsWith, "");
            itemsLine = itemsLine.replace(saleItemsArrayEndsWith, "");
            List<String> saleItemsSeparated = Arrays.asList(itemsLine.split(saleItemsArraySeparator));
            List<String[]> saleItemsInfoSeparated = new ArrayList<>();
            saleItemsSeparated.stream().forEach(line -> {
                saleItemsInfoSeparated.add(line.split(saleItemsInfoSeparator));
            });
            Optional<List<SaleItem>> saleItems = Optional.of(new ArrayList<>());
            saleItemsInfoSeparated.stream().forEach(line -> {if (validateSeparator(line, saleItemsInfoCount, saleItemsInfoSeparator))
                {saleItems.get().add(new SaleItem(Double.parseDouble(line[0]), Integer.parseInt(line[1]), Double.parseDouble(line[2])));}});
            return saleItems;

        } else {
            logger.error("Error: Invalid sale item format. Expected: [item id-item quantity-item price,...]. Found: " + itemsLine);
            return Optional.empty();
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
