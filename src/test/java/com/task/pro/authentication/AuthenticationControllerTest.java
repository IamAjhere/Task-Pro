package com.task.pro.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.pro.exceptions.CustomExceptionStore;
import com.task.pro.user.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authService;

    @Test
    @Transactional
    public void authenticationController_Register_ValidRegister() throws Exception {
        RegisterRequest validRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", Role.INDIVIDUAL, "weeak");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(validRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
    }

    @Test
    @Transactional
    public void authenticationController_Register_InvalidTeamMemberRoleRegister() throws Exception {
        RegisterRequest validRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", Role.TEAM_MEMBER, "Password@123");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(validRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(CustomExceptionStore.TEAM_MEMBER_NOT_ALLOWED.getStatusCode()))
                .andExpect(jsonPath("$.code").value(CustomExceptionStore.TEAM_MEMBER_NOT_ALLOWED.getCode()))
                .andExpect(jsonPath("$.message").value(CustomExceptionStore.TEAM_MEMBER_NOT_ALLOWED.getMessage()))
                .andReturn();
    }


    @Test
    @Transactional
    public void authenticationController_Register_InvalidRegisterWithSameEmail() throws Exception {
        RegisterRequest validRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", Role.INDIVIDUAL, "Password@123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(validRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(validRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(CustomExceptionStore.USER_EXISTS.getStatusCode()))
                .andExpect(jsonPath("$.code").value(CustomExceptionStore.USER_EXISTS.getCode()))
                .andExpect(jsonPath("$.message").value(CustomExceptionStore.USER_EXISTS.getMessage()))
                .andReturn();
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void authenticationController_Register_InvalidPasswordRegister() throws Exception {
        RegisterRequest invalidRequest = new RegisterRequest("John", "Doe", "test@etest.com", Role.INDIVIDUAL, "weakpassword");


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andReturn();
    }



    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void authenticationController_Register_InvalidEmailRegister() throws Exception {
        RegisterRequest invalidRequest = new RegisterRequest("John", "Doe", "invalid-email", Role.INDIVIDUAL, "Password@123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andReturn();
    }


    @Test
    @Transactional
    public void authenticationController_Authenticate_ValidAuthentication() throws Exception {
        RegisterRequest validRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", Role.INDIVIDUAL, "Password@123");
        AuthenticationRequest validLogin = new AuthenticationRequest("john.doe@example.com", "Password@123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(validRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .content(objectMapper.writeValueAsString(validLogin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
    }

    @Test
    @Transactional
    public void authenticationController_Authenticate_InValidAuthentication() throws Exception {
        RegisterRequest validRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", Role.INDIVIDUAL, "Password@123");
        AuthenticationRequest validLogin = new AuthenticationRequest("john.doe@example.com", "123123123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(validRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .content(objectMapper.writeValueAsString(validLogin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(CustomExceptionStore.INVALID_LOGIN.getStatusCode()))
                .andExpect(jsonPath("$.code").value(CustomExceptionStore.INVALID_LOGIN.getCode()))
                .andExpect(jsonPath("$.message").value(CustomExceptionStore.INVALID_LOGIN.getMessage()))
                .andReturn();
    }

}
