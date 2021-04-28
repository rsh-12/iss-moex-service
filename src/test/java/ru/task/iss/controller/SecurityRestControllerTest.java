package ru.task.iss.controller;
/*
 * Date: 4/23/21
 * Time: 6:30 PM
 * */

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.task.iss.dto.SecurityDto;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SecurityRestControllerTest extends AbstractControllerClass {

    /* Get 'view' fields */
    @Test
    public void view_ShouldReturnSpecificFields() throws Exception {
        mvc.perform(get(SECURITIES + "view"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /* Get view fields - with param size */
    @Test
    public void viewWithSizeParam_ShouldReturnSpecificFields() throws Exception {
        mvc.perform(get(SECURITIES + "view?size=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("_embedded.fields.size()", is(1)));
    }

    /* Get view fields - with param sort desc */
    @Test
    public void viewWithSortDescParam_ShouldReturnSpecificFields() throws Exception {
        mvc.perform(get(SECURITIES + "view?sort=name"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("_embedded.fields[0].name", containsString("Абрау-Дюрсо ПАО ао")));
    }

    /* Get view fields - with param sort desc */
    @Test
    public void viewWithSortAscParam_ShouldReturnSpecificFields() throws Exception {
        mvc.perform(get(SECURITIES + "view?sort=name,asc"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("_embedded.fields[0].name", containsString("Apple Inc.")));
    }

    /* Get all securities - returns max 10 elems by default */
    @Test
    public void findAllSecurities_ShouldReturnAllSecurities() throws Exception {
        mvc.perform(get(SECURITIES))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /* Get all securities - returns 2 elems sorted by shortname asc*/
    @Test
    public void findAllSecuritiesWithParams_ShouldReturnSorted2Objects() throws Exception {
        mvc.perform(get("/api/securities?sort=shortname,asc&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.securities.size()", is(2)))
                .andExpect(jsonPath("_embedded.securities[0].shortname", containsString("Apple")));
    }


    /* Get the security by id - returns ok */
    @Test
    public void findOne_ShouldReturnSecurityById() throws Exception {
        mvc.perform(get(SECURITIES + 12441))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", containsString("АбрауДюрсо")))
                .andDo(print());
    }

    /* Delete the security by id - returns 404 */
    @Test
    public void deleteOne_ShouldDeleteSecurityById() throws Exception {
        mvc.perform(delete(SECURITIES + 999))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message",
                        containsStringIgnoringCase("security not found: " + 999)))
                .andDo(print());
    }

    /* Update the security by id */
    @Test
    public void update_ShouldUpdateSecurity() throws Exception {
        final int SECURITY_ID = 154676;

        SecurityDto securityDto = new SecurityDto();
        securityDto.setShortname("shortname");
        securityDto.setName("имя организации");

        mvc.perform(get(SECURITIES + SECURITY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", containsString("Apple")))
                .andDo(print());

        mvc.perform(patch(SECURITIES + SECURITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(securityDto)))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(get(SECURITIES + SECURITY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", containsString("shortname")))
                .andExpect(jsonPath("name", containsString("имя организации")))
                .andDo(print());
    }

    /* Delete the Security object by id - should delete the object by id */
    @Test
    public void delete_ShouldDeleteSecurityById() throws Exception {
        final int SECURITY_ID = 131568;

        mvc.perform(get(SECURITIES + SECURITY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortname", containsString("АСКО ао")))
                .andDo(print());

        mvc.perform(delete(SECURITIES + SECURITY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mvc.perform(get(SECURITIES + SECURITY_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", containsString("Not Found")));
    }
}
