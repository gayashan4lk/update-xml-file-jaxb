package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Binder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("C:\\Users\\ErangaGayashanBISTEC\\workspaces\\java\\update-xml-values\\src\\main\\java\\org\\example\\Student.xml");
            JAXBContext jc = JAXBContext.newInstance(Student.class);
            Binder<Node> binder = jc.createBinder();

            binder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Node xmlNode = doc.getDocumentElement();

            Student student = (Student)binder.updateJAXB(xmlNode);
            student.setAge(11);
            student.setName("Andy");

            xmlNode = binder.updateXML(student);
            doc.setNodeValue(xmlNode.getNodeValue());

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.transform(new DOMSource(doc), new StreamResult(System.out));

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(student, new File("C:\\Users\\ErangaGayashanBISTEC\\workspaces\\java\\update-xml-values\\generatedXmlFiles\\Student.xml"));

            /*int amountOfFiles = 10000;
            for (int i = 0; i < amountOfFiles; i++){
                Student student = (Student)binder.updateJAXB(xmlNode);
                student.setAge(i);
                student.setName("Andy" + i);
                m.marshal(student, new File("C:\\Users\\ErangaGayashanBISTEC\\workspaces\\java\\update-xml-values\\generatedXmlFiles\\Student"+ i +".xml"));
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}