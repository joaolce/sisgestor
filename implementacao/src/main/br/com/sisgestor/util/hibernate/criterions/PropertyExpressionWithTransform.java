/*
 * Projeto: sisgestor
 * Cria��o: 07/04/2009 por Jo�o L�cio
 */
package br.com.sisgestor.util.hibernate.criterions;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.PropertyExpression;
import org.hibernate.engine.TypedValue;
import org.hibernate.util.StringHelper;

/**
 * Classe para adicionar crit�rios a uma consulta. <br />
 * Comportamento semelhante a classe {@link PropertyExpression} por�m permite que seja adicionado
 * uma fun��o de transforma��o aos operandos
 * <p>
 * Exemplos:
 * <ul>
 * <li>date(car.dataCadastro) > car.dataHoraEdicao</li>
 * <li>date(car.dataCadastro) > date(car.dataHoraEdicao)</li>
 * </ul>
 * </p>
 * 
 * @see PropertyExpression
 * 
 * @author Jo�o L�cio
 * @since 07/04/2009
 */
public class PropertyExpressionWithTransform implements Criterion {

	private final String primeiraPropriedade;
	private final String segundaPropriedade;
	private final String op;
	private final String primeiraFuncao;
	private final String segundaFuncao;

	/**
	 * Cria uma express�o de transforma��o para o hibernate.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira fun��o
	 * @param segundaFuncao nome da segunda fun��o
	 * @param op operador utilizado
	 */
	protected PropertyExpressionWithTransform(String primeiraPropriedade, String segundaPropriedade, String op,
			String primeiraFuncao, String segundaFuncao) {
		this.primeiraPropriedade = primeiraPropriedade;
		this.segundaPropriedade = segundaPropriedade;
		this.primeiraFuncao = primeiraFuncao;
		this.segundaFuncao = segundaFuncao;
		this.op = op;
	}

	/** {@inheritDoc} */
	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		return new TypedValue[0];
	}

	/** {@inheritDoc} */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		String[] xcols = criteriaQuery.getColumnsUsingProjection(criteria, this.primeiraPropriedade);
		if (this.primeiraFuncao != null) {
			for (int i = 0; i < xcols.length; i++) {
				xcols[i] = this.primeiraFuncao + '(' + xcols[i] + ')'; //NOPMD by Jo�o L�cio - mais leg�vel
			}
		}
		String[] ycols = criteriaQuery.getColumnsUsingProjection(criteria, this.segundaPropriedade);
		if (this.segundaFuncao != null) {
			for (int i = 0; i < ycols.length; i++) {
				ycols[i] = this.segundaFuncao + '(' + ycols[i] + ')'; //NOPMD by Jo�o L�cio - mais leg�vel
			}
		}
		StringBuffer result = new StringBuffer(StringHelper.join(" and ", StringHelper.add(xcols, this.op, ycols)));
		if (xcols.length > 1) {
			result.insert(0, '(');
			result.append(')');
		}
		return result.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		if ((this.primeiraFuncao == null) && (this.segundaFuncao == null)) {
			return this.primeiraPropriedade + this.op + this.segundaPropriedade;
		}
		if (this.segundaFuncao == null) {
			return this.primeiraFuncao + '(' + this.primeiraPropriedade + ')' + this.op + this.segundaPropriedade;
		}
		if (this.primeiraFuncao == null) {
			return this.primeiraPropriedade + this.op + this.segundaFuncao + '(' + this.segundaPropriedade + ')';
		}
		return this.primeiraFuncao + '(' + this.primeiraPropriedade + ')' + this.op + this.segundaFuncao + '('
				+ this.segundaPropriedade + ')';
	}
}
