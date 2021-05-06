package ru.task.iss.service;

import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;
import ru.task.iss.exception.CustomException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface HistoryService {

    /**
     * Converts MultipartFile to a File.
     * Parses and initializes the entity, then saves to the DB.
     *
     * @param file is a XML Multipart file.
     * @throws CustomException if file was not found, catches an IOException,
     *                         a ParserConfigurationException or a SAXException.
     */
    void importXmlData(MultipartFile file) throws IOException;

    /**
     * Creates a new History object and saves to the DB.
     *
     * @param historyDto - DTO for History object.
     */
    void create(HistoryDto historyDto);

    /**
     * Method searches for records in the DB and always returns a list of History objects or an empty list
     *
     * @param pageNo    - page number, default 0.
     * @param pageSize  - page size, default 10 elements.
     * @param sort      - sorting, default by secId.
     * @param tradeDate - filtering by trade_date.
     * @return list of Hisotry objects.
     */
    List<History> findAllHistories(Integer pageNo, Integer pageSize, String sort, LocalDate tradeDate);

    /**
     * Method searches for a specific record by ID in the DB.
     *
     * @param id - History object identifier.
     * @return History object.
     * @throws CustomException if the record was not found in the DB.
     */
    History findById(Long id);

    /**
     * Method deletes a record by ID from the DB.
     *
     * @param id is a History object identifier.
     * @throws CustomException if the record was not found in the DB.
     */
    void deleteById(Long id);

    /**
     * Updates a record fields and then saves to the DB.
     *
     * @param id         is a History object identifier.
     * @param historyDto - DTO for History entity.
     * @throws CustomException if History object by ID was not found.
     */
    void update(Long id, HistoryDto historyDto);

    /* Save the history to the DB */
    void save(History history);

}
