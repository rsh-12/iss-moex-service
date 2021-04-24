package ru.task.iss.util;
/*
 * Date: 4/24/21
 * Time: 10:22 AM
 * */

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import ru.task.iss.entity.History;
import ru.task.iss.repository.HistoryRepository;

@Service
public class HistoryXmlParser extends XmlParser {

    private final HistoryRepository historyRepository;

    public HistoryXmlParser(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    void initEntity(Element element) {

    }

    @Override
    void saveEntity(Object entity) {
        historyRepository.save((History) entity);
    }
}
