package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.UsuarioRepository;
import com.example.algamoney.api.security.util.GeradorSenha;
import com.example.algamoney.api.service.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("listar-usuarios")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public List<Usuario> listarUsuarios(){
		
		return usuarioRepository.findAll();
		
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public ResponseEntity buscarUsuarioPorId(@PathVariable Long codigo) {
		
		Optional usuario = usuarioRepository.findById(codigo);
		
		return usuario.isPresent() ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		
		
		
		//System.out.println("Senha antes" + usuarioSalvo.getSenha());
		
		String senhabruta = usuario.getSenha();	
		String senhaCritografada = GeradorSenha.encoder(senhabruta);
		usuario.setSenha(senhaCritografada);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		//System.out.println("Senha cripto" + senhaCritografada);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuario.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @Valid @RequestBody Usuario usuario) {
		
		Usuario usuarioAlterado = usuarioService.atualizar(codigo, usuario);
		
		return ResponseEntity.ok(usuarioAlterado);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA')")
	public void deletarUsuario(@PathVariable Long codigo) {
		
		usuarioRepository.deleteById(codigo);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public List<Usuario> pesquisarUserPorNome(@RequestParam(required = false, defaultValue = "") String nome) {
		
		return usuarioRepository.findByNomeContaining(nome);
	}

}
