/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.constantes;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;

/**
 * Constantes do contexto web.
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public final class ConstantesContexto {

	/** Data do último login */
	public static final String	DATA_LOGIN			= "dataLogin";
	/** Indica se foi erro do container */
	public static final String	ERRO_CONTAINER		= "errorContainer";
	/** Hora do último login */
	public static final String	HORA_LOGIN			= "horaLogin";
	/** Tamanho da página (em registros) de paginação */
	public static final String	TAMANHO_PAGINA		= "tamanhoPaginaPesquisa";
	/** Total de registros da pesquisa de paginação */
	public static final String	TOTAL_PESQUISA		= "totalPesquisa";
	/** Usuário atual na sessão */
	public static final String	USUARIO_SESSAO		= "usuarioSessao";
	/** Tarefa do {@link UsoWorkflow} já iniciada. */
	public static final String	TAREFA_INICIADA	= "TarefaIniciada";
	/** Identificador do {@link UsoWorkflow} para utilização do {@link Anexo}. */
	public static final String	ID_USO_WORKFLOW	= "idUsoWorkflowAnexo";
}
