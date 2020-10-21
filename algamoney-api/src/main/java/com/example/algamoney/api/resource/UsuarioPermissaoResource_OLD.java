package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.UsuarioPermissao_OLD;
import com.example.algamoney.api.repository.UsuarioPermissaoRepository_OLD;

@RestController
@RequestMapping("usuario-permissao")
public class UsuarioPermissaoResource_OLD {
	
	@Autowired
	private UsuarioPermissaoRepository_OLD usuarioPermissaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping()
	public List<UsuarioPermissao_OLD> listar() {
		
		return usuarioPermissaoRepository.findAll();
	}
	
	@PostMapping
	@Transactional
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
	public ResponseEntity<Integer> atribuirPermissaoAoUsuario(@Valid @RequestBody UsuarioPermissao_OLD usuarioPermissao, HttpServletResponse response) {
		
		//UsuarioPermissao usuarioSalvo = usuarioPermissaoRepository.save(usuarioPermissao);
		
		int usuarioSalvo = usuarioPermissaoRepository.salvarUsuarioPermissao(usuarioPermissao.getCodigo_usuario(), usuarioPermissao.getCodigo_permissao());
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioPermissao.getCodigo_usuario()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@GetMapping("/lista-usuario/{codigo}")
	public List<UsuarioPermissao_OLD> listarUsuarioPermissao(@PathVariable Long codigo) {
		
		return usuarioPermissaoRepository.findUsuariosPermissoes(codigo);
		
	}

}
