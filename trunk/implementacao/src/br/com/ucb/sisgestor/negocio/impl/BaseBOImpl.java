/*
 * Projeto: SisGestor
 * Criação: 16/10/2008 por João Lúcio
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
 * Implementação da interface que representa um objeto de negócio (Business Object).
 * 
 * @param <T> objeto persistente utilizado no DAO
 * @param <PK> chave primária do objeto persistente utilizado
 * 
 * @author João Lúcio
 * @since 16/10/2008
 */
public abstract class BaseBOImpl<T extends ObjetoPersistente, PK extends Serializable> implements
		BaseBO<T, PK> {

	private SessionFactory	sessionFactory;

	/**
	 * Cria uma nova instância do tipo {@link BaseBOImpl}.
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
	 * Atribui a fábrica de sessões do hibernate.
	 * 
	 * @param sessionFactory fábrica de sessões do hibernate
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
	 * @param destinatarios destinatários (TO) do email
	 * @return <code>true</code> se email enviado, <code>false</code> caso contrário
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
	 * Tira a persistência do objeto.
	 * 
	 * @param objeto objeto a ser transiente
	 */
	protected void evict(ObjetoPersistente objeto) {
		this.getSession().evict(objeto);
	}

	/**
	 * Efetua a validação final do fluxo definido.
	 * 
	 * @param lista Lista de elementos armazenados no banco
	 * @param exceptionIsolado Exceção a ser lançada caso encontre elemento isolado
	 * @param exceptionInicial Exceção a ser lançada caso não encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exceção a ser lançada caso não encontre elemento final
	 * @param mapAnteriores Mapa de fluxos anteriores
	 * @param mapPosteriores Mapa de fluxos posteriores
	 * @throws NegocioException Exceção a ser lançada
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
	 * Faz um flush na sessão salvando os objetos que não estavam persistidos.
	 */
	protected void flush() {
		this.getSession().flush();
	}

	/**
	 * Retorna a sessão de hibernate associada a thread atual.
	 * 
	 * @return sessão atual do hibernate
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * Dá um merge no objeto na sessão do hibernate.
	 * 
	 * @param objeto objeto a dar o merge
	 */
	protected void merge(T objeto) {
		this.getSession().merge(objeto);
	}

	/**
	 * Dá um refresh no objeto na sessão do hibernate.
	 * 
	 * @param objeto objeto a dar o refresh
	 */
	protected void refresh(T objeto) {
		this.getSession().refresh(objeto);
	}

	/**
	 * Lança exceção do fluxo se alguma regra for violada baseada nos parâmetros informados.
	 * 
	 * @param exceptionIsolado Exceção a ser lançada caso encontre elemento isolado
	 * @param exceptionInicial Exceção a ser lançada caso não encontre elemento inicial ou mais de um inicial
	 * @param exceptionFinal Exceção a ser lançada caso não encontre elemento final
	 * @param temInicio Indicador para ocorrência de elemento inicial
	 * @param temMaisDeUmInicio Indicador para ocorrência de mais de um elemento inicial
	 * @param temFim Indicador para ocorrência de nenhum elemento final
	 * @param temTarefasIsoladas Indicador para ocorrência de elemento isolado
	 * @throws NegocioException Exceção a ser lançada na ocorrência de regra violada
	 */
	private void lancarExcecaoFluxo(NegocioException exceptionIsolado, NegocioException exceptionInicial,
			NegocioException exceptionFinal, boolean temInicio, boolean temMaisDeUmInicio, boolean temFim,
			boolean temTarefasIsoladas) throws NegocioException {
		//Não permite tarefas isoladas
		if (temTarefasIsoladas) {
			throw exceptionIsolado;
		}
		//Não permite a inexistência de um início
		if (!temInicio) {
			throw exceptionInicial;
		}

		//Não permite que se tenha mais de um início
		if (temMaisDeUmInicio) {
			throw exceptionInicial;
		}

		//Não permite a inexistência de pelo menos um final
		if (!temFim) {
			throw exceptionFinal;
		}
	}
}
