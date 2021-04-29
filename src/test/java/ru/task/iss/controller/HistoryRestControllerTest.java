package ru.task.iss.controller;
/*
 * Date: 4/24/21
 * Time: 9:46 PM
 * */

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.task.iss.dto.HistoryDto;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HistoryRestControllerTest extends AbstractControllerClass {

    /* Create a new History object - returns validation error, 400 */
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

    /* Create a new History - returns PK not found 404 */
    @Test
    public void createHistory_ShouldReturnPkNotFoundError() throws Exception {
        HistoryDto history = new HistoryDto();

        mvc.perform(post(HISTORIES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(history)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", containsStringIgnoringCase("not found")))
                .andDo(print());
    }

    /* Create a new History - returns 200 ok */
    @Test
    public void createHistory_ShouldReturnOK() throws Exception {
        mvc.perform(get(HISTORIES + 4))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", containsStringIgnoringCase("not found")));

        HistoryDto history = new HistoryDto();
        history.setSecId("1");
        history.setShortname("HIST");

        mvc.perform(post(HISTORIES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(history)))
                .andExpect(status().isCreated());

        mvc.perform(get(HISTORIES + 4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", containsString("HIST")))
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

    /* Get History by id - returns 404 not found */
    @Test
    public void find_ShouldReturnNotFound() throws Exception {
        mvc.perform(get(HISTORIES + 9999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", containsString("Not Found")))
                .andDo(print());
    }

    /* Delete History - returns no content, 204 */
    @Test
    public void delete_ShouldDeleteHistoryById() throws Exception {
        mvc.perform(get(HISTORIES + 3)).andExpect(status().isOk());
        mvc.perform(delete(HISTORIES + 3)).andExpect(status().isNoContent());
        mvc.perform(get(HISTORIES + 3)).andExpect(status().isNotFound());
    }

    /* Update History - returns 200 ok */
    @Test
    public void update_ShouldUpdateHistory() throws Exception {
        mvc.perform(get(HISTORIES + 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", is("AKEU ETF")));

        String shortname = "{\"shortname\": \"IKIGAI\"}";
        mvc.perform(patch(HISTORIES + 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(shortname))
                .andExpect(status().isOk());

        mvc.perform(get(HISTORIES + 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", is("IKIGAI")));
    }

    @Test
    public void find_ShouldReturnHistory_FilterByDate() throws Exception {
        mvc.perform(get("/api/histories?date=2020-04-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.histories.size()", is(1)))
                .andExpect(jsonPath("_embedded.histories[0].boardId", containsString("TQTE")));
    }
}
