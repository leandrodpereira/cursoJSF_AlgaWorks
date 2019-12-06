package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ClienteEdicao
	@Produces
	private Cliente cliente;

	private Endereco endereco;
	private Endereco enderecoSelecionado;

	@Inject
	private CadastroClienteService cadastroClienteService;

	public void salvar() {
		this.cliente = cadastroClienteService.salvar(this.cliente);
		limpar();

		FacesUtil.addInfoMessage("Salvo com sucesso!");
	}

	public void incluirEndereco() {

		int indice = this.cliente.getEnderecos().indexOf(this.endereco);

		if (indice >= 0) {
			this.cliente.getEnderecos().set(indice, this.endereco);
		}

		else if (this.endereco != null) {
			this.endereco.setCliente(this.cliente);
			this.cliente.getEnderecos().add(this.endereco);
			this.endereco = new Endereco();
		} else {
			FacesUtil.addErrorMessage("O endereço precisa ser preenchido.");
		}

	}

	public void removerEndereco() {
		this.getCliente().getEnderecos().remove(this.enderecoSelecionado);
	}

	public CadastroClienteBean() {
		limpar();
	}

	private void limpar() {
		cliente = new Cliente();
		endereco = new Endereco();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoPessoa[] tipos() {
		return TipoPessoa.values();
	}

	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}

}
