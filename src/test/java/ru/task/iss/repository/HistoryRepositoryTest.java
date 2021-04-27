package ru.task.iss.repository;
/*
 * Date: 4/24/21
 * Time: 7:10 PM
 * */

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.task.iss.entity.History;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HistoryRepositoryTest extends AbstractRepositoryClass {

    @Autowired
    HistoryRepository repository;

    @Test
    public void save_ShouldSaveNewHistory() {
        int before = repository.findAll().size();

        History history = new History();
        history.setSecId("1");
        entityManager.persistAndFlush(history);

        int after = repository.findAll().size();
        assertEquals(before + 1, after);


        History historyFromDb = repository.findById(history.getId())
                .orElse(null);
        assertNotNull(historyFromDb);
        assertEquals("1", historyFromDb.getSecId());
    }
}
