package com.imss.sivimss.catvelatorios.controller;

import com.imss.sivimss.catvelatorios.base.BaseTest;
import com.imss.sivimss.catvelatorios.client.MockModCatalogosClient;
import com.imss.sivimss.catvelatorios.security.jwt.JwtTokenProvider;
import com.imss.sivimss.catvelatorios.util.JsonUtil;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockserver.model.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WithMockUser(username="10796223", password="123456",roles = "ADMIN")
public class PanteonesControllerTest extends BaseTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }
/*
    @Test
    @DisplayName("crear Panteon")
    @Order(1)
    public void crearPanteonlOK() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myToken=jwtTokenProvider.createTokenTest(authentication.getPrincipal().toString());
        MockModCatalogosClient.genericoConsultaExistePanteon(HttpStatusCode.OK_200, JsonUtil.readFromJson("json/request/crear_consulta_existe_panteon.json"), JsonUtil.readFromJson("json/request/existe_panteon.json"), myToken, mockServer);
        this.mockMvc.perform(post("/panteones/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer " + token)
                        .content(JsonUtil.readFromJson("json/request/crear_panteon.json"))
                        //.with(SecurityMockMvcRequestPostProcessors.user("10796223").password("123456").roles("ADMIN"))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is5xxServerError())
        ;
    }

    @Test
    @DisplayName("actualizar Panteon")
    @Order(2)
    public void actualizarPanteonlOK() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myToken=jwtTokenProvider.createTokenTest(authentication.getPrincipal().toString());
        MockModCatalogosClient.genericoActualizar(HttpStatusCode.OK_200, JsonUtil.readFromJson("json/request/sql_modificar_panteon.json"), JsonUtil.readFromJson("json/request/existe_panteon.json"), myToken, mockServer);
        this.mockMvc.perform(post("/panteones/modificar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer " + myToken)
                        .content(JsonUtil.readFromJson("json/request/modificar_panteon.json"))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
        ;
    }


    @Test
    @DisplayName("cambiar Estatus Panteon")
    @Order(3)
    public void cambiarEstatusPanteonOK() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myToken=jwtTokenProvider.createTokenTest(authentication.getPrincipal().toString());
        MockModCatalogosClient.genericoActualizar(HttpStatusCode.OK_200, JsonUtil.readFromJson("json/request/sql_modificar_panteon.json"), JsonUtil.readFromJson("json/response/response_modificar_panteon_mock.json"), myToken, mockServer);
        this.mockMvc.perform(post("/panteones/cambiar-estatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer " + myToken)
                        .content(JsonUtil.readFromJson("json/request/modificar_panteon.json"))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
        ;
    }*/

    @Test
    @DisplayName("buscar Panteon")
    @Order(4)
    public void buscarPanteonOK() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myToken=jwtTokenProvider.createTokenTest(authentication.getPrincipal().toString());
        MockModCatalogosClient.buscarPanteon(HttpStatusCode.OK_200, JsonUtil.readFromJson("json/request/buscar_panteon_mock.json"), JsonUtil.readFromJson("json/response/response_buscar_panteon_mock.json"), myToken, mockServer);
        this.mockMvc.perform(post("/panteones/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer " + myToken)
                        .content(JsonUtil.readFromJson("json/request/buscar_panteon_controller.json"))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is5xxServerError())
        ;
    }
}
