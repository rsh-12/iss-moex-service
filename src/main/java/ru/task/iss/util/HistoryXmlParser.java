package ru.task.iss.util;
/*
 * Date: 4/24/21
 * Time: 10:22 AM
 * */

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import ru.task.iss.entity.History;
import ru.task.iss.repository.HistoryRepository;

import java.time.LocalDate;

@Service
public class HistoryXmlParser extends XmlParser {

    private final HistoryRepository historyRepository;

    public HistoryXmlParser(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    void initEntity(Element element) {
        History history = new History();

        history.setBoardId(element.getAttribute("BOARDID"));
        history.setTradeDate(LocalDate.parse(element.getAttribute("TRADEDATE")));
        history.setShortname(element.getAttribute("SHORTNAME"));
        history.setSecId(element.getAttribute("SECID"));

        history.setNumTrades(getCheckedValue(element, "NUMTRADES"));
        history.setValue(getCheckedValue(element, "VALUE"));
        history.setOpen(getCheckedValue(element, "OPEN"));
        history.setLow(getCheckedValue(element, "LOW"));
        history.setHigh(getCheckedValue(element, "HIGH"));
        history.setLegalClosePrice(getCheckedValue(element, "LEGALCLOSEPRICE"));
        history.setWaPrice(getCheckedValue(element, "WAPRICE"));
        history.setClose(getCheckedValue(element, "CLOSE"));
        history.setVolume(getCheckedValue(element, "VOLUME"));
        history.setMarketPrice2(getCheckedValue(element, "MARKETPRICE2"));
        history.setMarketPrice3(getCheckedValue(element, "MARKETPRICE3"));
        history.setAdmittedQuote(getCheckedValue(element, "ADMITTEDQUOTE"));
        history.setMp2ValTrd(getCheckedValue(element, "MP2VALTRD"));
        history.setMarketPrice3TradesValue(getCheckedValue(element, "MARKETPRICE3TRADESVALUE"));
        history.setAdmittedValue(getCheckedValue(element, "ADMITTEDVALUE"));
        history.setWaVal(getCheckedValue(element, "WAVAL"));

        saveEntity(history);
    }

    private Double getCheckedValue(Element element, String value) {
        String field = element.getAttribute(value);
        if (field != null) return Double.parseDouble(field);
        return null;
    }

    @Override
    void saveEntity(Object entity) {
        historyRepository.save((History) entity);
    }
}
