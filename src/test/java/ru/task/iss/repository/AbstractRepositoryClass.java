package ru.task.iss.repository;
/*
 * Date: 4/21/21
 * Time: 10:14 PM
 * */

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.task.iss.entity.Security;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AbstractRepositoryClass {

    @Autowired
    TestEntityManager entityManager;

    Security createSecurity() {
        Security security = new Security();
        security.setId(1);
        security.setSecId("123");
        security.setShortname("short");
        security.setRegnumber("");
        security.setName("name");
        security.setIsin("ISIN2323");
        security.setIsTraded(12);
        security.setEmitentId(212);
        security.setEmitentTitle("emtitentTitle");
        security.setEmitentOkpo("okpo");
        security.setGosreg("");
        security.setType("common-share");
        security.setGroup("group");
        security.setPrimaryBoardid("121");
        security.setMarketPriceBoardid("mp12");
        return security;
    }
}
