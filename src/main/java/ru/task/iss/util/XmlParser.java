package ru.task.iss.util;
/*
 * Date: 4/24/21
 * Time: 9:52 AM
 * */

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.task.iss.exception.CustomException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public abstract class XmlParser {

    public void parseAndSave(MultipartFile multipartFile) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            File file = convertToFile(multipartFile);
            Document doc = documentBuilder.parse(new File(String.valueOf(file)));

            doc.getDocumentElement().normalize();

            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                Node node = rows.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println(element.getAttribute("id"));
                    initEntity(element);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            throw new CustomException("Error during parsing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* Convert multipart file to file */
    private File convertToFile(MultipartFile file) {
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try {
            file.transferTo(convertedFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("Internal Server Error", "Error during conversion",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return convertedFile;
    }

    abstract void initEntity(Element element);

    abstract void saveEntity(Object entity);

    boolean isValid(String value) {
        return value != null && !value.isBlank() && !value.isEmpty();
    }

}



