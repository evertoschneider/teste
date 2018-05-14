package com.example.service;

import java.util.List;

import com.example.model.Gerente;


public interface GerenteService {
	
	void saveGerente(Gerente gerente);
	
	void updateGerente(Gerente gerente);
	
	void deleteGerente(long gerenteId);
	
	Gerente getGerente(long gerenteId);
	
	Gerente getGerenteByName(String name);
	
	List<Gerente> getAllGerentes();
	
	void deleteAllGerentes();
}
