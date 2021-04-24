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
import ru.task.iss.entity.Security;
import ru.task.iss.exception.CustomException;
import ru.task.iss.repository.SecurityRepository;
import ru.task.iss.service.SecurityService;
import ru.task.iss.util.SecurityXmlParser;

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
    public void save(Security security) {
        log.info("Saving the security");
        securityRepository.save(security);
    }

    /**
     * Converts MultipartFile to a File.
     * Parses and initializes the entity, then saves to the DB.
     *
     * @param multipartFile XML Multipart file.
     * @throws CustomException if file is not found, catches an IOException,
     *                         a ParserConfigurationException or a SAXException.
     */
    @Override
    public void importXmlData(MultipartFile multipartFile) {
        SecurityXmlParser securityXmlParser = new SecurityXmlParser(securityRepository);
        securityXmlParser.parseAndSave(multipartFile);
    }

    /**
     * Creates a new History object and then saves to the DB.
     *
     * @param security is a Security object.
     * @throws CustomException if name is not valid.
     */
    @Override
    public void create(Security security) {
        if (!security.getName().matches("^[а-яА-Я0-9]+( [а-яА-Я0-9]+)*$")) {
            throw new CustomException("Validation error",
                    "The name must contain only Cyrillic and numbers", HttpStatus.BAD_REQUEST);
        }
        save(security);
    }

    /**
     * Method searches for records in the DB and always returns a list of History objects or an empty list
     *
     * @param pageNo    - page number, default 0.
     * @param pageSize  - page size,default 10 elements.
     * @param sort      - sorting, default by secId.
     * @param emitentTitle - filtering by emitent_title.
     * @return list of Security objects.
     */
    @Override
    public List<Security> findAllSecurities(Integer pageNo, Integer pageSize,
                                            String sort, String emitentTitle) {
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
     * @throws CustomException if an object not found.
     */
    @Override
    public Security findById(Integer id) {
        return securityRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found", "Security not found", HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a record by ID from the DB.
     *
     * @param id is a Security identifier
     * @throws CustomException if object is not found.
     */
    @Override
    public void deleteById(Integer id) {
        if (!securityRepository.existsById(id)) {
            throw new CustomException("Not Found", "Security not found: " + id, HttpStatus.BAD_REQUEST);
        }
        securityRepository.deleteById(id);
    }

    /**
     * Searches a securiy by ID.
     * Updates a record fields and then saves to the DB.
     *
     * @param id is a Security object identifier.
     * @param securityDto - DTO for Security entity.
     * @throws CustomException if Security by ID is not found.
     */
    @Override
    public void update(Integer id, SecurityDto securityDto) {
        Security security = securityRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found",
                        "Security not found: " + id, HttpStatus.NOT_FOUND));

        mapper.map(securityDto, security);
        save(security);
    }

}
