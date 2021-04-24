package ru.task.iss.util;
/*
 * Date: 4/24/21
 * Time: 9:55 AM
 * */

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import ru.task.iss.entity.Security;
import ru.task.iss.repository.SecurityRepository;

@Service
public class SecurityXmlParser extends XmlParser {

    private final SecurityRepository securityRepository;

    public SecurityXmlParser(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    void initEntity(Element element) {
        Security security = new Security();

        String id = element.getAttribute("id");
        if (isValid(id)) {
            security.setId(Integer.parseInt(id));
        }

        String isTraded = element.getAttribute("is_traded");
        if (isValid(isTraded)) {
            security.setIsTraded(Integer.parseInt(isTraded));
        }

        String emitentId = element.getAttribute("emitent_id");
        if (isValid(emitentId)) {
            security.setEmitentId(Integer.parseInt(emitentId));
        }

        security.setSecId(element.getAttribute("secid"));
        security.setShortname(element.getAttribute("shortname"));
        security.setRegnumber(element.getAttribute("regnumber"));
        security.setName(element.getAttribute("name"));
        security.setIsin(element.getAttribute("isin"));
        security.setEmitentTitle(element.getAttribute("emitent_title"));
        security.setEmitentInn(element.getAttribute("emitent_inn"));
        security.setEmitentOkpo(element.getAttribute("element_okpo"));
        security.setGosreg(element.getAttribute("gosreg"));
        security.setType(element.getAttribute("type"));
        security.setGroup(element.getAttribute("group"));
        security.setPrimaryBoardid(element.getAttribute("primary_boardid"));
        security.setMarketPriceBoardid(element.getAttribute("marketprice_boardid"));

        saveEntity(security);
    }

    @Override
    void saveEntity(Object entity) {
        securityRepository.save((Security) entity);
    }
}
