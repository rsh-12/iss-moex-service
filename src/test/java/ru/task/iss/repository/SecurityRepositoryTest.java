package ru.task.iss.repository;
/*
 * Date: 4/21/21
 * Time: 10:14 PM
 * */

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.task.iss.entity.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecurityRepositoryTest extends AbstractRepositoryClass {

    @Autowired
    SecurityRepository repository;

    @Test
    public void findAll_ReturnsAllSecurities() {
        int quantity = repository.findAll().size();
        assertEquals(3, quantity);
    }

    @Test
    public void findById_ReturnsSecurityById() {
        Security security = repository.findById(12441).orElse(null);
        assertNotNull(security);
        assertEquals("АбрауДюрсо", security.getShortname());
    }

    @Test
    public void findBySecId_ReturnsSecurityBySecIdPk() {
        Security security = repository.findBySecId("1").orElse(null);
        assertNotNull(security);
        assertEquals("Apple Inc.", security.getName());
    }

    @Test
    public void save_ShouldSaveNewSecurity() {
        int before = repository.findAll().size();
        Security security = createSecurity();
        entityManager.persistAndFlush(security);
        int after = repository.findAll().size();

        assertEquals(before + 1, after);
    }

    // delete
    @Test
    public void deleteById_ShouldDeleteRecordById() {
        int before = repository.findAll().size();
        repository.deleteBySecId("3");
        entityManager.flush();

        int after = repository.findAll().size();
        assertEquals(before - 1, after);
    }

    // update
}
