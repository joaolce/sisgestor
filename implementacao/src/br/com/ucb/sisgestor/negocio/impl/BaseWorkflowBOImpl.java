/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.BaseWorkflow;
import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Implementação da interface que representa um objeto de negócio (Business Object) para {@link BaseWorkflow}.
 * 
 * @param <T> {@link BaseWorkflow} utilizado no BO
 * @param <PK> chave primária do objeto utilizado
 * 
 * @author João Lúcio
 * @since 27/03/2009
 */
public abstract class BaseWorkflowBOImpl<T extends BaseWorkflow, PK extends Serializable> extends //NOPMD by João Lúcio - Herança
		BaseBOImpl<T, PK> {

	/**
	 * Cria uma nova instância do tipo {@link BaseWorkflowBOImpl}.
	 */
	protected BaseWorkflowBOImpl() {
		//apenas para proteger no pacote
	}

	/**
	 * Efetua a validação final do fluxo definido.
	 * 
	 * @param lista Lista de elementos armazenados no banco
	 * @param exceptionIsolado Exceção a ser lançada caso encontre elemento isolado
	 * @param exceptionInicial Exceção a ser lançada caso não encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exceção a ser lançada caso não encontre elemento final
	 * @param mapAnteriores Mapa de fluxos anteriores
	 * @param mapPosteriores Mapa de fluxos posteriores
	 * @throws NegocioException Exceção a ser lançada
	 */
	protected void finalizarValidacaoFluxos(List<?> lista, NegocioException exceptionIsolado,
			NegocioException exceptionInicial, NegocioException exceptionFinal,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores) throws NegocioException {

		boolean temInicio = false;
		boolean temMaisDeUmInicio = false;
		boolean temFim = false;
		boolean temTarefasIsoladas = false;

		Integer id;
		Integer elementoAnterior;
		Integer elementoPosterior;
		ObjetoPersistente elemento;

		for (Object object : lista) {
			elemento = (ObjetoPersistente) object;

			id = elemento.getId();
			elementoAnterior = mapAnteriores.get(id);
			elementoPosterior = mapPosteriores.get(id);

			if ((elementoAnterior == null) && (elementoPosterior == null)) {
				temTarefasIsoladas = true;
				exceptionIsolado.putValorDevolvido("id" + id, id.toString());
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
				temMaisDeUmInicio, temFim, temTarefasIsoladas);
	}

	/**
	 * Lança exceção do fluxo se alguma regra for violada baseada nos parâmetros informados.
	 * 
	 * @param exceptionIsolado Exceção a ser lançada caso encontre elemento isolado
	 * @param exceptionInicial Exceção a ser lançada caso não encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exceção a ser lançada caso não encontre elemento final
	 * @param temInicio Indicador para ocorrência de elemento inicial
	 * @param temMaisDeUmInicio Indicador para ocorrência de mais de um elemento inicial
	 * @param temFim Indicador para ocorrência de nenhum elemento final
	 * @param temTarefasIsoladas Indicador para ocorrência de elemento isolado
	 * @throws NegocioException Exceção a ser lançada na ocorrência de regra violada
	 */
	private void lancarExcecaoFluxo(NegocioException exceptionIsolado, NegocioException exceptionInicial,
			NegocioException exceptionFinal, boolean temInicio, boolean temMaisDeUmInicio, boolean temFim,
			boolean temTarefasIsoladas) throws NegocioException {
		//Não permite tarefas isoladas
		if (temTarefasIsoladas) {
			throw exceptionIsolado;
		}
		//Não permite a inexistência de um início
		if (!temInicio) {
			throw exceptionInicial;
		}

		//Não permite que se tenha mais de um início
		if (temMaisDeUmInicio) {
			throw exceptionInicial;
		}

		//Não permite a inexistência de pelo menos um final
		if (!temFim) {
			throw exceptionFinal;
		}
	}
}
