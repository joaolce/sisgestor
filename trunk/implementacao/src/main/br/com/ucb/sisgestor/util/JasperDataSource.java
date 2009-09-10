/*
 * Projeto: SisGestor
 * Criação: 24/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Classe para a manipulação dos dados para montar relatórios jasper.
 * 
 * @author João Lúcio
 * @since 24/10/2008
 */
public class JasperDataSource implements JRDataSource {

	private List<? extends Object> dado;
	private int index;

	/**
	 * Cria uma nova instância do tipo JasperDataSource
	 * 
	 * @param lista lista de dados para o relatório
	 */
	public JasperDataSource(List<? extends Object> lista) {
		this.dado = lista;
		this.index = -1;
	}

	/**
	 * Recupera o valor do field solicitado pelo jasper
	 * 
	 * @param jrField field requerido
	 * @return valor solicitado
	 * @throws JRException caso ocorra exceção ao pegar valor do campo
	 */
	public Object getFieldValue(JRField jrField) throws JRException {
		Object valor = null;

		try {
			Object obj = this.dado.get(this.index);

			String name = jrField.getName();
			String nomeMetodo = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);

			Class<? extends Object> classe = obj.getClass();
			Method metodo = classe.getMethod(nomeMetodo);

			valor = metodo.invoke(obj);

			if (valor instanceof Collection<?>) {
				//utilizado caso o dado seja para um subrelatório
				valor = new JRBeanCollectionDataSource((Collection<?>) valor);
			}
		} catch (Exception e) {
			throw new JRException(e);
		}
		return valor;
	}

	/**
	 * Verifica se há um próximo elemento
	 * 
	 * @return true case haja, false caso contrário
	 * @throws JRException caso seja lançada exceção
	 */
	public boolean next() throws JRException {
		return (++this.index < this.dado.size());
	}
}
