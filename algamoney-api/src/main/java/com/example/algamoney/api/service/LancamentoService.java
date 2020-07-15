package com.example.algamoney.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(@Valid Lancamento lancamento) {

		Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).orElseThrow(() -> new EmptyResultDataAccessException(0));
		
		if(pessoa == null || pessoa.isInativo()) {
		throw new PessoaInexistenteOuInativaException();	
		}
		
		
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	
	
	private Lancamento buscarLancamentoExistente(Long codigo) {
	    Optional<Lancamento> lancamentoSalvoOpt = lancamentoRepository.findById(codigo);

	    // se o valor estiver presente, retorna o valor, senão lança uma exceção
	    return lancamentoSalvoOpt.orElseThrow(() -> new IllegalArgumentException()); 
	}
	
	
	
	private void validarPessoa(Lancamento lancamento) {
	    Pessoa pessoaOpt = null;  

	    if (lancamento.getPessoa().getCodigo() != null) {
	        pessoaOpt = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).orElseThrow(() -> new EmptyResultDataAccessException(0));
	    }
		
	    if (pessoaOpt == null  || pessoaOpt.isInativo()) {
	        throw new PessoaInexistenteOuInativaException();
	    }
	}

}
