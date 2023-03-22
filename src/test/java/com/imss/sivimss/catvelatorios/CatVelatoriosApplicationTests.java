package com.imss.sivimss.catvelatorios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CatVelatoriosApplicationTests {

	@Test
	void contextLoads() {
		String result="test";
		CatVelatoriosApplication.main(new String[]{});
		assertNotNull(result);
	}

}
