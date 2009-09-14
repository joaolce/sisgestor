/*
 * Projeto: sisgestor
 * Criação: 27/03/2009 por Gustavo
 */
package br.com.sisgestor.apresentacao.dwr;

import br.com.sisgestor.util.GenericsUtil;
import br.com.sisgestor.util.dto.ListaResultadoDTO;
import br.com.sisgestor.util.dto.PesquisaUsarWorkflowDTO;
import br.com.sisgestor.util.constantes.ConstantesContexto;
import br.com.sisgestor.negocio.TarefaBO;
import br.com.sisgestor.negocio.UsoWorkflowBO;
import br.com.sisgestor.entidade.Campo;
import br.com.sisgestor.entidade.HistoricoUsoWorkflow;
import br.com.sisgestor.entidade.Tarefa;
import br.com.sisgestor.entidade.TipoAcaoEnum;
import br.com.sisgestor.entidade.UsoWorkflow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.impl.DefaultScriptSession;
import org.directwebremoting.proxy.dwr.Util;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Objeto DWR de usar workflow do projeto.
 * 
 * @author Gustavo
 * @since 27/03/2009
 */
public class UsarWorkflowDWR extends BaseDWR {

	private UsoWorkflowBO	usoWorkflowBO;
	private TarefaBO			tarefaBO;

	/**
	 * Recupera a lista de {@link TipoAcaoEnum}.
	 * 
	 * @return {@link List} com array de Object[], onde: [0] - id do tipo, [1] - name do tipo, [2] - descrição
	 *         do tipo
	 */
	public List<Object[]> getAcoesHistorico() {
		//método gambiarra feito, pois o DWR não converte de forma que conseguimos pegar os dados de uma enum
		List<Object[]> tipos = new ArrayList<Object[]>();
		for (TipoAcaoEnum tipo : TipoAcaoEnum.values()) {
			tipos.add(new Object[] {tipo.getId(), tipo.name(), tipo.getDescricao()});
		}
		return tipos;
	}

	/**
	 * Recupera um uso de workflow a partir do identificador.
	 * 
	 * @param id identificador do uso
	 * @return {@link UsoWorkflow}
	 */
	public UsoWorkflow getById(Integer id) {
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(id);
		Hibernate.initialize(usoWorkflow.getCamposUsados());
		Hibernate.initialize(usoWorkflow.getHistorico());
		return usoWorkflow;
	}

	/**
	 * Recupera uma lista de campos do workflow pelo código identificador do uso workflow
	 * 
	 * @param idUsoWorkflow Código identificador do uso workflow
	 * @return lista de campos
	 */
	public List<Campo> getCamposByIdUsoWorkflow(Integer idUsoWorkflow) {
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter((idUsoWorkflow));
		List<Campo> campos = usoWorkflow.getWorkflow().getCampos();
		for (Campo campo : campos) {
			Hibernate.initialize(campo.getOpcoes());
		}
		return campos;
	}

	/**
	 * Recupera o histórico do {@link UsoWorkflow}. <br>
	 * obs: usado apenas para atualizar a tabela de histórico, sem precisar recuperar todo o uso
	 * 
	 * @param id identificador do uso
	 * @return {@link List} do {@link HistoricoUsoWorkflow}
	 */
	public List<HistoricoUsoWorkflow> getHistorico(Integer id) {
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(id);
		return usoWorkflow.getHistorico();
	}

	/**
	 * Notifica o usuário da tarefa atual via AJAX reverso.
	 * 
	 * @param idUso identificador do uso com a tarefa modificada
	 */
	public void notificarUsuarioAJAX(Integer idUso) {
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(idUso);
		if (!usoWorkflow.getUsoFinalizado()) {
			WebContext webContext = WebContextFactory.get();
			Collection<DefaultScriptSession> todasSessions =
					GenericsUtil.checkedCollection(webContext.getAllScriptSessions(), DefaultScriptSession.class);
			Hibernate.initialize(usoWorkflow.getHistorico());
			new Util(todasSessions).addFunctionCall("Usuario.notificarTransferenciaRegistro", usoWorkflow);
		}
	}

	/**
	 * Pesquisa os {@link UsoWorkflow} com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link UsoWorkflow}
	 */
	public ListaResultadoDTO<UsoWorkflow> pesquisar(PesquisaUsarWorkflowDTO parametros) {
		Integer paginaAtual = parametros.getPaginaAtual();

		ListaResultadoDTO<UsoWorkflow> resultado = new ListaResultadoDTO<UsoWorkflow>();
		Integer totalRegistros = this.getTotalRegistros(parametros, this.usoWorkflowBO);
		if (totalRegistros > 0) {
			List<UsoWorkflow> lista = this.usoWorkflowBO.recuperarPendentesUsuarioAtual(paginaAtual);
			resultado.setColecaoParcial(lista);
		}
		resultado.setTotalRegistros(totalRegistros);
		return resultado;
	}

	/**
	 * Pesquisa os {@link UsoWorkflow} finalizados com os parâmetros preenchidos.
	 * 
	 * @param parametros parâmetros da pesquisa
	 * @return {@link List} de {@link UsoWorkflow}
	 */
	public ListaResultadoDTO<UsoWorkflow> pesquisarFinalizados(PesquisaUsarWorkflowDTO parametros) {
		String numeroRegistro = parametros.getNumeroRegistro();
		Integer idWorkflow = parametros.getWorkflow();
		Integer paginaAtual = parametros.getPaginaAtual();

		ListaResultadoDTO<UsoWorkflow> resultado = new ListaResultadoDTO<UsoWorkflow>();
		Integer totalRegistros = this.getTotalFinalizados(parametros);
		if (totalRegistros > 0) {
			List<UsoWorkflow> lista =
					this.usoWorkflowBO.recuperarFinalizados(numeroRegistro, idWorkflow, paginaAtual);
			for (UsoWorkflow uso : lista) {
				Hibernate.initialize(uso.getHistorico());
			}
			resultado.setColecaoParcial(lista);
		}
		resultado.setTotalRegistros(totalRegistros);
		return resultado;
	}

	/**
	 * Recupera se o {@link UsoWorkflow} em questão pode mudar de tarefa.
	 * 
	 * @param idUso identificador do uso
	 * @return <code>true</code> caso possa, <code>false</code> caso contrário
	 */
	public Boolean podeMudarDeTarefa(Integer idUso) {
		return this.usoWorkflowBO.podeMudarDeTarefa(idUso);
	}

	/**
	 * Recupera as próximas tarefas da tarefa atual do {@link UsoWorkflow}.
	 * 
	 * @param isUsoWorkflow identificador do uso
	 * @return {@link List} com as {@link Tarefa}
	 */
	public List<Tarefa> recuperarProximasTarefas(Integer isUsoWorkflow) {
		UsoWorkflow usoWorkflow = this.usoWorkflowBO.obter(isUsoWorkflow);
		return this.tarefaBO.recuperarProximasTarefas(usoWorkflow);
	}

	/**
	 * Atribui o BO de {@link Tarefa}.
	 * 
	 * @param tarefaBO BO de {@link Tarefa}
	 */
	@Autowired
	public void setTarefaBO(TarefaBO tarefaBO) {
		this.tarefaBO = tarefaBO;
	}

	/**
	 * Atribui o BO de {@link UsoWorkflowBO}.
	 * 
	 * @param usoWorkflowBO BO de {@link UsoWorkflowBO}
	 */
	@Autowired
	public void setUsoWorkflowBO(UsoWorkflowBO usoWorkflowBO) {
		this.usoWorkflowBO = usoWorkflowBO;
	}

	/**
	 * Recupera o número total de registros finalizados.
	 * 
	 * @param dto dto com os parâmetros
	 * @return número total de registros finalizados
	 */
	private Integer getTotalFinalizados(PesquisaUsarWorkflowDTO dto) {
		Integer totalRegistros;
		if (dto.getPaginaAtual() == null) {
			totalRegistros =
					this.usoWorkflowBO.recuperarTotalFinalizados(dto.getNumeroRegistro(), dto.getWorkflow());
			this.setSessionAttribute(ConstantesContexto.TOTAL_PESQUISA, totalRegistros);
		} else {
			totalRegistros = (Integer) this.getSessionAttribute(ConstantesContexto.TOTAL_PESQUISA);
		}
		return totalRegistros;
	}
}
