package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Grupos;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private Grupo grupoSelecionado;
	
	private List<Grupo> listaGrupos;
	private List<Grupo> usuarioGrupos;
	
	@Inject
	private Grupos grupos;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService; 
	
	public CadastroUsuarioBean() {
		limpar();
	}
	
	public void insereGrupo() {
		if (grupoSelecionado != null) 	
			usuarioGrupos.add(grupoSelecionado);
		else
			FacesUtil.addErrorMessage("Um grupo precisa ser selecionado.");
	}
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			listaGrupos = grupos.mostraTrodos();	
			usuarioGrupos = grupos.porUsuario(usuario);
		}
	}
		
	public void salvar() {
		usuario.setGrupos(usuarioGrupos);		
		
		cadastroUsuarioService.salvar(this.usuario);
		
		limpar();
		FacesUtil.addInfoMessage("Salvo com sucesso.");
	}
	
	public void limpar() {
		usuario = new Usuario();
		grupoSelecionado = new Grupo();
		listaGrupos = new ArrayList<>();
		usuarioGrupos = new ArrayList<>();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public List<Grupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}

	public void setUsuarioGrupos(List<Grupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}	
	
}
