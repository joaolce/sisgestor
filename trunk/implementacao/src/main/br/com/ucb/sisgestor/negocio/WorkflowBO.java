/*
 * Projeto: SisGestor
 * Criação: 27/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.negocio;

import br.com.ucb.sisgestor.entidade.Workflow;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import java.util.List;

/**
 * Interface para um objeto de negócio de {@link Workflow}.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
public interface WorkflowBO extends BaseBO<Workflow> {

	/**
	 * Faz a cópia do {@link Workflow} informado.
	 * 
	 * @param idWorkflow identificador do workflow
	 * @throws NegocioException caso alguma regra seja violada
	 */
	void copiar(Integer idWorkflow) throws NegocioException;

	/**
	 * Recupera o workflow pelo id do uso
	 * 
	 * @param idUsoWorkflow Código identificador do uso workflow
	 * @return {@link Workflow}
	 */
	Workflow getByIdUsoWorkflow(Integer idUsoWorkflow);

	/**
	 * Retorna um {@link List} de {@link Workflow} a partir dos parâmetros informados. <br />
	 * 
	 * @param nome parte do nome do workflow
	 * @param descricao parte da descrição do workflow
	 * @param ativo indica se o workflow está ativo ou não
	 * @param excluidos indica se deve apresentar os workflows excluidos
	 * @param paginaAtual página atual da pesquisa
	 * @return Retorna os workflows
	 */
	List<Workflow> getByNomeDescricaoAtivo(String nome, String descricao, Boolean ativo, Boolean excluidos,
			Integer paginaAtual);

	/**
	 * Recupera a lista de workflows pendentes de serem inicializados.
	 * 
	 * @return {@link List} com {@link Workflow}
	 */
	List<Workflow> recuperarPendentesIniciar();
}
