package com.imss.sivimss.catvelatorios.util;

import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UsuarioDtoTest {
    @Test
    public void usuarioDtoTest() throws Exception {
        UsuarioDto request=new UsuarioDto();
        assertNull(request.getIdUsuario());
        assertNull(request.getDesRol());
        assertNull(request.getNombre());
        assertNull(request.getNombre());
    }
}
