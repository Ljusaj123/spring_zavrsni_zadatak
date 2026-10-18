package com.example.spring_zavrsni_zadatak.controller;

import com.example.spring_zavrsni_zadatak.dto.AuthRequestDTO;
import com.example.spring_zavrsni_zadatak.dto.JwtResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProgramControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    private String accessToken;

    @BeforeEach
    void setUp() {
        AuthRequestDTO authRequest = new AuthRequestDTO();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin");

        if (Optional.ofNullable(accessToken).isEmpty()) {
            JwtResponseDTO jwtResponse = authController.authenticateAndGetToken(authRequest);
            accessToken = jwtResponse.getAccessToken();
        }
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/program")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetByNaziv() throws Exception {
        mockMvc.perform(get(
                        "/program/{naziv}",
                        "Programiranje u Javi")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveProgram() throws Exception {
        String json = """
                {
                    "naziv": "Testni program",
                    "csvet": 5
                }
                """;

        mockMvc.perform(post("/program")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        "Program je uspješno kreiran"));
    }

    @Test
    void testUpdateProgram() throws Exception {
        String json = """
                {
                    "naziv": "Programiranje u Javi",
                    "csvet": 6
                }
                """;

        mockMvc.perform(put("/program/{programId}", 1)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Program je uspješno ažuriran"));
    }

    @Test
    void testDeleteProgram() throws Exception {
        mockMvc.perform(delete("/program/{programId}", 1)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Program je uspješno obrisan"));
    }
}