package ru.task.iss.util;
/*
 * Date: 4/24/21
 * Time: 10:22 AM
 * */

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import ru.task.iss.entity.History;
import ru.task.iss.repository.HistoryRepository;
import ru.task.iss.repository.SecurityRepository;

import java.time.LocalDate;

@Service
public class HistoryXmlParser extends XmlParser {

    private final SecurityRepository securityRepository;
    private final HistoryRepository historyRepository;

    public HistoryXmlParser(HistoryRepository historyRepository, SecurityRepository securityRepository) {
        this.historyRepository = historyRepository;
        this.securityRepository = securityRepository;
    }

    @Override
    void initEntity(Element element) {
        History history = new History();

        String tradeDate = element.getAttribute("TRADEDATE");
        if (isValid(tradeDate)) {
            history.setTradeDate(LocalDate.parse(tradeDate));
        }

        history.setBoardId(element.getAttribute("BOARDID"));
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
        if (isValid(field)) return Double.parseDouble(field);
        return null;
    }

    @Override
    void saveEntity(Object entity) {
        History history = (History) entity;
        if (securityRepository.existsBySecId(history.getSecId())) {
            historyRepository.save(history);
        }
    }
}
