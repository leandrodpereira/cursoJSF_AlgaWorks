package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;

	private String nome;
	private Usuario usuarioSelecionado;
	
	private List<Usuario> filtrados;
	
	public void excluir() {
		usuarios.remover(usuarioSelecionado);
		filtrados.remove(usuarioSelecionado);
		
		FacesUtil.addInfoMessage("Usuário excuído com sucesso!");
	}
	
	public void pesquisar() {
		filtrados = usuarios.filtrados(nome);
	}

	public List<Usuario> getFiltrados() {
		return filtrados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}	
	
}
