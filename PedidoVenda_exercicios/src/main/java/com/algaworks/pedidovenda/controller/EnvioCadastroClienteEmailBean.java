package com.algaworks.pedidovenda.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioCadastroClienteEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@ClienteEdicao
	private Cliente cliente;
	
	
	public void enviarPedido() throws IOException {
		MailMessage message = mailer.novaMensagem();
		
		File file = new File("/home/lduarte/pedido.template");
		
		message.to(this.cliente.getEmail())
			.subject("Cliente " + this.cliente.getId())
			.bodyHtml(new VelocityTemplate(file))
			.put("cliente", this.cliente)
			.send();
		
		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}

}
