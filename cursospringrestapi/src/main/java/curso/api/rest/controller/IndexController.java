package curso.api.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/{id}/relatoriopdf", produces = "application/json")
	public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		System.out.println("Faz de conta que aqui tem a rotina do relatorio");
		
		/*O retorno seria um relatorio*/
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
//		return ResponseEntity.ok(usuario); <<< Apenas para um unico objeto e não uma lista.
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable(value = "id") Long id) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
//		return ResponseEntity.ok(usuario); <<< Apenas para um unico objeto e não uma lista.
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<Usuario>> usuario(){
		
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
		
		String login = usuario.getLogin().toUpperCase();
		usuario.setNome(usuario.getNome().toUpperCase());
		usuario.setLogin(login);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
//		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); //<<<==== Aula do professor Alex
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo); //<<<==== Aula da AlgaWorks
	}
	
	@PutMapping(value = "/{iduser}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long iduser, @RequestBody Usuario usuario){
		
		String login = usuario.getLogin().toUpperCase();
		usuario.setLogin(login);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
//		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); //<<<==== Aula do professor Alex
		return ResponseEntity.status(HttpStatus.OK).body(usuarioSalvo); //<<<==== Aula da AlgaWorks
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		
		usuarioRepository.deleteById(id);

	}
	
	
	
//	@GetMapping(value = "/", produces = "application/json")
//	@RequestMapping(value = "/usuario")
//	public ResponseEntity init(@RequestParam(value = "nome", defaultValue = "Nome não informado.") String nome,
//							@RequestParam(value = "salario", defaultValue = "Salario não informado.") Long salario) {
//		
//		System.out.println("Parametro recebido: " + nome + " e salário: " + salario);
//		
//		return new ResponseEntity("Olá usuário. Seu nome é: " + nome  + " e salário: " + salario, HttpStatus.OK);
//	}

}
