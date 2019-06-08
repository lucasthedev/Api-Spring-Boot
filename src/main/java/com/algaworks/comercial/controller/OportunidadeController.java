package com.algaworks.comercial.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

		if(!op.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(op.get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade op) {
		Optional<Oportunidade> oportunidadeExistente = 
				this.oportunidades.findByDescricaoAndNomeProspecto(op.getDescricaoOportunidade(), op.getNomeProspecto());

		if(oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "já existe uma oportunidade para este prospecto com a mesma descricação");
		}

		return this.oportunidades.save(op);
	}

	@PutMapping("/alterar")
	public Oportunidade alterar(Oportunidade op) {

		Optional<Oportunidade> oportunidadeExistente = this.oportunidades.findById(op.getId());

		if(oportunidadeExistente.isPresent()) {
			this.oportunidades.save(op);
		}
		
		return op;

	}
	
	@DeleteMapping("excluir/{id}")
	public ResponseEntity<Oportunidade> deletar(@PathVariable Long id) {
		Optional<Oportunidade> op = this.oportunidades.findById(id);
		
		if(op.isPresent()) {
			this.oportunidades.deleteById(id);
			return ResponseEntity.ok(op.get());
		}
		
		return ResponseEntity.notFound().build();
		
	}

}
