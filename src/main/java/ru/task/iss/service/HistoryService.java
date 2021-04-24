package ru.task.iss.service;

import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;
import ru.task.iss.entity.Security;

import java.io.IOException;
import java.util.List;

public interface HistoryService {

    void importXmlData(MultipartFile file) throws IOException;

    void create(HistoryDto historyDto);

    List<Security> findAllHistories(Integer pageNo, Integer pageSize, String sort, String emitentId);

    History findById(Integer id);

    void deleteById(Integer id);

    void update(Integer id, HistoryDto historyDto);
}
