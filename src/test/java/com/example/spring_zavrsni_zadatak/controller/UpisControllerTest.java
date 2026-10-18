package com.example.spring_zavrsni_zadatak.controller;

import com.example.spring_zavrsni_zadatak.dto.PolaznikDTO;
import com.example.spring_zavrsni_zadatak.dto.UpisDTO;
import com.example.spring_zavrsni_zadatak.service.UpisService;
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

class UpisControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UpisService upisService;
    @InjectMocks
    private UpisController upisController;

    private List<UpisDTO> upisList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(upisController).build();

        UpisDTO upisDTO1 = new UpisDTO(
                "Ana Anić",
                "Programiranje u Javi"
        );

        UpisDTO upisDTO2 = new UpisDTO(
                "Mate Matić",
                "Programiranje u Pythonu"
        );

        upisList = Arrays.asList(upisDTO1, upisDTO2);
    }

    @Test
    void testGetAll() throws Exception {
        when(upisService.getAll())
                .thenReturn(upisList);

        mockMvc.perform(get("/upis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(2)));

        verify(upisService).getAll();
    }

    @Test
    void testGetById_Found() throws Exception {
        when(upisService.getById(1))
                .thenReturn(Optional.ofNullable(upisList.get(0)));

        mockMvc.perform(get("/upis/{id}", 1))
                .andExpect(status().isOk());

        verify(upisService).getById(1);
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(upisService.getById(99))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Upis ne postoji"
                ));

        mockMvc.perform(get("/upis/{id}", 99))
                .andExpect(status().isNotFound());

        verify(upisService).getById(99);
    }

    @Test
    void testSaveUpis() throws Exception {
        String upisJson = """
                {
                    "polaznik": "Ana Anić",
                    "nazivPrograma": "Java programiranje"
                }
                """;

        mockMvc.perform(post("/upis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(upisJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        "Upis je uspješno kreiran"));

        verify(upisService).saveUpis(any(UpisDTO.class));
    }

    @Test
    void testUpdateUpis() throws Exception {
        String upisJson = """
                {
                    "polaznik": "Ana Anić",
                    "nazivPrograma": "Java programiranje"
                }
                """;
        mockMvc.perform(put("/upis/{upisId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(upisJson))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Upis je uspješno ažuriran"));

        verify(upisService)
                .updateUpis(any(UpisDTO.class), eq(1));
    }

    @Test
    void testDeleteUpis() throws Exception {
        mockMvc.perform(delete("/upis/{upisId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Upis je uspješno obrisan"));

        verify(upisService).deleteUpisById(1);
    }
}