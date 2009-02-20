/*
 * Projeto: sisgestor
 * Criação: 04/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
		@AttributeOverride(name = "descricao", column = @Column(name = "TAR_DESCRICAO", nullable = false, length = ConstantesDB.DESCRICAO))})
public class Tarefa extends BaseWorkflow {

	private Atividade	atividade;
	private Usuario	usuario;

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
	 * Recupera o usuário da tarefa.
	 * 
	 * @return usuário da tarefa
	 */
	@ManyToOne
	@JoinColumn(name = "UUR_ID", nullable = true)
	@ForeignKey(name = "IR_URR_TAR")
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
	 * Atribui o usuário da tarefa.
	 * 
	 * @param usuario usuário da tarefa
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
