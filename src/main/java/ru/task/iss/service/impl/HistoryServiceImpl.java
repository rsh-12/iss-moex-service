package ru.task.iss.service.impl;
/*
 * Date: 4/24/21
 * Time: 9:21 AM
 * */

import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;
import ru.task.iss.entity.Security;
import ru.task.iss.repository.HistoryRepository;
import ru.task.iss.service.HistoryService;

import java.io.IOException;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void importXmlData(MultipartFile file) throws IOException {

    }

    @Override
    public void create(HistoryDto historyDto) {

    }

    @Override
    public List<Security> findAllHistories(Integer pageNo, Integer pageSize, String sort, String emitentId) {
        return null;
    }

    @Override
    public History findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void update(Integer id, HistoryDto historyDto) {

    }

    public void save(History history) {
        historyRepository.save(history);
    }
}
