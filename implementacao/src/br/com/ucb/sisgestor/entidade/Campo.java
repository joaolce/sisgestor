/*
 * Projeto: sisgestor
 * Criação: 18/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import br.com.ucb.sisgestor.util.constantes.ConstantesDB;
import br.com.ucb.sisgestor.util.hibernate.HibernateUserTypeConstants;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * Classe para representar um campo utilizado no workflow.
 * 
 * @author Thiago
 * @since 18/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CAM_CAMPO")
@org.hibernate.annotations.Table(appliesTo = "CAM_CAMPO")
@AttributeOverride(name = "id", column = @Column(name = "CAM_ID", nullable = false))
public class Campo extends ObjetoPersistente {

	private String					nome;
	private String					descricao;
	private TipoCampoEnum		tipo;
	private Boolean				obrigatorio;
	private Workflow				workflow;
	private List<OpcaoCampo>	opcoes;

	/**
	 * Recupera a descrição do campo.
	 * 
	 * @return descrição do campo
	 */
	@Column(name = "CAM_DESCRICAO", nullable = true, length = ConstantesDB.DESCRICAO)
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Recupera o nome do campo.
	 * 
	 * @return nome do campo
	 */
	@Column(name = "CAM_NOME", nullable = false, length = ConstantesDB.NOME)
	public String getNome() {
		return this.nome;
	}

	/**
	 * Recupera se o campo é obrigatório.
	 * 
	 * @return se o campo é obrigatório
	 */
	@Column(name = "CAM_OBRIGATORIO", nullable = false, columnDefinition = ConstantesDB.DEFINICAO_BOOLEAN)
	public Boolean getObrigatorio() {
		return this.obrigatorio;
	}

	/**
	 * Recupera as opções do campo.
	 * 
	 * @return opções do campo
	 */
	@OneToMany(targetEntity = OpcaoCampo.class, mappedBy = "campo")
	@Cascade(CascadeType.DELETE_ORPHAN)
	public List<OpcaoCampo> getOpcoes() {
		return this.opcoes;
	}

	/**
	 * Recupera o tipo do campo.
	 * 
	 * @return tipo do campo
	 */
	@Column(name = "CAM_TIPO", nullable = false)
	@Type(type = HibernateUserTypeConstants.CODIGO_INTEGER, parameters = @Parameter(name = "className", value = HibernateUserTypeConstants.TIPO_CAMPO_ENUM))
	public TipoCampoEnum getTipo() {
		return this.tipo;
	}

	/**
	 * Recupera o workflow do campo.
	 * 
	 * @return workflow do campo
	 */
	@ManyToOne
	@JoinColumn(name = "WOR_ID", nullable = false)
	@ForeignKey(name = "IR_WOR_CAM")
	public Workflow getWorkflow() {
		return this.workflow;
	}

	/**
	 * Atribui a descrição do campo.
	 * 
	 * @param descricao descrição do campo
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Atribui o nome do campo.
	 * 
	 * @param nome nome do campo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Atribui se o campo é obrigatório.
	 * 
	 * @param obrigatorio se o campo é obrigatório
	 */
	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	/**
	 * Atribui as opções do campo.
	 * 
	 * @param opcoes opções do campo
	 */
	public void setOpcoes(List<OpcaoCampo> opcoes) {
		this.opcoes = opcoes;
	}

	/**
	 * Atribui o tipo do campo.
	 * 
	 * @param tipo tipo do campo
	 */
	public void setTipo(TipoCampoEnum tipo) {
		this.tipo = tipo;
	}

	/**
	 * Atribui o workflow do campo.
	 * 
	 * @param workflow workflow do campo
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
