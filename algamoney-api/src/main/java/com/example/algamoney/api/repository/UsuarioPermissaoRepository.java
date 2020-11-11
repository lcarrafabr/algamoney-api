package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.algamoney.api.model.UsuarioPermissao;

@Repository
public interface UsuarioPermissaoRepository extends JpaRepository<UsuarioPermissao, Long>{
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from usuario_permissao " + 
			"where codigo_usuario = ?1 ")
	public void DeleteAllPermissaoByUser(Long codigoUsuario);

}
