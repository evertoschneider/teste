package com.example;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.controller.GerenteController;
import com.example.controller.UserController;
import com.example.model.Gerente;
import com.example.model.PasswordType;
import com.example.model.User;
import com.example.service.GerenteService;
import com.example.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {UserController.class, GerenteController.class}, secure = false)
public class SpringBootTesteApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	User mockUser = new User("User1", "P0", PasswordType.PREFERENCIAL);
	
	@MockBean
	private GerenteService gerenteService;
	
	Gerente mockGerente = new Gerente("Gerente1");
	
	@Test
	public void retrieveDetailsForUser() throws Exception{
		
		Mockito.when(userService.getUser(Mockito.anyLong())).thenReturn(mockUser);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/user/1").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{name:User1,senha:P0, tipoSenha:PREFERENCIAL}";
	
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void retrieveDetailsForGerente() throws Exception{
		
		Mockito.when(gerenteService.getGerente(Mockito.anyLong())).thenReturn(mockGerente);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/gerentes/gerente/1").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println("====================" + result.getResponse().getContentAsString());
		String expected = "{name:Gerente1}";
	
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
}