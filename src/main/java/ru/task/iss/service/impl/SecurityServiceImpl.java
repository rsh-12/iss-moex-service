package ru.task.iss.service.impl;
/*
 * Date: 4/20/21
 * Time: 9:06 PM
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.task.iss.entity.Security;
import ru.task.iss.exception.CustomException;
import ru.task.iss.repository.SecurityRepository;
import ru.task.iss.service.SecurityService;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final SecurityRepository securityRepository;

    public SecurityServiceImpl(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    private File multipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

    public void save(Security security) {
        log.info("Saving the security");
        securityRepository.save(security);
    }

    @Override
    public void importXmlData(MultipartFile multipartFile) {
        File file;
        try {
            file = multipartToFile(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("Error during file converting", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        parseXmlData(file);
    }

    @Override
    public void create(Security security) {
        save(security);
    }

    @Async
    void parseXmlData(File file) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.parse(new File(String.valueOf(file)));
            doc.getDocumentElement().normalize();

            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                Node node = rows.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println(element.getAttribute("id"));

                    Security security = new Security();
                    initSecurityEntity(security, element);

                    save(security);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            throw new CustomException("Error during parsing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    void initSecurityEntity(Security security, Element element) {

        String id = element.getAttribute("id");
        if (isValid(id)) {
            security.setId(Integer.parseInt(id));
        }

        String isTraded = element.getAttribute("is_traded");
        if (isValid(isTraded)) {
            security.setIsTraded(Integer.parseInt(isTraded));
        }

        String emitentId = element.getAttribute("emitent_id");
        if (isValid(emitentId)) {
            security.setEmitentId(Integer.parseInt(emitentId));
        }

        security.setSecId(element.getAttribute("secid"));
        security.setShortname(element.getAttribute("shortname"));
        security.setRegnumber(element.getAttribute("regnumber"));
        security.setName(element.getAttribute("name"));
        security.setIsin(element.getAttribute("isin"));
        security.setEmitentTitle(element.getAttribute("emitent_title"));
        security.setEmitentInn(element.getAttribute("emitent_inn"));
        security.setEmitentOkpo(element.getAttribute("element_okpo"));
        security.setGosreg(element.getAttribute("gosreg"));
        security.setType(element.getAttribute("type"));
        security.setGroup(element.getAttribute("group"));
        security.setPrimaryBoardid(element.getAttribute("primary_boardid"));
        security.setMarketPriceBoardid(element.getAttribute("marketprice_boardid"));

        save(security);
    }

    private boolean isValid(String value) {
        return value != null && !value.isBlank() && !value.isEmpty();
    }
}
