package org.dalloz.kata.simpleusermanager;

import org.dalloz.kata.simpleusermanager.application.UserWebDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(scripts = {"/clean.sql","/data.sql"})
public class SpringRestUserControllerIT extends SpringConfigurationIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenCallGetUser_shouldSuccess() throws Exception {
        mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstname").value("Jacques"));
    }

    @Test
    public void whenCallCreateUser_shouldSuccess() throws Exception {
        //Given
        UserWebDto userWebDto = new UserWebDto("testFirstName","testLastName",
                "testmail@gmail.com");
        mvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userWebDto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstname").value("testFirstName"));
    }

    @Test
    public void whenCallCreateUserWithExistingMail_shouldFail() throws Exception {
        //Given
        UserWebDto userWebDto = new UserWebDto("testFirstName","testLastName",
                "jchirac@gmail.com");
        mvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userWebDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertInstanceOf(IllegalArgumentException.class,
                        result.getResolvedException()))
                .andExpect(result -> Assertions
                        .assertEquals("The following email is already used : jchirac@gmail.com",
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void whenCallUpdateUser_shouldSuccess() throws Exception {
        //GIVEN
        MvcResult result = mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstname").value("Jacques")).andReturn();

        List<UserWebDto> userWebDtoList =objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, UserWebDto.class));

        assertThat(userWebDtoList).isNotEmpty();
        UserWebDto userWebDto = userWebDtoList.get(0);
        userWebDto.setFirstname("Yoyo");
        userWebDto.setLastname("Rapido");

        //WHEN - THEN
        mvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userWebDto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(userWebDto.getId().toString()))
                .andExpect(jsonPath("$.firstname").value("Yoyo"));
    }

    @Test
    public void whenCallUpdateUserEmail_shouldFail() throws Exception {
        //GIVEN
        MvcResult result = mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstname").value("Jacques")).andReturn();

        List<UserWebDto> userWebDtoList =objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, UserWebDto.class));

        assertThat(userWebDtoList).isNotEmpty();
        UserWebDto userWebDto = userWebDtoList.get(0);
        userWebDto.setFirstname("Yoyo");
        userWebDto.setLastname("Rapido");
        userWebDto.setEmail("test@gmail.com");

        //WHEN - THEN
        mvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userWebDto)))
                .andExpect(status().isBadRequest())
                .andExpect(s -> Assertions.assertInstanceOf(IllegalArgumentException.class,
                        s.getResolvedException()))
                .andExpect(s -> Assertions
                        .assertEquals("Mail modification is not allowed",
                                Objects.requireNonNull(s.getResolvedException()).getMessage()));
    }

    @Test
    public void whenCallDeleteUser_shouldSuccess() throws Exception {
        //GIVEN
        MvcResult result = mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstname").value("Jacques")).andReturn();

        List<UserWebDto> userWebDtoList =objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, UserWebDto.class));

        assertThat(userWebDtoList).isNotEmpty();
        UserWebDto userWebDto = userWebDtoList.get(0);


        //WHEN
        mvc.perform(delete("/api/users/"+userWebDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //THEN
        mvc.perform(get("/api/users/"+userWebDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

    }
}
