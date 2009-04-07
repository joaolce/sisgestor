/*
 * Projeto: sisgestor
 * Cria��o: 07/04/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.util.hibernate.criterions;

import java.sql.Types;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.TypedValue;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

/**
 * Classe para adicionar crit�rios a uma consulta. <br />
 * Comportamento semelhante a classe {@link SimpleExpression} por�m permite que seja adicionado uma fun��o de
 * transforma��o ao operando
 * <p>
 * Exemplos:
 * <ul>
 * <li>year(uwr.dataHoraInicio) > 2008</li>
 * </ul>
 * </p>
 * 
 * @see SimpleExpression
 * 
 * @author Jo�o L�cio
 * @since 07/04/2009
 */
public class SimpleExpressionWithTransform implements Criterion {

	private String		propriedade;
	private String		funcao	= "";
	private Object		valor;
	private boolean	ignoreCase;	//NOPMD by Jo�o L�cio - usando mesma nota��o do hibernate
	private String		op;

	/**
	 * Cria uma express�o de com transforma��o de dados para o hibernate, utilizando o operador '>'.
	 * 
	 * @param propriedade nome da propriedade
	 * @param funcao fun��o utilizada
	 * @param valor valor a comparar
	 * @param op operador utilizado
	 */
	protected SimpleExpressionWithTransform(String propriedade, String funcao, Object valor, String op) {
		this.propriedade = propriedade;
		if (StringUtils.isNotBlank(funcao)) {
			this.funcao = funcao;
		}
		this.valor = valor;
		this.op = op;
	}

	/**
	 * {@inheritDoc}
	 */
	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
			throws HibernateException {
		Type type = null;
		if ("".equals(this.funcao)) { //n�o tem fun��o
			type = criteriaQuery.getType(criteria, this.propriedade);
		} else {
			type = new IntegerType();
		}
		TypedValue typedValue = new TypedValue(type, this.valor, EntityMode.POJO);
		return new TypedValue[] {typedValue};
	}

	/**
	 * Atribui a express�o para ser 'ignore case'.
	 * 
	 * @return a pr�pria express�o com ignore case
	 */
	public SimpleExpressionWithTransform ignoreCase() {
		this.ignoreCase = true;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		String columns[] = criteriaQuery.getColumnsUsingProjection(criteria, this.propriedade);
		Type type = criteriaQuery.getTypeUsingProjection(criteria, this.propriedade);
		StringBuffer sql = new StringBuffer();
		if (columns.length > 1) {
			sql.append('(');
		}
		SessionFactoryImplementor factory = criteriaQuery.getFactory();
		int sqlTypes[] = type.sqlTypes(factory);
		for (int i = 0; i < columns.length; i++) {
			sql.append(this.funcao).append('(');
			boolean lower = this.ignoreCase && ((sqlTypes[i] == Types.VARCHAR) || (sqlTypes[i] == Types.CHAR));
			if (lower) {
				sql.append(factory.getDialect().getLowercaseFunction()).append('(');
			}
			sql.append(columns[i]);
			if (lower) {
				sql.append(')');
			}
			sql.append(')'); //Relativo a fun��o utilizada

			sql.append(this.op).append('?');
			if (i < columns.length - 1) {
				sql.append(" and ");
			}
		}
		if (columns.length > 1) {
			sql.append(')');
		}
		return sql.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.funcao + '(' + this.propriedade + ')' + this.op + this.valor;
	}
}
