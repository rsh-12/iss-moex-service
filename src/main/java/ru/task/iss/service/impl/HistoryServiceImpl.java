package ru.task.iss.service.impl;
/*
 * Date: 4/24/21
 * Time: 9:21 AM
 * */

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;
import ru.task.iss.repository.HistoryRepository;
import ru.task.iss.service.HistoryService;
import ru.task.iss.util.HistoryXmlParser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final ModelMapper mapper;

    public HistoryServiceImpl(HistoryRepository historyRepository, ModelMapper mapper) {
        this.historyRepository = historyRepository;
        this.mapper = mapper;
    }

    @Override
    public void importXmlData(MultipartFile file) throws IOException {
        HistoryXmlParser historyXmlParser = new HistoryXmlParser(historyRepository);
        historyXmlParser.parseAndSave(file);
    }

    @Override
    public void create(HistoryDto historyDto) {
        History history = mapper.map(historyDto, History.class);
        save(history);
    }

    @Override
    public List<History> findAllHistories(Integer pageNo, Integer pageSize, String sort, LocalDate tradeDate) {
        return null;
    }

    @Override
    public History findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(Long id, HistoryDto historyDto) {

    }

    public void save(History history) {
        historyRepository.save(history);
    }
}
