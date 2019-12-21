package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizarPessoa(Long codigo, Pessoa pessoa) {
		
		Pessoa pessoaSalva = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		/**BeansUtils pode ser usado para ajudar a tratar od dados para atualziar
		 * Source: A fonte dos dados - no caso da classe pessoas
		 * target: Para onde irei mandar os dados - no caso para minha variavel pessoaSalva
		 * ignoreProperties: qual dado devo ignorar - no caso o codigo que é PK*/
		//BeanUtils.copyProperties(source, target, ignoreProperties);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		
		return pessoaRepository.save(pessoaSalva);
		
		
		
	}

}
