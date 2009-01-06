/*
 * Projeto: sisgestor
 * Criação: 03/01/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.apresentacao.forms;

import br.com.ucb.sisgestor.apresentacao.actions.ManterDepartamentoAction;
import br.com.ucb.sisgestor.entidade.Departamento;
import java.util.List;

/**
 * Form para a action {@link ManterDepartamentoAction}.
 * 
 * @author João Lúcio
 * @since 03/01/2009
 */
public class ManterDepartamentoActionForm extends BaseForm {

	private List<Departamento>	departamentos;

	/**
	 * Recupera o valor de departamentos
	 * 
	 * @return departamentos
	 */
	public List<Departamento> getDepartamentos() {
		return this.departamentos;
	}

	/**
	 * Atribui departamentos
	 * 
	 * @param departamentos o valor a ajustar em departamentos
	 */
	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
}
