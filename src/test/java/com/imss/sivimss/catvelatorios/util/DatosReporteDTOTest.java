package com.imss.sivimss.catvelatorios.util;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatosReporteDTOTest {
    @Test
    public void datosReporteDTO() throws Exception {
        Map<String, Object> datos=new HashMap<>();
        datos.put("nombre","vacio");
        DatosReporteDTO reporteDTO=new DatosReporteDTO(datos,"reporte1","pdf");
        assertNotNull(reporteDTO.getDatos());
        assertNotNull(reporteDTO.getNombreReporte());
        assertNotNull(reporteDTO.getTipoReporte());
    }
}
