package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.repository.CidadeRepository;

@Service
public class CidadesService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cidade atualizarCidade(Long codigo, Cidade cidade) {
		
		Cidade cidadeSalva = buscaPorId(codigo);
		
		BeanUtils.copyProperties(cidade, cidadeSalva, "codigo");
		return cidadeRepository.save(cidadeSalva);
	}
	
	private Cidade buscaPorId(Long codigo) {
		
		Cidade cidadeSalva = cidadeRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return cidadeSalva;
	}

}
