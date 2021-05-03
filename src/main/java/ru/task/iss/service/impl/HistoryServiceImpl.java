package ru.task.iss.service.impl;
/*
 * Date: 4/24/21
 * Time: 9:21 AM
 * */

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;
import ru.task.iss.exception.CustomException;
import ru.task.iss.repository.HistoryRepository;
import ru.task.iss.repository.SecurityRepository;
import ru.task.iss.service.HistoryService;
import ru.task.iss.util.HistoryXmlParser;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoryServiceImpl extends AbstractServiceClass implements HistoryService {

    private static final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);

    private final HistoryRepository historyRepository;
    private final SecurityRepository securityRepository;
    private final ModelMapper mapper;

    public HistoryServiceImpl(HistoryRepository historyRepository,
                              SecurityRepository securityRepository, ModelMapper mapper) {
        this.historyRepository = historyRepository;
        this.securityRepository = securityRepository;
        this.mapper = mapper;
    }

    @Override
    public void importXmlData(MultipartFile multipartFile) {
        log.info("> parsing XML file (history)");
        HistoryXmlParser historyXmlParser = new HistoryXmlParser(historyRepository, securityRepository);
        historyXmlParser.parseAndSave(multipartFile);
    }

    @Override
    public void create(HistoryDto historyDto) {
        log.info("> creating a new History object");
        History history = mapper.map(historyDto, History.class);
        save(history);
    }

    @Override
    public List<History> findAllHistories(Integer pageNo, Integer pageSize,
                                          String sort, LocalDate tradeDate) {
        log.info("> getting a list of histories objects");

        PageRequest pageRequest = getPageRequest(pageNo, pageSize, sort, HISTORY_FIELDS);

        if (tradeDate == null) {
            return historyRepository.findAll(pageRequest).getContent();
        }
        return historyRepository.findAll(pageRequest, tradeDate).getContent();
    }

    @Override
    public History findById(Long id) {
        log.info("> getting a History by id: " + id);

        return historyRepository.findById(id).orElseThrow(() ->
                new CustomException("Not Found", "History not found: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteById(Long id) {
        log.info("> deleting a History by id: " + id);

        if (!historyRepository.existsById(id)) {
            log.warn("> a History by id {} not found", id);
            throw new CustomException("Not Found", "History not found: " + id, HttpStatus.NOT_FOUND);
        }
        historyRepository.deleteById(id);
    }

    @Override
    public void update(Long id, HistoryDto historyDto) {
        log.info("> updating a History by id: " + id);

        History history = historyRepository.findById(id).orElseThrow(() ->
                new CustomException("Not Found", "History not found: " + id, HttpStatus.NOT_FOUND));

        mapper.map(historyDto, history);
        save(history);
    }

    /* Save the history to the DB */
    @Override
    public void save(History history) {
        String fk = history.getSecId();
        if (!securityRepository.existsBySecId(fk)) {
            throw new CustomException("Not found", "Primary Key not found: " + fk, HttpStatus.NOT_FOUND);
        }
        historyRepository.save(history);
    }

    @Override
    public void saveMvc(History history) {
        String fk = history.getSecId();
        if (!securityRepository.existsBySecId(fk)) {
            throw new RuntimeException("Primary Id Not Found: " + fk);
        }
        historyRepository.save(history);
    }
}
