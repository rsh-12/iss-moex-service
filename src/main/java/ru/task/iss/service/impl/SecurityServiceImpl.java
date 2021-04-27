package ru.task.iss.service.impl;
/*
 * Date: 4/20/21
 * Time: 9:06 PM
 * */

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.SecurityDto;
import ru.task.iss.dto.SecurityHistoryDto;
import ru.task.iss.entity.Security;
import ru.task.iss.exception.CustomException;
import ru.task.iss.repository.SecurityRepository;
import ru.task.iss.service.SecurityService;
import ru.task.iss.util.SecurityXmlParser;

import java.time.LocalDate;
import java.util.List;

@Service
public class SecurityServiceImpl extends AbstractServiceClass implements SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final SecurityRepository securityRepository;
    private final ModelMapper mapper;

    public SecurityServiceImpl(SecurityRepository securityRepository, ModelMapper mapper) {
        this.securityRepository = securityRepository;
        this.mapper = mapper;
    }

    /* Save the security to the DB */
    @Override
    public void save(Security security) {
        log.info("> saving the security");
        securityRepository.save(security);
    }

    /**
     * Converts MultipartFile to a File.
     * Parses and initializes the entity, then saves to the DB.
     *
     * @param multipartFile XML Multipart file.
     * @throws CustomException if file was not found, catches an IOException,
     *                         a ParserConfigurationException or a SAXException.
     */
    @Override
    public void importXmlData(MultipartFile multipartFile) {
        log.info("> parsing XML file (security)");
        SecurityXmlParser securityXmlParser = new SecurityXmlParser(securityRepository);
        securityXmlParser.parseAndSave(multipartFile);
    }

    /**
     * Creates a new Security object and then saves to the DB.
     *
     * @param security is a Security object.
     * @throws CustomException if name is not valid.
     */
    @Override
    public void create(Security security) {
        log.info("> creating a new Security object");

        if (!security.getName().matches("^[а-яА-Я0-9]+( [а-яА-Я0-9]+)*$")) {
            log.warn("> name validation failed");
            throw new CustomException("Validation error",
                    "The name must contain only Cyrillic and numbers", HttpStatus.BAD_REQUEST);
        }
        save(security);
    }

    /**
     * Method searches for records in the DB and always returns a list of Security objects or an empty list
     *
     * @param pageNo       - page number, default 0.
     * @param pageSize     - page size,default 10 elements.
     * @param sort         - sorting, default by secId.
     * @param emitentTitle - filtering by emitent_title.
     * @return list of Security objects.
     */
    @Override
    public List<Security> findAllSecurities(Integer pageNo, Integer pageSize,
                                            String sort, String emitentTitle) {
        log.info("> getting a list of securities objects");

        PageRequest pageRequest = getPageRequest(pageNo, pageSize, sort, SECURITY_FIELDS);

        if (emitentTitle == null) {
            return securityRepository.findAll(pageRequest).getContent();
        }
        return securityRepository.findAll(pageRequest, emitentTitle).getContent();
    }

    /**
     * Returns a Security object from database.
     *
     * @param id is an identifier of the Security object.
     * @return a Security object.
     * @throws CustomException if an object was not found.
     */
    @Override
    public Security findById(Integer id) {
        log.info("> getting a Security by id: " + id);

        return securityRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found", "Security not found", HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a record by ID from the DB.
     *
     * @param id is a Security identifier
     * @throws CustomException if object was not found.
     */
    @Override
    public void deleteById(Integer id) {
        log.info("> deleting a Security by id: " + id);

        if (!securityRepository.existsById(id)) {
            log.warn("> a Security by id {} not found", id);
            throw new CustomException("Not Found", "Security not found: " + id, HttpStatus.BAD_REQUEST);
        }
        securityRepository.deleteById(id);
    }

    /**
     * Searches a securiy by ID.
     * Updates a record fields and then saves to the DB.
     *
     * @param id          is a Security object identifier.
     * @param securityDto - DTO for Security entity.
     * @throws CustomException if Security by ID was not found.
     */
    @Override
    public void update(Integer id, SecurityDto securityDto) {
        log.info("> updating a Security by id: " + id);

        if (securityDto.getName() == null || !securityDto.getName().matches("^[а-яА-Я0-9]+( [а-яА-Я0-9]+)*$")) {
            log.warn("> name validation failed");
            throw new CustomException("Validation error",
                    "The name must contain only Cyrillic and numbers", HttpStatus.BAD_REQUEST);
        }

        Security security = securityRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found",
                        "Security not found: " + id, HttpStatus.NOT_FOUND));

        mapper.map(securityDto, security);
        save(security);
    }

    /**
     * Returns a list of specific fields: secid, regnumber, name, emitent_title, tradedate, numtrades, open, close.
     *
     * @param pageNo       indicates the page number.
     * @param pageSize     sets the number of objects to return.
     * @param sort         - sorting by field, case sensitive.
     * @param emitentTitle - filtering by emitent_title, case insensitive.
     * @param tradeDate    compares to trade_date.
     * @return list of specific fields.
     */
    @Override
    public List<SecurityHistoryDto> findSpecificFields(Integer pageNo, Integer pageSize, String sort,
                                                       String emitentTitle, LocalDate tradeDate) {
        log.info("> getting a list of specific fields");

        PageRequest pageRequest = getPageRequest(pageNo, pageSize, sort, SECURITY_HISTORY_FIELDS);

        if (emitentTitle == null && tradeDate == null) {
            return securityRepository.findFields(pageRequest).getContent();
        }
        return securityRepository.findFields(pageRequest, emitentTitle, tradeDate).getContent();
    }

    /**
     * Returns a list of specific fields: secid, regnumber, name, emitent_title, tradedate, numtrades, open, close.
     *
     * @param pageNo   indicates the page number.
     * @param pageSize sets the number of objects to return.
     * @param sort     - sorting by field, case sensitive.
     * @return list of specific fields.
     */
    @Override
    public List<SecurityHistoryDto> findViewFields(Integer pageNo, Integer pageSize, String sort) {
        log.info("> getting a list of specific fields");

        PageRequest pageRequest = getPageRequest(pageNo, pageSize, sort, SECURITY_HISTORY_FIELDS);
        return securityRepository.findFields(pageRequest).getContent();
    }


}
