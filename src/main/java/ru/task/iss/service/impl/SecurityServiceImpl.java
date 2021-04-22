package ru.task.iss.service.impl;
/*
 * Date: 4/20/21
 * Time: 9:06 PM
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final SecurityRepository securityRepository;

    private final List<Field> fields = Arrays.asList(Security.class.getDeclaredFields());

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

    /* Create the entity if the name is only Cyrillic +/- numbers (space) */
    @Override
    public void create(Security security) {
        if (!security.getName().matches("^[а-яА-Я0-9]+( [а-яА-Я0-9]+)*$")) {
            throw new CustomException("Validation error",
                    "The name must contain only Cyrillic and numbers", HttpStatus.BAD_REQUEST);
        }
        save(security);
    }

    @Override
    public List<Security> findAllSecurities(Integer pageNo, Integer pageSize,
                                            String sort, String emitentTitle) {

        String sortBy = getSortAsString(sort);
        String finalSortBy = sortBy;

        boolean isValid = fields.stream().anyMatch(field -> field.getName().equals( finalSortBy));
        if (!isValid) {
            sortBy = "secId";
        }

        PageRequest pageRequest = PageRequest.of(
                pageNo, pageSize, Sort.by(getSortDirection(sort), sortBy));

        if (emitentTitle == null) {
            return securityRepository.findAll(pageRequest).getContent();
        }

        return securityRepository.findAll(pageRequest, emitentTitle).getContent();
    }

    private Sort.Direction getSortDirection(String sort) {
        if (sort.contains(",asc")) return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }

    private String getSortAsString(String sort) {
        if (sort.contains(",")) return sort.split(",")[0];
        return sort;
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
