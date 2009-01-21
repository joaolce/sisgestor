/*
 * Projeto: sisgestor
 * Criação: 21/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterUsuarioAction;

/**
 * Validações da action {@link ManterUsuarioAction}.
 * 
 * @author João Lúcio
 * @since 21/01/2009
 */
public class ManterUsuarioValidator extends BaseValidator {

	/**
	 * Faz as validações no método atualizar.
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
	 * Faz as validações no método atualizarSenha.
	 */
	public void atualizarSenha() {
		this.validaRequerido("label.senha.atual", "senhaAtual");
		this.validaRequerido("label.senha.nova", "novaSenha");
		this.validaRequerido("label.senha.confirmar", "confirmarSenha");
		this.validaTamanhoMinimo("label.senha.nova", "novaSenha", 6);
		this.validaTamanhoMinimo("label.senha.confirmar", "confirmarSenha", 6);
	}

	/**
	 * Faz as validações no método salvar.
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
