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

    /* Find all Securities */
    @Test
    public void findAll_ReturnsAllSecurities() {
        int quantity = repository.findAll().size();
        assertEquals(3, quantity);
    }

    /* Find the Security by id*/
    @Test
    public void findById_ReturnsSecurityById() {
        Security security = repository.findById(12441).orElse(null);
        assertNotNull(security);
        assertEquals("АбрауДюрсо", security.getShortname());
    }

    /* Find the Security by secId */
    @Test
    public void findBySecId_ReturnsSecurityBySecIdPk() {
        Security security = repository.findBySecId("1").orElse(null);
        assertNotNull(security);
        assertEquals("Apple Inc.", security.getName());
    }

    /* Save a new Security entity */
    @Test
    public void save_ShouldSaveNewSecurity() {
        int before = repository.findAll().size();
        Security security = createSecurity();
        entityManager.persistAndFlush(security);
        int after = repository.findAll().size();

        assertEquals(before + 1, after);
    }

    /* Delete the Security by secId */
    @Test
    public void deleteById_ShouldDeleteRecordBySecId() {
        int before = repository.findAll().size();
        repository.deleteBySecId("3");
        entityManager.flush();

        int after = repository.findAll().size();
        assertEquals(before - 1, after);
    }

    /* Update the Security shortname */
    @Test
    public void update_ShouldUpdateSecurityBySecId() {
        Security security = repository.findBySecId("1").orElse(null);
        assertNotNull(security);

        assertEquals("Apple", security.getShortname());
        security.setShortname("Pineapple");
        entityManager.persistAndFlush(security);

        Security updatedSecurity = repository.findBySecId("1").orElse(null);
        assertNotNull(updatedSecurity);
        assertEquals("Pineapple", updatedSecurity.getShortname());
    }
}
