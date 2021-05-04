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

    @Override
    public void save(SecurityDto securityDto) {
        Security security = mapper.map(securityDto, Security.class);
        save(security);
    }

    @Override
    public void importXmlData(MultipartFile multipartFile) {
        log.info("> parsing XML file (security)");
        SecurityXmlParser securityXmlParser = new SecurityXmlParser(securityRepository);
        securityXmlParser.parseAndSave(multipartFile);
    }

    @Override
    public void create(SecurityDto securityDto) {
        log.info("> creating a new Security object");

        if (securityDto.getName() == null || !securityDto.getName().matches("^[а-яА-Я0-9]+( [а-яА-Я0-9]+)*$")) {
            log.warn("> name validation failed");
            throw new CustomException("Validation error",
                    "The name must contain only Cyrillic and numbers", HttpStatus.BAD_REQUEST);
        }
        Security security = mapper.map(securityDto, Security.class);
        save(security);
    }

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

    @Override
    public Security findById(Integer id) {
        log.info("> getting a Security by id: " + id);

        return securityRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found", "Security not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteById(Integer id) {
        log.info("> deleting a Security by id: " + id);

        if (!securityRepository.existsById(id)) {
            log.warn("> a Security by id {} not found", id);
            throw new CustomException("Not Found", "Security not found: " + id, HttpStatus.BAD_REQUEST);
        }
        securityRepository.deleteById(id);
    }

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

    @Override
    public List<SecurityHistoryDto> findViewFields(Integer pageNo, Integer pageSize, String sort,
                                                   String emitentTitle, LocalDate tradeDate) {
        log.info("> getting a list of specific fields");

        PageRequest pageRequest = getPageRequest(pageNo, pageSize, sort, SECURITY_HISTORY_FIELDS);

        if (emitentTitle == null && tradeDate == null) {
            return securityRepository.findFields(pageRequest).getContent();
        }
        return securityRepository.findFields(pageRequest, emitentTitle, tradeDate).getContent();
    }

    @Override
    public List<SecurityHistoryDto> findViewFields(Integer pageNo, Integer pageSize, String sort) {
        log.info("> getting a list of specific fields");

        PageRequest pageRequest = getPageRequest(pageNo, pageSize, sort, SECURITY_HISTORY_FIELDS);
        return securityRepository.findFields(pageRequest).getContent();
    }


}
