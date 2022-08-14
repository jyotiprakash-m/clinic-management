package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.repository.DiagnosticServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DiagnosticServiceRestControllerTest {
	@Autowired
	MockMvc mvc;
	@MockBean
	DiagnosticServiceRepository repo;

//	Add new tests in the list of tests
	@Test
	public void testAddDiagnostic() throws Exception {
		Diagnostic diagnostic = new Diagnostic("X-Ray");
		when(repo.save(diagnostic)).thenReturn(diagnostic);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(diagnostic);
		mvc.perform(post("/diagnostic/save").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Diagnostic is saved"));
	}

//	************ Data validation test  *************
	@Test
	public void testEmptyNameAddDiagnostic() throws Exception {
		Diagnostic diagnostic = new Diagnostic("");
		when(repo.save(diagnostic)).thenReturn(diagnostic);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(diagnostic);
		mvc.perform(post("/diagnostic/save").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Diagnostic name is required"));
	}

//	Update test (Positive)
	@Test
	public void testUpdateDiagnostic() throws Exception{
		int id=1;
		Diagnostic diagnostic = new Diagnostic(1,"X-Ray");
		Optional<Diagnostic> optDiagnostic = Optional.of(diagnostic);
		Mockito.when(repo.findById(1)).thenReturn(optDiagnostic);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(diagnostic);
		mvc.perform(put("/diagnostic/update/" + id).contentType(MediaType.APPLICATION_JSON).content(value))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Diagnostic is updated"));
	}
//	Update test (Negative)
	@Test
	public void negativeTestUpdateDiagnostic() throws Exception{
		int id=2;
		Diagnostic diagnostic = new Diagnostic(1,"X-Ray");
		Optional<Diagnostic> optDiagnostic = Optional.of(diagnostic);
		Mockito.when(repo.findById(1)).thenReturn(optDiagnostic);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(diagnostic);
		mvc.perform(put("/diagnostic/update/" + id).contentType(MediaType.APPLICATION_JSON).content(value))
		.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Not found"));
	}
//	Remove tests from the list of tests (Positive)
	@Test
	public void testDeleteDiagnostic() throws Exception{
		int id=1;
		Diagnostic diagnostic = new Diagnostic(1,"X-Ray");
		Optional<Diagnostic> optDiagnostic = Optional.of(diagnostic);
		Mockito.when(repo.findById(1)).thenReturn(optDiagnostic);
		mvc.perform(delete("/diagnostic/delete/" + id).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Diagnostic is deleted"));
	}
//	Remove tests from the list of tests (Negative)
	@Test
	public void negativeTestDeleteDiagnostic() throws Exception{
		int id=2;
		Diagnostic diagnostic = new Diagnostic(1,"X-Ray");
		Optional<Diagnostic> optDiagnostic = Optional.of(diagnostic);
		Mockito.when(repo.findById(1)).thenReturn(optDiagnostic);
		mvc.perform(delete("/diagnostic/delete/" + id).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Not found"));
	}
//	List all the diagnostic tests available in the clinic
	@Test
	public void testGetAllDiagnostic() throws Exception{
		Diagnostic diagnostic = new Diagnostic(1,"X-Ray");
		List<Diagnostic> diagnostics = new ArrayList<>();
		diagnostics.add(diagnostic);
		Mockito.when(repo.findAll()).thenReturn(diagnostics);
		mvc.perform(get("/diagnostic/get").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0].name").value("X-Ray"));
		
	}
}
