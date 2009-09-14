/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.sisgestor.entidade;

import br.com.sisgestor.util.constantes.ConstantesDB;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

/**
 * Classe que representa uma Tarefa.
 * 
 * @author Thiago
 * @since 04/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TAR_TAREFA")
@org.hibernate.annotations.Table(appliesTo = "TAR_TAREFA")
@AttributeOverrides( {
		@AttributeOverride(name = "id", column = @Column(name = "TAR_ID", nullable = false)),
		@AttributeOverride(name = "nome", column = @Column(name = "TAR_NOME", nullable = false, length = ConstantesDB.NOME)),
		@AttributeOverride(name = "descricao", column = @Column(name = "TAR_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO)),
		@AttributeOverride(name = "top", column = @Column(name = "TAR_TOP", nullable = true)),
		@AttributeOverride(name = "left", column = @Column(name = "TAR_LEFT", nullable = true))})
public class Tarefa extends BaseWorkflowDesenhavel {

	private Atividade					atividade;
	private Usuario					usuario;
	private List<TransacaoTarefa>	transacoesAnteriores;
	private List<TransacaoTarefa>	transacoesPosteriores;

	/**
	 * Recupera a atividade da tarefa.
	 * 
	 * @return atividade da tarefa
	 */
	@ManyToOne
	@JoinColumn(name = "ATI_ID", nullable = false)
	@ForeignKey(name = "IR_ATI_TAR")
	public Atividade getAtividade() {
		return this.atividade;
	}

	/**
	 * Recupera as transações anteriores diretas da tarefa.
	 * 
	 * @return transações anteriores diretas da tarefa
	 */
	@OneToMany(targetEntity = TransacaoTarefa.class, mappedBy = "posterior", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<TransacaoTarefa> getTransacoesAnteriores() {
		return this.transacoesAnteriores;
	}

	/**
	 * Recupera as transações posteriores diretas da tarefa.
	 * 
	 * @return transações posteriores diretas da tarefa
	 */
	@OneToMany(targetEntity = TransacaoTarefa.class, mappedBy = "anterior", fetch = FetchType.LAZY)
	@Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<TransacaoTarefa> getTransacoesPosteriores() {
		return this.transacoesPosteriores;
	}

	/**
	 * Recupera o usuário da tarefa.
	 * 
	 * @return usuário da tarefa
	 */
	@ManyToOne
	@JoinColumn(name = "UUR_ID", nullable = true)
	@ForeignKey(name = "IR_UUR_TAR")
	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * Atribui a atividade da tarefa.
	 * 
	 * @param atividade atividade da tarefa
	 */
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	/**
	 * Atribui as transações anteriores diretas da tarefa.
	 * 
	 * @param transacoesAnteriores transações anteriores diretas da tarefa
	 */
	public void setTransacoesAnteriores(List<TransacaoTarefa> transacoesAnteriores) {
		this.transacoesAnteriores = transacoesAnteriores;
	}

	/**
	 * Atribui as transações posteriores diretas da tarefa.
	 * 
	 * @param transacoesPosteriores transações posteriores diretas da tarefa
	 */
	public void setTransacoesPosteriores(List<TransacaoTarefa> transacoesPosteriores) {
		this.transacoesPosteriores = transacoesPosteriores;
	}

	/**
	 * Atribui o usuário da tarefa.
	 * 
	 * @param usuario usuário da tarefa
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
