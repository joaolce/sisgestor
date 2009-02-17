/*
 * Projeto: sisgestor
 * Criação: 17/02/2009 por Thiago
 */
package br.com.ucb.sisgestor.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;

/**
 * Classe para representar um campo utilizado no workflow.
 * 
 * @author Thiago
 * @since 17/02/2009
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CAM_CAMPO")
@org.hibernate.annotations.Table(appliesTo = "CAM_CAMPO")
@AttributeOverride(name = "id", column = @Column(name = "CAM_ID", nullable = false))
public class Campo extends ObjetoPersistente {

}
