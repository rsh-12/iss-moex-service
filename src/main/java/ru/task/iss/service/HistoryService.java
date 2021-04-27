package ru.task.iss.service;

import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface HistoryService {

    void importXmlData(MultipartFile file) throws IOException;

    void create(HistoryDto historyDto);

    List<History> findAllHistories(Integer pageNo, Integer pageSize, String sort, LocalDate tradeDate);

    History findById(Long id);

    void deleteById(Long id);

    void update(Long id, HistoryDto historyDto);

    /* Save the history to the DB */
    void save(History history);
}
