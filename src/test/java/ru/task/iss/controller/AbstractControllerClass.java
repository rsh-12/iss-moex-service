package ru.task.iss.controller;
/*
 * Date: 4/22/21
 * Time: 5:50 PM
 * */

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public abstract class AbstractControllerClass {

    final static String SECURITIES = "/api/securities/";

    @Autowired
    MockMvc mvc;

}
