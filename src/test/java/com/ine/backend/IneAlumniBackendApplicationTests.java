package com.ine.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Disabled("Disabled until full application context test configuration is complete")
class IneAlumniBackendApplicationTests {

	@Test
	void contextLoads() {
	}
}
