package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.Gerente;
import com.example.model.User;
import com.example.service.GerenteService;


@RestController
@RequestMapping("/api/gerentes")
public class GerenteController {
	
	public static final Logger logger = LoggerFactory.getLogger(GerenteController.class);
	
	@Autowired
	GerenteService gerenteService;
	
	@Autowired
	UserController userController;
	
	User userAtual = null;
	
	@RequestMapping("/senhaAtual/")
	public String senhaAtual() {
		
		if(!userController.getUsersPref().isEmpty()) {
			userAtual = userController.getUsersPref().get(0);
		} else if(!userController.getUsersNorm().isEmpty()){
			userAtual = userController.getUsersNorm().get(0);
		}
		
		if (userAtual == null) {
			return "Aguardando novas senhas";
		}
		
		return "A senha atual é " + userAtual.getSenha() + ". User: " + userAtual.getName() + ".";
	}
	
	@RequestMapping("/novaSenha/")
	public String gerarNovaSenha() {
		
		if (userAtual == null) {
			return "Aguardando novas senhas";
		}
		
		if(userAtual.getSenha().startsWith("P")) {
			userController.getUsersPref().remove(0);
		} else if(userAtual.getSenha().startsWith("N")) {
			userController.getUsersNorm().remove(0);
		}
		
		return "Uma nova senha foi gerada";
	}
	
	@RequestMapping(value = "/gerente/", method = RequestMethod.POST)
	public ResponseEntity<?> saveGerente(@RequestBody Gerente gerente, UriComponentsBuilder builder) {
		logger.info("Saving Gerente: {}", gerente);
		
		gerenteService.saveGerente(gerente);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/api/gerentes/gerente/{id}").buildAndExpand(gerente.getId()).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/gerente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateGerente(@PathVariable("id") long id, @RequestBody Gerente gerente) {
		logger.info("Updating Gerente: {}", id);

		Gerente currentGerente = gerenteService.getGerente(id);
		
		if (currentGerente == null) {
			logger.error("Gerente not found");
			return new ResponseEntity(new CustomErrorType("Error updating. Gerente not found"), HttpStatus.NOT_FOUND);
		}
		
		currentGerente.setName(gerente.getName());
		gerenteService.updateGerente(currentGerente);
		
		return new ResponseEntity<Gerente>(currentGerente, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gerente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteGerente(@PathVariable("id") long id) {
		logger.info("Deleting User: {}", id);
		
		Gerente gerente = gerenteService.getGerente(id);
		
		if(gerente == null) {
			logger.error("Gerente not found");
			return new ResponseEntity(new CustomErrorType("Error deleting. Gerente not found"), HttpStatus.NOT_FOUND);
		}
		
		gerenteService.deleteGerente(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/gerente/", method = RequestMethod.DELETE)
	public ResponseEntity<Gerente> deleteAllGerentes() {
		logger.info("Deleting all gerentes");
		
		gerenteService.deleteAllGerentes();
		return new ResponseEntity<Gerente>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/gerente/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getGerente(@PathVariable("id") long id) {
		logger.info("Getting gerente: {}", id);
		
		Gerente gerente = gerenteService.getGerente(id);
		
		if(gerente == null) {
			logger.info("Gerente not found");
			return new ResponseEntity(new CustomErrorType("Gerente not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Gerente>(gerente, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gerenteList", method = RequestMethod.GET)
	public ResponseEntity<List<Gerente>> getAllGerentes() {
		List<Gerente> gerentes = gerenteService.getAllGerentes();
		
		for(User user : userController.getUsersNorm()) {
			System.out.println(user.getSenha());
		}
		
		if(gerentes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Gerente>>(gerentes, HttpStatus.OK);
	}

}
