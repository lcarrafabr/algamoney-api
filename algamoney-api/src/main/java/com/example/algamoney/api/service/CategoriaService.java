package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria atualizarCategoria(Long codigo, Categoria categoria) {
		
		Categoria categoriaSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		return categoriaRepository.save(categoriaSalva);
	}
	
	private Categoria buscaPorId(Long codigo) {
		
		Categoria categoriaSalva = categoriaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return categoriaSalva;
	}

}
