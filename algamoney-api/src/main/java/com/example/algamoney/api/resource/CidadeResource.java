package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.repository.CidadeRepository;
import com.example.algamoney.api.service.CidadesService;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CidadesService cidadeService;
	
	@GetMapping
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
	public List<Cidade> pesquisar(@RequestParam Long estado) {
		
		  return cidadeRepository.findByEstadoCodigo(estado);
	}
	
	@GetMapping("/listar")
	public List<Cidade> listar() {
		
		return cidadeRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Cidade> cadastrarCidade(@Valid @RequestBody Cidade cidade, HttpServletResponse response) {
		
		Cidade cidadeSalva = cidadeRepository.save(cidade);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, cidadeSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalva);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Cidade> buscaPorId(@PathVariable Long codigo){
		return cidadeRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCidade(@PathVariable Long codigo) {
		
		cidadeRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cidade> atualizarCidade(@PathVariable Long codigo, @Valid @RequestBody Cidade cidade) {
		
		Cidade cidadeSalva = cidadeService.atualizarCidade(codigo, cidade);
		return ResponseEntity.ok(cidadeSalva);
	}

}
