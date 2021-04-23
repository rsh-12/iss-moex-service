package ru.task.iss.controller;
/*
 * Date: 4/23/21
 * Time: 6:30 PM
 * */

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SecurityControllerTest extends AbstractControllerClass {

    /* Get the security by id - returns ok */
    @Test
    public void findOne_ReturnsTheSecurityById() throws Exception {
        mvc.perform(get(SECURITIES + 154676))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", containsString("Apple")))
                .andDo(print());
    }
}
