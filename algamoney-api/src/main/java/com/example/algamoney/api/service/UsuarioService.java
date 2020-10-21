package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario buscarusuarioPeloId(Long codigo) {
		
		Usuario usuarioSalvo = usuarioRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		return usuarioSalvo;
	}
	
	public Usuario atualizar(Long codigo, Usuario usuario) {
		
		Usuario usuarioSalvo = buscarusuarioPeloId(codigo);
		
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		
		return usuarioRepository.save(usuarioSalvo);
	}

}
