package com.algaworks.comercial.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.comercial.model.Oportunidade;
import com.algaworks.comercial.repository.OportunidadeRepository;

@RestController
@RequestMapping("oportunidades")
public class OportunidadeController {
	
	//injecao dependencia spring
	@Autowired
	private OportunidadeRepository oportunidades;

	@GetMapping
	public List<Oportunidade> listar() {
		return this.oportunidades.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {
		Optional<Oportunidade> op = this.oportunidades.findById(id);
		
		if(op.equals("")) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(op.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade adicionar(@RequestBody Oportunidade op) {
		return this.oportunidades.save(op);
	}

}
