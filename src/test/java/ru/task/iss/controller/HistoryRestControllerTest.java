package ru.task.iss.controller;
/*
 * Date: 4/24/21
 * Time: 9:46 PM
 * */

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.task.iss.dto.HistoryDto;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HistoryRestControllerTest extends AbstractControllerClass {

    /* Create a new History object - returns validation error, 404 */
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

    /* Get all History objects - default max 10 elems */
    @Test
    public void findAll_ShouldReturnHistoryObjects() throws Exception {
        mvc.perform(get(HISTORIES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.histories[0].shortname", containsString("AKNX ETF")))
                .andDo(print());
    }

    /* Get all History objects - sort by boardId */
    @Test
    public void findAllSortByBoardid_ShouldReturnHistoryObjects() throws Exception {
        mvc.perform(get("/api/histories?sort=boardId,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.histories[0].boardId", containsString("TQBR")))
                .andDo(print());
    }

    /* Get History by id - returns 200 ok */

    @Test
    public void find_ShouldReturnHistoryById() throws Exception {
        mvc.perform(get(HISTORIES + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", containsString("АбрауДюрсо")))
                .andDo(print());
    }
}
