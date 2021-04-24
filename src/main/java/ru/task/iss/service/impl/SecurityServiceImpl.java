package ru.task.iss.service.impl;
/*
 * Date: 4/20/21
 * Time: 9:06 PM
 * */

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.SecurityDto;
import ru.task.iss.entity.Security;
import ru.task.iss.exception.CustomException;
import ru.task.iss.repository.SecurityRepository;
import ru.task.iss.service.SecurityService;
import ru.task.iss.util.SecurityXmlParser;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final SecurityRepository securityRepository;
    private final ModelMapper mapper;

    // List that contains all Security's fields for the sorting validation
    private final List<Field> fields = Arrays.asList(Security.class.getDeclaredFields());

    public SecurityServiceImpl(SecurityRepository securityRepository, ModelMapper mapper) {
        this.securityRepository = securityRepository;
        this.mapper = mapper;
    }

    /* Save the security to DB */
    public void save(Security security) {
        log.info("Saving the security");
        securityRepository.save(security);
    }

    @Override
    public void importXmlData(MultipartFile multipartFile) {
        SecurityXmlParser securityXmlParser = new SecurityXmlParser(securityRepository);
        securityXmlParser.parseAndSave(multipartFile);
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

        // If there is no such field or the name is incorrect, set the default to secId
        boolean isValid = fields.stream().anyMatch(field -> field.getName().equals(finalSortBy));
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

    /**
     * Returns a Security object from database
     *
     * @param id a identifier of the Security object
     * @return the Security object
     * @throws CustomException if an object not found
     */
    @Override
    public Security findById(Integer id) {
        return securityRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found", "Security not found", HttpStatus.NOT_FOUND));
    }

    /* Delete the security by id */
    @Override
    public void deleteById(Integer id) {
        if (!securityRepository.existsById(id)) {
            throw new CustomException("Not Found", "Security not found: " + id, HttpStatus.BAD_REQUEST);
        }
        securityRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, SecurityDto securityDto) {
        Security security = securityRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found",
                        "Security not found: " + id, HttpStatus.NOT_FOUND));

        mapper.map(securityDto, security);
        save(security);
    }

    /* Define a sort direction */
    private Sort.Direction getSortDirection(String sort) {
        if (sort.contains(",asc")) return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }

    /* Remove 'asc' */
    private String getSortAsString(String sort) {
        if (sort.contains(",")) return sort.split(",")[0];
        return sort;
    }

}
