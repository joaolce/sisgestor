/*
 * Projeto: sisgestor
 * Cria��o: 27/03/2009 por Jo�o L�cio
 */
package br.com.sisgestor.negocio.impl;

import br.com.sisgestor.negocio.exception.NegocioException;
import br.com.sisgestor.entidade.BaseWorkflow;
import br.com.sisgestor.entidade.ObjetoPersistente;
import br.com.sisgestor.entidade.Workflow;
import java.util.List;
import java.util.Map;

/**
 * Implementa��o da interface que representa um objeto de neg�cio (Business Object) para {@link BaseWorkflow}.
 * 
 * @param <T> {@link BaseWorkflow} utilizado no BO
 * 
 * @author Jo�o L�cio
 * @since 27/03/2009
 */
public abstract class BaseWorkflowBOImpl<T extends BaseWorkflow> extends BaseBOImpl<T> { //NOPMD by Jo�o L�cio - Heran�a

	/**
	 * Cria uma nova inst�ncia do tipo {@link BaseWorkflowBOImpl}.
	 */
	protected BaseWorkflowBOImpl() {
		//apenas para proteger no pacote
	}

	/**
	 * Efetua a valida��o final do fluxo definido.
	 * 
	 * @param lista Lista de elementos armazenados no banco
	 * @param exceptionIsolado Exce��o a ser lan�ada caso encontre elemento isolado
	 * @param exceptionInicial Exce��o a ser lan�ada caso n�o encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exce��o a ser lan�ada caso n�o encontre elemento final
	 * @param mapAnteriores Mapa de fluxos anteriores
	 * @param mapPosteriores Mapa de fluxos posteriores
	 * @throws NegocioException Exce��o a ser lan�ada
	 */
	protected void validarFluxos(List<?> lista, NegocioException exceptionIsolado,
			NegocioException exceptionInicial, NegocioException exceptionFinal,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores) throws NegocioException {

		boolean temInicio = false;
		boolean temMaisDeUmInicio = false;
		boolean temFim = false;
		boolean temElementoIsolado = false;

		Integer id;
		Integer elementoAnterior;
		Integer elementoPosterior;
		ObjetoPersistente elemento;

		for (Object objeto : lista) {
			elemento = (ObjetoPersistente) objeto;

			id = elemento.getId();
			elementoAnterior = mapAnteriores.get(id);
			elementoPosterior = mapPosteriores.get(id);

			if ((elementoAnterior == null) && (elementoPosterior == null)) {
				temElementoIsolado = true;
				exceptionIsolado.putValorDevolvido("id" + id, id);
			}
			if (elementoPosterior == null) {
				temFim = true;
			}
			if (elementoAnterior == null) {
				exceptionInicial.putValorDevolvido("id" + id, id);
				if (temInicio) {
					temMaisDeUmInicio = true;
				}
				temInicio = true;
			}
			if (elementoPosterior == null) {
				temFim = true;
			}
		}

		this.lancarExcecaoFluxo(exceptionIsolado, exceptionInicial, exceptionFinal, temInicio,
				temMaisDeUmInicio, temFim, temElementoIsolado);
	}

	/**
	 * Verifica se o {@link Workflow} pode ser alterado, para n�o poder ocorrer altera��o em nenhum lugar.
	 * 
	 * @param workflow {@link Workflow} a verificar
	 * @throws NegocioException caso o {@link Workflow} n�o possa ser alterado
	 */
	protected void validarSePodeAlterarWorkflow(Workflow workflow) throws NegocioException {
		if (workflow.getAtivo() || (workflow.getDataHoraExclusao() != null)) {
			throw new NegocioException("erro.workflow.alterar");
		}
	}

	/**
	 * Lan�a exce��o do fluxo se alguma regra for violada baseada nos par�metros informados.
	 * 
	 * @param exceptionIsolado Exce��o a ser lan�ada caso encontre elemento isolado
	 * @param exceptionInicial Exce��o a ser lan�ada caso n�o encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exce��o a ser lan�ada caso n�o encontre elemento final
	 * @param temInicio Indicador para ocorr�ncia de elemento inicial
	 * @param temMaisDeUmInicio Indicador para ocorr�ncia de mais de um elemento inicial
	 * @param temFim Indicador para ocorr�ncia de nenhum elemento final
	 * @param temElementoIsolado Indicador para ocorr�ncia de elemento isolado
	 * @throws NegocioException Exce��o a ser lan�ada na ocorr�ncia de regra violada
	 */
	private void lancarExcecaoFluxo(NegocioException exceptionIsolado, NegocioException exceptionInicial,
			NegocioException exceptionFinal, boolean temInicio, boolean temMaisDeUmInicio, boolean temFim,
			boolean temElementoIsolado) throws NegocioException {
		//N�o permite tarefas isoladas
		if (temElementoIsolado) {
			throw exceptionIsolado;
		}
		//N�o permite a inexist�ncia de um in�cio
		if (!temInicio) {
			throw exceptionInicial;
		}

		//N�o permite que se tenha mais de um in�cio
		if (temMaisDeUmInicio) {
			throw exceptionInicial;
		}

		//N�o permite a inexist�ncia de pelo menos um final
		if (!temFim) {
			throw exceptionFinal;
		}
	}
}
