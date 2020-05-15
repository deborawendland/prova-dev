package com.deborawendland.provadev.service;

import com.deborawendland.provadev.model.Client;
import com.deborawendland.provadev.model.Sale;
import com.deborawendland.provadev.model.SaleItem;
import com.deborawendland.provadev.model.Salesperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.*;

public class FileService {

    private Map<Double, Client> clients;
    private Map<Double, Salesperson> salespeople;
    private Map<Double, Sale> sales;

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

/*        dataColumnSeparator = environment.getProperty("data.column.separator");
        saleItemsArraySeparator = environment.getProperty("sale.items.array.separator");
        saleItemsInfoSeparator = environment.getProperty("sale.items.info.separator");
        dataColumnCount = Integer.parseInt(environment.getProperty("data.column.count"));
        saleItemsInfoCount = Integer.parseInt(environment.getProperty("sale.items.info.count"));
        saleItemsArrayBeginsWith = environment.getProperty("sale.items.array.begins.with");
        saleItemsArrayEndsWith = environment.getProperty("sale.items.array.ends.with");*/
    }

    public void parseFile(List<String> file){
        // TODO string eh limitado, alterar quando ler arquivo
        List<String[]> linesParsedBySeparator = new ArrayList<>();
        //file.stream().forEach(line -> System.out.println(line));
        file.stream().forEach(line -> linesParsedBySeparator.add(line.split(dataColumnSeparator)));
/*        linesParsedBySeparator.stream().forEach(line -> {Arrays.stream(line).forEach(part -> System.out.println(part));
            System.out.println("\n");});*/
        linesParsedBySeparator.stream().forEach(line -> {if (validateSeparator(line, dataColumnCount, dataColumnSeparator)) {sortDataType(line);}});
    }

    public boolean validateSeparator (String[] line, int columnCount, String columnSeparator){
        if (line.length != columnCount) {
            // TODO corrigir para error
            System.out.println("Errr: Expected separator(" + columnSeparator + ") count: " + (columnCount - 1) + ". Found: " + (line.length - 1) + ". \nEntry:" + line);
            // throw new InvalidSeparatorException("Error: Expected separator(" + columnSeparator + ") count: " + (columnCount - 1) + ". Found: " + line.length + ". \nEntry:" + line.toString());
            return false;
        }
        return true;
    }

    public void sortDataType (String[] line){
        switch (line[0]){
            case "001":
                double cpf = Double.parseDouble(line[1]);
                salespeople.put(cpf, new Salesperson(cpf, line[2], Double.parseDouble(line[3])));
                break;
            case "002":
                double cnpj = Double.parseDouble(line[1]);
                clients.put(cnpj, new Client(cnpj, line[2], line[3]));
                break;
            case "003":
                double saleID = Double.parseDouble(line[1]);
                // TODO Sale 3 param, verificar se colocar a info do salesperson ao inves da str do nome
                sales.put(saleID, new Sale(saleID, parseSaleItems(line[2]).get(), line[3]));
                break;
            default:
                System.out.println("Errr: Invalid identifier: " + line[0] + ". Expected: 001, 002 or 003. \nEntry: " + line.toString());
                //throw new InvalidDataIdentifierException("Error: Invalid identifier: " + line[0] + ". Expected: 001, 002 or 003. \nEntry: " + line.toString());
        }
    }

    public Optional<List<SaleItem>> parseSaleItems (String itemsLine){
        // TODO colocar brackets como variavel?
        if (itemsLine.startsWith(saleItemsArrayBeginsWith) && itemsLine.endsWith(saleItemsArrayEndsWith)) {
            itemsLine = itemsLine.replace(saleItemsArrayBeginsWith, "");
            itemsLine = itemsLine.replace(saleItemsArrayEndsWith, "");
            List<String> saleItemsSeparated = Arrays.asList(itemsLine.split(saleItemsArraySeparator));
            List<String[]> saleItemsInfoSeparated = new ArrayList<>();
            saleItemsSeparated.stream().forEach(line -> saleItemsInfoSeparated.add(line.split(saleItemsInfoSeparator)));
            Optional<List<SaleItem>> saleItems = Optional.of(new ArrayList<>());
            saleItemsInfoSeparated.stream().forEach(line -> {if (validateSeparator(line, saleItemsInfoCount, saleItemsInfoSeparator))
            {saleItems.get().add(new SaleItem(Double.parseDouble(line[0]), Integer.parseInt(line[1]), Double.parseDouble(line[2])));}});
            return saleItems;

        } else {
            System.out.println("Errr: Invalid sale item format. Expected: [item id-item quantity-item price,...]. Found: " + itemsLine);
            //throw new InvalidSaleItemFormatException("Error: Invalid sale item format. Expected: [item id - item quantity, item price, ...]. Found: " + itemsLine);
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
