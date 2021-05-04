package ru.task.iss.service;

import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.SecurityDto;
import ru.task.iss.dto.SecurityHistoryDto;
import ru.task.iss.entity.Security;
import ru.task.iss.exception.CustomException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface SecurityService {

    /* Save the security to the DB */
    void save(Security security);

    void save(SecurityDto securityDto);

    /**
     * Converts MultipartFile to a File.
     * Parses and initializes the entity, then saves to the DB.
     *
     * @param file XML Multipart file.
     * @throws CustomException if file was not found, catches an IOException,
     *                         a ParserConfigurationException or a SAXException.
     */
    void importXmlData(MultipartFile file) throws IOException;

    /**
     * Creates a new Security object and then saves to the DB.
     *
     * @param securityDto is a SecurityDto object.
     * @throws CustomException if name is not valid.
     */
    void create(SecurityDto securityDto);

    /**
     * Method searches for records in the DB and always returns a list of Security objects or an empty list
     *
     * @param pageNo       - page number, default 0.
     * @param pageSize     - page size,default 10 elements.
     * @param sort         - sorting, default by secId.
     * @param emitentTitle - filtering by emitent_title.
     * @return list of Security objects.
     */
    List<Security> findAllSecurities(Integer pageNo, Integer pageSize, String sort, String emitentTitle);

    /**
     * Returns a Security object from database.
     *
     * @param id is an identifier of the Security object.
     * @return a Security object.
     * @throws CustomException if an object was not found.
     */
    Security findById(Integer id);

    /**
     * Deletes a record by ID from the DB.
     *
     * @param id is a Security identifier
     * @throws CustomException if object was not found.
     */
    void deleteById(Integer id);

    /**
     * Searches a securiy by ID.
     * Updates a record fields and then saves to the DB.
     *
     * @param id          is a Security object identifier.
     * @param securityDto - DTO for Security entity.
     * @throws CustomException if Security by ID was not found.
     */
    void update(Integer id, SecurityDto securityDto);

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
    List<SecurityHistoryDto> findViewFields(Integer pageNo, Integer pageSize, String sort,
                                            String emitentTitle, LocalDate tradeDate);

    /**
     * Returns a list of specific fields: secid, regnumber, name, emitent_title, tradedate, numtrades, open, close.
     *
     * @param pageNo   indicates the page number.
     * @param pageSize sets the number of objects to return.
     * @param sort     - sorting by field, case sensitive.
     * @return list of specific fields.
     */
    List<SecurityHistoryDto> findViewFields(Integer pageNo, Integer pageSize, String sort);
}
