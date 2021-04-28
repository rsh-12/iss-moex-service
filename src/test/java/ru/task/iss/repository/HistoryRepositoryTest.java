package ru.task.iss.repository;
/*
 * Date: 4/24/21
 * Time: 7:10 PM
 * */

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.task.iss.entity.History;

import static org.junit.Assert.*;

public class HistoryRepositoryTest extends AbstractRepositoryClass {

    @Autowired
    HistoryRepository repository;

    /* Save new History to DB */
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

    /* Find all History objects */
    @Test
    public void findAll_ShouldReturnAllObjects() {
        int quantity = repository.findAll().size();
        assertEquals(3, quantity);
    }

    /* Find by id */
    @Test
    public void findById_ShouldReturnHistoryById() {
        History history = repository.findById(1L).orElse(null);
        assertNotNull(history);
        assertEquals("АбрауДюрсо", history.getShortname());
    }

    /* Update History object */
    @Test
    public void update_ShouldUpdateHistory() {
        History history = repository.findById(2L).orElse(null);
        assertNotNull(history);
        assertEquals("AKEU ETF", history.getShortname());

        history.setShortname("new name");
        entityManager.persistAndFlush(history);

        History updatedHistory = repository.findById(2L).orElse(null);
        assertNotNull(updatedHistory);
        assertEquals("new name", updatedHistory.getShortname());
    }

    /* Delete History by id */
    @Test
    public void delete_ShouldDeleteHistoryById() {
        int before = repository.findAll().size();
        assertTrue(repository.existsById(3L));

        repository.deleteById(3L);
        entityManager.flush();

        assertFalse(repository.existsById(3L));
        int after = repository.findAll().size();

        assertEquals(before - 1, after);
    }
}
