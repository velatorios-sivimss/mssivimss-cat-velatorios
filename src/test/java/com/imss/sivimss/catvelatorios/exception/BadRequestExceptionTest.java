package com.imss.sivimss.catvelatorios.exception;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.imss.sivimss.catvelatorios.util.UtUtils.createInstance;
import static com.imss.sivimss.catvelatorios.util.UtUtils.setField;
import static org.junit.Assert.assertNull;

public final class BadRequestExceptionTest {
    @Test
    public void testGetEstado_ReturnCodigo() throws Exception {
        BadRequestException badRequestException = ((BadRequestException) createInstance("com.imss.sivimss.catvelatorios.exception.BadRequestException"));
        setField(badRequestException, "com.imss.sivimss.catvelatorios.exception.BadRequestException", "codigo", null);

        HttpStatus actual = badRequestException.getEstado();

        assertNull(actual);
    }

    @Test
    public void testGetMensaje_ReturnMensaje() throws Exception {
        BadRequestException badRequestException = ((BadRequestException) createInstance("com.imss.sivimss.catvelatorios.exception.BadRequestException"));
        setField(badRequestException, "com.imss.sivimss.catvelatorios.exception.BadRequestException", "mensaje", null);

        String actual = badRequestException.getMensaje();

        assertNull(actual);
    }
}