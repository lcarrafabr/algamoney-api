package com.example.algamoney.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_permissao")
public class UsuarioPermissao_OLD implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long codigo_usuario;

	private Long codigo_permissao;
//	
//	private String email;
//	
//	private String descricao;

	public Long getCodigo_usuario() {
		return codigo_usuario;
	}

	public void setCodigo_usuario(Long codigo_usuario) {
		this.codigo_usuario = codigo_usuario;
	}

	public Long getCodigo_permissao() {
		return codigo_permissao;
	}

	public void setCodigo_permissao(Long codigo_permissao) {
		this.codigo_permissao = codigo_permissao;
	}
	
//	public String getEmail() {
//		return email;
//	}
//	
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	
//	
//
//	public String getDescricao() {
//		return descricao;
//	}
//
//	public void setDescricao(String descricao) {
//		this.descricao = descricao;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo_permissao == null) ? 0 : codigo_permissao.hashCode());
		result = prime * result + ((codigo_usuario == null) ? 0 : codigo_usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioPermissao_OLD other = (UsuarioPermissao_OLD) obj;
		if (codigo_permissao == null) {
			if (other.codigo_permissao != null)
				return false;
		} else if (!codigo_permissao.equals(other.codigo_permissao))
			return false;
		if (codigo_usuario == null) {
			if (other.codigo_usuario != null)
				return false;
		} else if (!codigo_usuario.equals(other.codigo_usuario))
			return false;
		return true;
	}

}
