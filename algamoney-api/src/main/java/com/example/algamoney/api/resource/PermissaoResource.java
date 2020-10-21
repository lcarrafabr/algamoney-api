package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Permissao;
import com.example.algamoney.api.repository.PermissoesRepository;

@RestController
@RequestMapping("permissoes")
public class PermissaoResource {
	
	@Autowired
	private PermissoesRepository permissoesRepository;
	
	@GetMapping("/listar")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public List<Permissao> listarPermissoes() {
		
		return permissoesRepository.findAll();
	}

}
