package com.cognitivenode;

import com.cognitivenode.jaxb.Invoices;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

/**
 * Sample application to demonstrate JAXB usage.
 * This class show how to un-marshall XML data
 * <p/>
 * NOTE: Before running execute "mvn jaxb2:xjc" goal
 * Required sources will be generated in folder
 * src/main/generated
 *
 * @author nataraj.basappa
 * @version 1.0, 28/01/2012
 */
public class AppUnmarshaller {

    public static final String XSD_FILE = "eConnect_Invoice.xsd";
    public static final String SAMPLE_XML_FILE = "eConnect_Invoice.xml";

    public static void main(String args[]) {
        try {
            // package where to find JAXB generated files
            JAXBContext context = JAXBContext.newInstance("com.cognitivenode.jaxb");
            // create a un-marshaller from context to un-marshall XML data
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // create a schema factory instance using Standard W3C schema namespace
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // use the created factory to generate a schema object with given schema xsd
            // set the schema to be applied (validated against) for incoming xml
            unmarshaller.setSchema(schemaFactory.newSchema(AppUnmarshaller.class.getClassLoader().getResource(XSD_FILE)));
            // un-marshaled the xml data
            Object object = unmarshaller.unmarshal(AppUnmarshaller.class.getClassLoader().getResourceAsStream(SAMPLE_XML_FILE));
            // check against root of object if it returns true type cast and apply business rules...
            if (object instanceof Invoices) {
                Invoices invoices = (Invoices) object;
                System.out.println(invoices.getInvoice().getHeader().getInvoiceNumber());
            } else {
                System.out.println("Shouldn't reach here\nUnmarshalled object is not of type invoice.");
            }
        } catch (JAXBException e) {
            System.out.println("JAXB Exception code: " + e.getErrorCode() + " \nJAXB Exception message: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("Unable to set schema \n" + e.getMessage());
        }
    }
}
