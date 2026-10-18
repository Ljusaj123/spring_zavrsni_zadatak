package com.example.spring_zavrsni_zadatak.controller;

import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;
import com.example.spring_zavrsni_zadatak.service.PolaznikService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

class PolaznikControllerTest {

    @Mock
    private PolaznikService polaznikService;

    @InjectMocks
    private PolaznikController polaznikController;

    private MockMvc mockMvc;

    private List<PolaznikDTO> polaznikList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(polaznikController).build();

        PolaznikDTO polaznikDTO1 = new PolaznikDTO("Ana", "Anić");
        PolaznikDTO polaznikDTO2 = new PolaznikDTO("Mate", "Matić");

        polaznikList = Arrays.asList(polaznikDTO1, polaznikDTO2);

    }

    @Test
    void testGetAll() throws Exception {
        when(polaznikService.getAll())
                .thenReturn(polaznikList);

        mockMvc.perform(get("/polaznik"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(2)));

        verify(polaznikService).getAll();
    }

    @Test
    void testGetById_Found() throws Exception {
        when(polaznikService.getById(1))
                .thenReturn(Optional.ofNullable(polaznikList.get(0)));

        mockMvc.perform(get("/polaznik/{id}", 1))
                .andExpect(status().isOk());

        verify(polaznikService).getById(1);
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(polaznikService.getById(99))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Polaznik ne postoji"
                ));

        mockMvc.perform(get("/polaznik/{id}", 99))
                .andExpect(status().isNotFound());

        verify(polaznikService).getById(99);
    }

    @Test
    void testSavePolaznik() throws Exception {
        String polaznikJson =
                """
                        {
                            "ime": "Ana",
                            "prezime": "Anić"
                        }
                        """;
        mockMvc.perform(post("/polaznik")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(polaznikJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        "Polaznik je uspješno kreiran"));

        verify(polaznikService).savePolaznik(any(PolaznikDTO.class));
    }

    @Test
    void testUpdatePolaznik() throws Exception {
        String polaznikJson =
                """
                        {
                            "ime": "Ana",
                            "prezime": "Stanić"
                        }
                        """;
        mockMvc.perform(put("/polaznik/{polaznikId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(polaznikJson))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Polaznik je uspješno ažuriran"));

        verify(polaznikService)
                .updatePolaznik(any(PolaznikDTO.class), eq(1));
    }

    @Test
    void testDeletePolaznik() throws Exception {
        mockMvc.perform(delete("/polaznik/{polaznikId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Polaznik je uspješno obrisan"));

        verify(polaznikService).deletePolaznikById(1);
    }
}
