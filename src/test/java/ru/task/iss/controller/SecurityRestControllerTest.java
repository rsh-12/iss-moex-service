package ru.task.iss.controller;
/*
 * Date: 4/23/21
 * Time: 6:30 PM
 * */

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.task.iss.dto.SecurityDto;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SecurityRestControllerTest extends AbstractControllerClass {

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
                .andDo(print());
    }
}
