package com.deborawendland.provadev.service;

import com.deborawendland.provadev.config.AppConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    private List<String> lines = new ArrayList<>();

    @Before
    public void init(){
        lines.add("001ç5555çJoanaç50000");
        lines.add("001ç4444çTiagoç60000");
        lines.add("002ç11111çJoseçFinance");
        lines.add("002ç22222çLanaçEducation");
        lines.add("003ç130ç[1-1-10]çJoana");
        lines.add("003ç120ç[1-100-100,2-30-2.50,3-40-3.10]çTiago");
        fileService.parseFile(lines);
    }


    @Test
    public void clientsTest() {
        Assert.assertEquals(2, fileService.getClients().size());

        Assert.assertTrue(fileService.getClients().get(11111.0).getName().equals("Jose"));
        Assert.assertTrue(fileService.getClients().get(11111.0).getBusinessArea().equals("Finance"));

        Assert.assertTrue(fileService.getClients().get(22222.0).getName().equals("Lana"));
        Assert.assertTrue(fileService.getClients().get(22222.0).getBusinessArea().equals("Education"));
    }

    @Test
    public void salespeopleTest() {
        Assert.assertEquals(2, fileService.getSalespeople().size());
        Assert.assertTrue(fileService.getSalespeople().get(5555.0).getName().contentEquals("Joana"));
        Assert.assertEquals(50000, fileService.getSalespeople().get(5555.0).getSalary(), 0.1);

        Assert.assertTrue(fileService.getSalespeople().get(4444.0).getName().equals("Tiago"));
        Assert.assertEquals(60000, fileService.getSalespeople().get(4444.0).getSalary(), 0.1);
    }

    @Test
    public void salesTest() {
        Assert.assertEquals(2, fileService.getSales().size());

        Assert.assertTrue(fileService.getSales().get(130.0).getSalesperson().equals("Joana"));
        Assert.assertEquals(1, fileService.getSales().get(130.0).getSaleItems().size());
        Assert.assertEquals(1, fileService.getSales().get(130.0).getSaleItems().get(0).getItemQuantity());
        Assert.assertEquals(1, fileService.getSales().get(130.0).getSaleItems().get(0).getItemID(), 0.1);
        Assert.assertEquals(10, fileService.getSales().get(130.0).getSaleItems().get(0).getItemPrice(), 0.1);

        Assert.assertTrue(fileService.getSales().get(120.0).getSalesperson().equals("Tiago"));
        Assert.assertEquals(3, fileService.getSales().get(120.0).getSaleItems().size());
        Assert.assertEquals(100, fileService.getSales().get(120.0).getSaleItems().get(0).getItemQuantity());
        Assert.assertEquals(1, fileService.getSales().get(120.0).getSaleItems().get(0).getItemID(), 0.1);
        Assert.assertEquals(100, fileService.getSales().get(120.0).getSaleItems().get(0).getItemPrice(), 0.1);

        Assert.assertEquals(30, fileService.getSales().get(120.0).getSaleItems().get(1).getItemQuantity());
        Assert.assertEquals(2, fileService.getSales().get(120.0).getSaleItems().get(1).getItemID(), 0.1);
        Assert.assertEquals(2.50, fileService.getSales().get(120.0).getSaleItems().get(1).getItemPrice(), 0.1);

        Assert.assertEquals(40, fileService.getSales().get(120.0).getSaleItems().get(2).getItemQuantity());
        Assert.assertEquals(3, fileService.getSales().get(120.0).getSaleItems().get(2).getItemID(), 0.1);
        Assert.assertEquals(3.10, fileService.getSales().get(120.0).getSaleItems().get(2).getItemPrice(), 0.1);

    }
}