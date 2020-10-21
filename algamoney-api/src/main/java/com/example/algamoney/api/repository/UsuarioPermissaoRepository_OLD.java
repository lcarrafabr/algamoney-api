package com.example.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.algamoney.api.model.Permissao;
import com.example.algamoney.api.model.UsuarioPermissao_OLD;

@Repository
public interface UsuarioPermissaoRepository_OLD extends JpaRepository<UsuarioPermissao_OLD, Long>{
	
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuario_permissao (codigo_usuario, codigo_permissao) values (?1 , ?2) ")
	public int salvarUsuarioPermissao(Long codigoUsuario, Long codigo_permissao);
	
	@Query(nativeQuery = true, value = "select up.*, p.descricao, u.nome, u.email "
			+ "from usuario_permissao up "
			+ "inner join usuario u on u.codigo = up.codigo_usuario "
			+ "inner join permissao p on p.codigo = up.codigo_permissao "
			+ "where u.codigo = ? ")
	public List<UsuarioPermissao_OLD> findUsuariosPermissoes(Long codigoUsuario);

}
