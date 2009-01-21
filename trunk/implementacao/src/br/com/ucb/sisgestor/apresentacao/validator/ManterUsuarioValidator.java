/*
 * Projeto: sisgestor
 * Cria��o: 21/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterUsuarioAction;

/**
 * Valida��es da action {@link ManterUsuarioAction}.
 * 
 * @author Jo�o L�cio
 * @since 21/01/2009
 */
public class ManterUsuarioValidator extends BaseValidator {

	/**
	 * Faz as valida��es no m�todo atualizar.
	 */
	public void atualizar() {
		this.validaRequerido("label.login", "login");
		this.validaRequerido("label.nome", "nome");
		this.validaRequerido("label.departamento", "departamento");
		this.validaEmail("label.email", "email");
		this.validaTamanhoMaximo("label.login", "login", 15);
		this.validaTamanhoMaximo("label.nome", "nome", 150);
	}

	/**
	 * Faz as valida��es no m�todo atualizarSenha.
	 */
	public void atualizarSenha() {
		this.validaRequerido("label.senha.atual", "senhaAtual");
		this.validaRequerido("label.senha.nova", "novaSenha");
		this.validaRequerido("label.senha.confirmar", "confirmarSenha");
		this.validaTamanhoMinimo("label.senha.nova", "novaSenha", 6);
		if (!this.getFormValue("senhaAtual").equals(this.getUser().getSenha())) {
			this.addError("erro.senha.atual");
		}
		if (!this.getFormValue("novaSenha").equals(this.getFormValue("confirmarSenha"))) {
			this.addError("erro.senha.diferente", this.getMessageKey("label.senha.nova"), this
					.getMessageKey("label.senha.confirmar"));
		}
	}

	/**
	 * Faz as valida��es no m�todo salvar.
	 */
	public void salvar() {
		this.validaRequerido("label.login", "login");
		this.validaRequerido("label.nome", "nome");
		this.validaRequerido("label.departamento", "departamento");
		this.validaRequerido("label.permissoesSelecionadas", "permissoes");
		this.validaEmail("label.email", "email");
		this.validaTamanhoMaximo("label.login", "login", 15);
		this.validaTamanhoMaximo("label.nome", "nome", 150);
	}
}
