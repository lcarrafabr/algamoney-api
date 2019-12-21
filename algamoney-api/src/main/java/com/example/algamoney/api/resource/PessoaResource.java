package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> listar(){
		
		return pessoaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity buscarPessoaPorId(@PathVariable Long codigo) {
		
		Optional pessoa = this.pessoaRepository.findById(codigo);

		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		
		pessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public Pessoa atualizarPessoa(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		
		Pessoa pessoaSalva = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		/**BeansUtils pode ser usado para ajudar a tratar od dados para atualziar
		 * Source: A fonte dos dados - no caso da classe pessoas
		 * target: Para onde irei mandar os dados - no caso para minha variavel pessoaSalva
		 * ignoreProperties: qual dado devo ignorar - no caso o codigo que é PK*/
		//BeanUtils.copyProperties(source, target, ignoreProperties);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		
		return this.pessoaRepository.save(pessoaSalva);
	}
	

}
