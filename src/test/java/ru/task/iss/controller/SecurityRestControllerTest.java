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
                .andExpect(jsonPath("_embedded.fields.size()", is(1)))
                .andReturn().getResponse()
    }

    /* Get all securities - returns max 10 elems by default */
    @Test
    public void findAllSecurities_ShouldReturnAllSecurities() throws Exception {
        mvc.perform(get(SECURITIES))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // todo: test the view method
    // todo: test the findAllSecurities with params


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
}
