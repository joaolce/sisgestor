/*
 * Projeto: sisgestor
 * Cria��o: 21/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.validator;

import br.com.ucb.sisgestor.apresentacao.actions.ManterUsuarioAction;
import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import org.apache.commons.lang.StringUtils;

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
		this.salvar();
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
			this.setFocusControl("senhaAtual");
		}
		if (!this.getFormValue("novaSenha").equals(this.getFormValue("confirmarSenha"))) {
			this.addError("erro.senha.diferente", this.getMessageKey("label.senha.nova"), this
					.getMessageKey("label.senha.confirmar"));
			this.setFocusControl("confirmarSenha");
		}
	}

	/**
	 * Faz as valida��es no m�todo salvar.
	 */
	public void salvar() {
		this.validaRequerido("label.login", "login");
		this.validaRequerido("label.nome", "nome");
		this.validaRequerido("label.departamento", "departamento");
		this.validaRequerido("label.chefe", "chefe");
		this.validaRequerido("label.permissoesSelecionadas", "permissoes");
		this.validaEmail("label.email", "email");
		this.validaTamanhoMinimo("label.login", "login", 5);
		this.validaTamanhoMaximo("label.login", "login", ConstantesDB.LOGIN);
		this.validaTamanhoMaximo("label.nome", "nome", ConstantesDB.NOME);
		String login = (String) this.getFormValue("login");
		if (StringUtils.contains(login, ' ')) {
			this.addError("erro.usuario.login.branco");
			this.setFocusControl("login");
		}
	}
}
