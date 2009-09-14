/*
 * Projeto: sisgestor
 * Criação: 21/01/2009 por João Lúcio
 */
package br.com.sisgestor.apresentacao.validator;

import br.com.sisgestor.util.constantes.ConstantesDB;
import br.com.sisgestor.apresentacao.actions.ManterUsuarioAction;
import org.apache.commons.lang.StringUtils;

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
		this.salvar();
	}

	/**
	 * Faz as validações no método atualizarSenha.
	 */
	public void atualizarSenha() {
		if (this.validaRequerido("label.senha.atual", "senhaAtual")
				&& !this.getFormValue("senhaAtual").equals(this.getUser().getSenha())) {
			this.addError("erro.senha.atual");
			this.setFocusControl("senhaAtual");
		}
		if (this.validaRequerido("label.senha.nova", "novaSenha")) {
			this.validaTamanhoMinimo("label.senha.nova", "novaSenha", 6);
		}
		this.validaRequerido("label.senha.confirmar", "confirmarSenha");
		if (!this.getFormValue("novaSenha").equals(this.getFormValue("confirmarSenha"))) {
			this.addError("erro.senha.diferente", this.getMessageKey("label.senha.nova"), this
					.getMessageKey("label.senha.confirmar"));
			this.setFocusControl("confirmarSenha");
		}
	}

	/**
	 * Faz as validações no método salvar.
	 */
	public void salvar() {
		if (this.validaRequerido("label.login", "login")) {
			this.validaTamanhoMinimo("label.login", "login", 5);
			this.validaTamanhoMaximo("label.login", "login", ConstantesDB.LOGIN);
			String login = (String) this.getFormValue("login");
			if (StringUtils.contains(login, ' ')) {
				this.addError("erro.usuario.login.branco");
				this.setFocusControl("login");
			}
		}
		if (this.validaRequerido("label.nome", "nome")) {
			this.validaTamanhoMaximo("label.nome", "nome", ConstantesDB.NOME);
		}
		this.validaRequerido("label.departamento", "departamento");
		this.validaRequerido("label.chefe", "chefe");
		this.validaRequerido("label.permissoesSelecionadas", "permissoes");
		this.validaEmail("label.email", "email");
	}
}
