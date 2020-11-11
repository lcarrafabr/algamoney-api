package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.model.UsuarioPermissao;
import com.example.algamoney.api.repository.UsuarioPermissaoRepository;

@RestController
@RequestMapping("/user_permition")
public class UsuarioPermissaoResource {
	
	@Autowired
	private UsuarioPermissaoRepository usuarioPermissaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<UsuarioPermissao> listar() {
		
		return usuarioPermissaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<UsuarioPermissao> criarUserPermissao(@Valid @RequestBody UsuarioPermissao usuarioPermissao, HttpServletResponse response) {
		
		UsuarioPermissao usuarioPermissaoSalva = usuarioPermissaoRepository.save(usuarioPermissao);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioPermissao.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioPermissaoSalva);
	}
	
	@DeleteMapping("/deletar-all-permition/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA')")
	public void removerTodasPermissoesUsuario(@PathVariable Long codigo) {
		
		usuarioPermissaoRepository.DeleteAllPermissaoByUser(codigo);
		
	}

}
