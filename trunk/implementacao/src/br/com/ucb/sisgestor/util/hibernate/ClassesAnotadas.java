/*
 * Projeto: SisGestor
 * Criação: 23/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import br.com.ucb.sisgestor.entidade.Anexo;
import br.com.ucb.sisgestor.entidade.Atividade;
import br.com.ucb.sisgestor.entidade.Campo;
import br.com.ucb.sisgestor.entidade.CampoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.ucb.sisgestor.entidade.HistoricoUsoWorkflowPK;
import br.com.ucb.sisgestor.entidade.HistoricoUsuario;
import br.com.ucb.sisgestor.entidade.HistoricoUsuarioPK;
import br.com.ucb.sisgestor.entidade.OpcaoCampo;
import br.com.ucb.sisgestor.entidade.Permissao;
import br.com.ucb.sisgestor.entidade.Processo;
import br.com.ucb.sisgestor.entidade.Tarefa;
import br.com.ucb.sisgestor.entidade.TransacaoAtividade;
import br.com.ucb.sisgestor.entidade.TransacaoProcesso;
import br.com.ucb.sisgestor.entidade.TransacaoTarefa;
import br.com.ucb.sisgestor.entidade.UsoWorkflow;
import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.entidade.Workflow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Armazena as classes anotadas para o hibernate.
 * 
 * @author João Lúcio
 * @since 23/10/2008
 */
public class ClassesAnotadas {

	private static List<Class<? extends Serializable>>	classes	=
																							new ArrayList<Class<? extends Serializable>>();

	/**
	 * Adiciona as classes anotadas em uma lista.
	 */
	static {
		classes.add(Anexo.class);
		classes.add(Atividade.class);
		classes.add(Campo.class);
		classes.add(CampoUsoWorkflow.class);
		classes.add(Departamento.class);
		classes.add(HistoricoUsoWorkflow.class);
		classes.add(HistoricoUsoWorkflowPK.class);
		classes.add(HistoricoUsuario.class);
		classes.add(HistoricoUsuarioPK.class);
		classes.add(OpcaoCampo.class);
		classes.add(Permissao.class);
		classes.add(Processo.class);
		classes.add(Tarefa.class);
		classes.add(TransacaoAtividade.class);
		classes.add(TransacaoProcesso.class);
		classes.add(TransacaoTarefa.class);
		classes.add(UsoWorkflow.class);
		classes.add(Usuario.class);
		classes.add(Workflow.class);
	}

	/**
	 * Retorna a lista com as classes anotadas
	 * 
	 * @return lista de classes anotadas
	 */
	public static List<Class<? extends Serializable>> getClassesAnotadas() {
		return classes;
	}
}
