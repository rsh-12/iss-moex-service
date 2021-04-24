package ru.task.iss.controller;
/*
 * Date: 4/24/21
 * Time: 9:46 PM
 * */

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HistoryControllerTest extends AbstractControllerClass {

    @Test
    public void createHistory_ShouldReturnError() throws Exception {
        HistoryDto history = new HistoryDto();
        history.setValue(1.2345678);

        mvc.perform(post(HISTORIES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(history)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
