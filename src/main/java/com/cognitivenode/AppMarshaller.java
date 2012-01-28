package com.cognitivenode;

import com.cognitivenode.jaxb.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Sample application to demonstrate JAXB usage.
 * This class show how to marshall Java Objects into XML
 * <p/>
 * NOTE: Before running execute "mvn jaxb2:xjc" goal
 * Required sources will be generated in folder
 * src/main/generated
 *
 * @author nataraj.basappa
 * @version 1.0, 28/01/2012
 */
public class AppMarshaller {

    public static final String XSD_FILE = "eConnect_Order.xsd";
    public static final String SAMPLE_XML_FILE_NAME = "eConnect_Order.xml";

    public static void main(String args[]) {

        try {
            // package where to find JAXB generated files
            JAXBContext context = JAXBContext.newInstance("com.cognitivenode.jaxb");

            // create a marshaller from context to marshall Java objects to XML
            Marshaller marshaller = context.createMarshaller();

            // create a schema factory instance using Standard W3C schema namespace
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // use the created factory to generate a schema object with given schema xsd
            // set the schema to be applied & validated against before xml is written out
            File schemaFile = new File(AppMarshaller.class.getClassLoader().getResource(XSD_FILE).getPath());
            marshaller.setSchema(schemaFactory.newSchema(schemaFile));

            // create various java objects to represent xml data using factory methods
            // and add some dummy data to be written out
            ObjectFactory factory = new ObjectFactory();
            Orders orders = factory.createOrders();
            Orders.Order order = factory.createOrdersOrder();
            orders.getOrder().add(order);

            OrderHeader header = factory.createOrderHeader();
            header.setOrderDate(Calendar.getInstance());
            header.setOrderNum("12345");
            header.setOrderType("NORMAL");
            order.setHeader(header);

            OrderBuyer buyer = factory.createOrderBuyer();
            buyer.setBuyerName("PK Industries");
            buyer.setBuyerId("PK");
            buyer.setVATRegNo("910 020 12");
            AddressLine buyerAddress1 = factory.createAddressLine();
            buyerAddress1.setValue("15 Betahouse Southcote Road");
            AddressLine buyerAddress2 = factory.createAddressLine();
            buyerAddress2.setValue("Reading");
            buyer.getBillingAddress().add(buyerAddress1);
            buyer.getBillingAddress().add(buyerAddress2);
            PostCode buyerPostCode = factory.createPostCode();
            buyerPostCode.setValue("RG30 2AR");
            buyer.setBillingAddressPostCode(buyerPostCode);
            buyer.getShippingAddress().add(buyerAddress1);
            buyer.getShippingAddress().add(buyerAddress2);
            buyer.setShippingAddressPostCode(buyerPostCode);
            ContactDetails buyerContact = factory.createContactDetails();
            buyerContact.setName("Poornima");
            buyerContact.setPhone("07875224696");
            buyerContact.setEMail("pk.poornima@gmail.com");
            buyer.setBuyerContact(buyerContact);
            order.setBuyer(buyer);

            OrderSupplier supplier = factory.createOrderSupplier();
            supplier.setSupplierName("Cognitivenode");
            supplier.setSupplierId("CN");
            supplier.setVATRegNo("212 012 29");
            AddressLine supplierAddress1 = factory.createAddressLine();
            supplierAddress1.setValue("483 Rochfords Gardens");
            AddressLine supplierAddress2 = factory.createAddressLine();
            supplierAddress2.setValue("Slough");
            supplier.getSupplierAddress().add(supplierAddress1);
            supplier.getSupplierAddress().add(supplierAddress2);
            PostCode supplierPostCode = factory.createPostCode();
            supplierPostCode.setValue("SL5 1XF");
            supplier.setSupplierAddressPostCode(supplierPostCode);
            ContactDetails supplierContact = factory.createContactDetails();
            supplierContact.setName("Nataraj Basappa");
            supplierContact.setEMail("natarajmb@gmail.com");
            supplierContact.setPhone("07875359990");
            supplier.setSupplierContact(supplierContact);
            order.setSupplier(supplier);

            OrderBody body = factory.createOrderBody();
            body.setNumberOfLines("1");
            LineEntry lineEntry = factory.createLineEntry();
            lineEntry.setLineNum("1");
            lineEntry.setBuyerDescription("Marlboro Silvers");
            lineEntry.setBuyerProductCode("MLS01S");
            lineEntry.setSupplierDescription("Marlboro Silvers (ultra-lights)");
            lineEntry.setSupplierProductCode("MLS01-S");
            lineEntry.setEAN("MARLBORO");
            lineEntry.setUnitCode("Pack");
            lineEntry.setUnitDescription("Pack of 20");
            lineEntry.setQuantity("1");
            lineEntry.setUnitPriceInc("10.00");
            lineEntry.setUnitVAT("1.00");
            lineEntry.setUnitPriceExc("9.00");
            lineEntry.setLinePriceInc("10.00");
            lineEntry.setLineVAT("1.00");
            lineEntry.setLinePriceExc("9.00");
            body.getOrderLine().add(lineEntry);
            order.setBody(body);

            OrderSummary summary = factory.createOrderSummary();
            summary.setOrderTotalExc("9.00");
            summary.setOrderTotalVAT("1.00");
            summary.setOrderTotalInc("10.00");
            order.setSummary(summary);

            File file = new File(schemaFile.getParent() + System.getProperty("file.separator") + SAMPLE_XML_FILE_NAME);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            marshaller.marshal(orders, fileWriter);
            fileWriter.close();
        } catch (JAXBException e) {
            System.out.println("JAXB Exception code: " + e.getErrorCode() + " \nJAXB Exception message: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("Unable to set schema \n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Unable to write to file output.\n" + e.getMessage());
        }
    }

}
