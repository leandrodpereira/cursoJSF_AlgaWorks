package com.algaworks.pedidovenda.repository.filter;

import java.io.Serializable;

public class ClienteFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String documentoReceita;
	private String nome;
	
	
	public String getDocumentoReceita() {
		return documentoReceita;
	}
	public void setDocumentoReceita(String documentoReceita) {
		this.documentoReceita = documentoReceita;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
