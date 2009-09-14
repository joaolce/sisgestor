/*
 * Projeto: SisGestor
 * Criação: 23/10/2008 por João Lúcio
 */
package br.com.sisgestor.util.hibernate; //NOPMD by João Lúcio - classes para o hibernate

import br.com.sisgestor.entidade.Anexo;
import br.com.sisgestor.entidade.Atividade;
import br.com.sisgestor.entidade.Campo;
import br.com.sisgestor.entidade.CampoUsoWorkflow;
import br.com.sisgestor.entidade.Departamento;
import br.com.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.sisgestor.entidade.HistoricoUsoWorkflowPK;
import br.com.sisgestor.entidade.OpcaoCampo;
import br.com.sisgestor.entidade.Permissao;
import br.com.sisgestor.entidade.Processo;
import br.com.sisgestor.entidade.Tarefa;
import br.com.sisgestor.entidade.TransacaoAtividade;
import br.com.sisgestor.entidade.TransacaoProcesso;
import br.com.sisgestor.entidade.TransacaoTarefa;
import br.com.sisgestor.entidade.UsoWorkflow;
import br.com.sisgestor.entidade.Usuario;
import br.com.sisgestor.entidade.Workflow;
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
