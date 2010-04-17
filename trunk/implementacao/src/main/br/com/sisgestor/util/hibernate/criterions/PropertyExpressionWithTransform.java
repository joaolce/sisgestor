/*
 * Projeto: sisgestor
 * Criação: 07/04/2009 por João Lúcio
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
 * Classe para adicionar critérios a uma consulta. <br />
 * Comportamento semelhante a classe {@link PropertyExpression} porém permite que seja adicionado
 * uma função de transformação aos operandos
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
 * @author João Lúcio
 * @since 07/04/2009
 */
public class PropertyExpressionWithTransform implements Criterion {

	private final String primeiraPropriedade;
	private final String segundaPropriedade;
	private final String op;
	private final String primeiraFuncao;
	private final String segundaFuncao;

	/**
	 * Cria uma expressão de transformação para o hibernate.
	 * 
	 * @param primeiraPropriedade nome da primeira propriedade
	 * @param segundaPropriedade nome da segunda propriedade
	 * @param primeiraFuncao nome da primeira função
	 * @param segundaFuncao nome da segunda função
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
				xcols[i] = this.primeiraFuncao + '(' + xcols[i] + ')'; //NOPMD by João Lúcio - mais legível
			}
		}
		String[] ycols = criteriaQuery.getColumnsUsingProjection(criteria, this.segundaPropriedade);
		if (this.segundaFuncao != null) {
			for (int i = 0; i < ycols.length; i++) {
				ycols[i] = this.segundaFuncao + '(' + ycols[i] + ')'; //NOPMD by João Lúcio - mais legível
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
