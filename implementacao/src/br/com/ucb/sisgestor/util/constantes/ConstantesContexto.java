/*
 * Projeto: SisGestor
 * Cria��o: 25/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.constantes;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;

/**
 * Constantes do contexto web.
 * 
 * @author Jo�o L�cio
 * @since 25/10/2008
 */
public final class ConstantesContexto {

	/** Data do �ltimo login */
	public static final String	DATA_LOGIN			= "dataLogin";
	/** Indica se foi erro do container */
	public static final String	ERRO_CONTAINER		= "errorContainer";
	/** Hora do �ltimo login */
	public static final String	HORA_LOGIN			= "horaLogin";
	/** Tamanho da p�gina (em registros) de pagina��o */
	public static final String	TAMANHO_PAGINA		= "tamanhoPaginaPesquisa";
	/** Total de registros da pesquisa de pagina��o */
	public static final String	TOTAL_PESQUISA		= "totalPesquisa";
	/** Usu�rio atual na sess�o */
	public static final String	USUARIO_SESSAO		= "usuarioSessao";
	/** Tarefa do {@link UsoWorkflow} j� iniciada. */
	public static final String	TAREFA_INICIADA	= "TarefaIniciada";
	/** Identificador do {@link UsoWorkflow} para utiliza��o do {@link Anexo}. */
	public static final String	ID_USO_WORKFLOW	= "idUsoWorkflowAnexo";
}
