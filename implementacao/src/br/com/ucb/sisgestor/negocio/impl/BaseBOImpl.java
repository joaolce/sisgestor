/*
 * Projeto: SisGestor
 * Cria��o: 16/10/2008 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.negocio.impl;

import br.com.ucb.sisgestor.entidade.ObjetoPersistente;
import br.com.ucb.sisgestor.mail.Email;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.negocio.exception.NegocioException;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementa��o da interface que representa um objeto de neg�cio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave prim�ria do objeto persistente utilizado
 * 
 * @author Jo�o L�cio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente, PK extends Serializable> implements
		BaseBO<T, PK> {

	private SessionFactory	sessionFactory;

	/**
	 * Cria uma nova inst�ncia do tipo {@link BaseBOImpl}.
	 */
	protected BaseBOImpl() {
		//apenas para proteger no pacote
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getTotalPesquisa(PesquisaPaginadaDTO parametros) {
		return 0;
	}

	/**
	 * Atribui a f�brica de sess�es do hibernate.
	 * 
	 * @param sessionFactory f�brica de sess�es do hibernate
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Faz o envio de um email.
	 * 
	 * @param remetente remetente do email
	 * @param assunto assunto do email
	 * @param corpo corpo do email
	 * @param destinatarios destinat�rios (TO) do email
	 * @return <code>true</code> se email enviado, <code>false</code> caso contr�rio
	 */
	@Transactional(readOnly = true)
	protected boolean enviaEmail(String remetente, String assunto, String corpo, String... destinatarios) {
		try {
			Email email = new Email();
			email.setAssunto(assunto);
			email.setRemetente(remetente);
			email.setCorpo(corpo);

			for (String destinatario : destinatarios) {
				email.addDestinatariosTO(destinatario.trim());
			}

			email.send();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Tira a persist�ncia do objeto.
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		this.getSession().evict(objeto);
	}

	/**
	 * Efetua a valida��o final do fluxo definido.
	 * 
	 * @param lista Lista de elementos armazenados no banco
	 * @param exceptionIsolado Exce��o a ser lan�ada caso encontre elemento isolado
	 * @param exceptionInicial Exce��o a ser lan�ada caso n�o encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exce��o a ser lan�ada caso n�o encontre elemento final
	 * @param mapAnteriores Mapa de fluxos anteriores
	 * @param mapPosteriores Mapa de fluxos posteriores
	 * @throws NegocioException Exce��o a ser lan�ada
	 */
	protected void finalizarValidacaoFluxos(List<?> lista, NegocioException exceptionIsolado,
			NegocioException exceptionInicial, NegocioException exceptionFinal,
			Map<Integer, Integer> mapAnteriores, Map<Integer, Integer> mapPosteriores) throws NegocioException {

		boolean temInicio = false;
		boolean temMaisDeUmInicio = false;
		boolean temFim = false;
		boolean temTarefasIsoladas = false;

		Integer id;
		Integer elementoAnterior;
		Integer elementoPosterior;
		ObjetoPersistente elemento;

		for (Object object : lista) {
			elemento = (ObjetoPersistente) object;

			id = elemento.getId();
			elementoAnterior = mapAnteriores.get(id);
			elementoPosterior = mapPosteriores.get(id);

			if ((elementoAnterior == null) && (elementoPosterior == null)) {
				temTarefasIsoladas = true;
				exceptionIsolado.putValorDevolvido("id" + id, id.toString());
			}
			if (elementoPosterior == null) {
				temFim = true;
			}
			if (elementoAnterior == null) {
				exceptionInicial.putValorDevolvido("id" + id, id);
				if (temInicio) {
					temMaisDeUmInicio = true;
				}
				temInicio = true;
			}
			if (elementoPosterior == null) {
				temFim = true;
			}
		}

		this.lancarExcecaoFluxo(exceptionIsolado, exceptionInicial, exceptionFinal, temInicio,
				temMaisDeUmInicio, temFim, temTarefasIsoladas);
	}

	/**
	 * Faz um flush na sess�o salvando os objetos que n�o estavam persistidos.
	 */
	protected void flush() {
		this.getSession().flush();
	}

	/**
	 * Retorna a sess�o de hibernate associada a thread atual.
	 * 
	 * @return sess�o atual do hibernate
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * D� um merge no objeto na sess�o do hibernate.
	 * 
	 * @param objeto objeto a dar o merge
	 */
	protected void merge(T objeto) {
		this.getSession().merge(objeto);
	}

	/**
	 * D� um refresh no objeto na sess�o do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}

	/**
	 * Lan�a exce��o do fluxo se alguma regra for violada baseada nos par�metros informados.
	 * 
	 * @param exceptionIsolado Exce��o a ser lan�ada caso encontre elemento isolado
	 * @param exceptionInicial Exce��o a ser lan�ada caso n�o encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exce��o a ser lan�ada caso n�o encontre elemento final
	 * @param temInicio Indicador para ocorr�ncia de elemento inicial
	 * @param temMaisDeUmInicio Indicador para ocorr�ncia de mais de um elemento inicial
	 * @param temFim Indicador para ocorr�ncia de nenhum elemento final
	 * @param temTarefasIsoladas Indicador para ocorr�ncia de elemento isolado
	 * @throws NegocioException Exce��o a ser lan�ada na ocorr�ncia de regra violada
	 */
	private void lancarExcecaoFluxo(NegocioException exceptionIsolado, NegocioException exceptionInicial,
			NegocioException exceptionFinal, boolean temInicio, boolean temMaisDeUmInicio, boolean temFim,
			boolean temTarefasIsoladas) throws NegocioException {
		//N�o permite tarefas isoladas
		if (temTarefasIsoladas) {
			throw exceptionIsolado;
		}
		//N�o permite a inexist�ncia de um in�cio
		if (!temInicio) {
			throw exceptionInicial;
		}

		//N�o permite que se tenha mais de um in�cio
		if (temMaisDeUmInicio) {
			throw exceptionInicial;
		}

		//N�o permite a inexist�ncia de pelo menos um final
		if (!temFim) {
			throw exceptionFinal;
		}
	}
}
