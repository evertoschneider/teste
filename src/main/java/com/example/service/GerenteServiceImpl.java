package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Gerente;
import com.example.repository.GerenteRepository;

@Service
public class GerenteServiceImpl implements GerenteService {
	
	@Autowired
	private GerenteRepository gerenteRepository;
	
	@Override
	public void saveGerente(Gerente gerente) {
		gerenteRepository.save(gerente);
	}

	@Override
	public void updateGerente(Gerente gerente) {
		gerenteRepository.save(gerente);
	}

	@Override
	public void deleteGerente(long gerenteId) {
		gerenteRepository.delete(getGerente(gerenteId));

	}

	@Override
	public Gerente getGerente(long gerenteId) {
		for(Gerente gerente : getAllGerentes()) {
			if(gerente.getId().equals(gerenteId)) {
				return gerente;
			}
		}
		return null;
	}

	@Override
	public Gerente getGerenteByName(String name) {
		for(Gerente gerente : getAllGerentes()) {
			if(gerente.getName().equals(name)) {
				return gerente;
			}
		}
		return null;
	}

	@Override
	public List<Gerente> getAllGerentes() {
		List<Gerente> gerentes = new ArrayList<>();
		gerenteRepository.findAll().forEach(gerentes::add);
		return gerentes;
	}

	@Override
	public void deleteAllGerentes() {
		gerenteRepository.deleteAll();
	}

}