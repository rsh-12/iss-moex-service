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

@RunWith(SpringRunner.class)
@DataJpaTest
public class AbstractRepositoryClass {

    @Autowired
    TestEntityManager entityManager;
}
