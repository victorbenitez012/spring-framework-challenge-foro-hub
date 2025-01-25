package forohub.forohub;

import ch.qos.logback.classic.encoder.JsonEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ForohubApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		// Verifica que el contexto de la aplicación se cargue correctamente
	}

	@Test
	void testLoginEndpoint() throws Exception {

		// Cuerpo de la solicitud con credenciales válidas
		String loginRequest = """
                {
                    "login": "usuarioEjemplo",
                    "contrasena": "contrasenaEjemplo"
                }
                """;

		// Realiza la solicitud POST al endpoint /login
		mockMvc.perform(post("/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginRequest))
				.andExpect(status().isOk()) // Verifica que el estado de respuesta sea 200
				.andExpect(jsonPath("$.token").exists()); // Verifica que el token esté presente en la respuesta
	}

	@Test
	void testLoginEndpointWithInvalidCredentials() throws Exception {
		// Cuerpo de la solicitud con credenciales inválidas
		String loginRequest = """
                {
                    "login": "usuarioInvalido",
                    "contrasena": "contrasenaInvalida"
                }
                """;

		// Realiza la solicitud POST al endpoint /login
		mockMvc.perform(post("/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginRequest))
				.andExpect(status().isForbidden()); // Verifica que el estado de respuesta sea 403
	}
}
