/*
 * Projeto: sisgestor
 * Cria��o: 01/01/2009 por Jo�o L�cio
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import br.com.ucb.sisgestor.entidade.Usuario;
import br.com.ucb.sisgestor.negocio.BaseBO;
import br.com.ucb.sisgestor.util.Utils;
import br.com.ucb.sisgestor.util.constantes.ConstantesContexto;
import br.com.ucb.sisgestor.util.dto.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.dto.PesquisaPaginadaDTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContextFactory;

/**
 * Base para os objetos DWR do projeto.
 * 
 * @author Jo�o L�cio
 * @since 01/01/2009
 */
public class BaseDWR {

	/**
	 * Cria uma nova inst�ncia do tipo {@link BaseDWR}.
	 */
	public BaseDWR() {
		Utils.injectionAutowired(this, WebContextFactory.get().getServletContext());
	}

	/**
	 * Recupera a mensagem no properties.
	 * 
	 * @param key chave da mensagem
	 * @param args argumentos da mensagem a subtituir
	 * @return {@link String} da mensagem
	 */
	protected String getMessage(String key, String... args) {
		return Utils.getMessageFromProperties(key, args);
	}

	/**
	 * Recupera o {@link HttpServletRequest} atual.
	 * 
	 * @return request atual
	 */
	protected HttpServletRequest getRequest() {
		return WebContextFactory.get().getHttpServletRequest();
	}

	/**
	 * Recupera a {@link HttpSession} atual.
	 * 
	 * @return session atual
	 */
	protected HttpSession getSession() {
		return this.getRequest().getSession();
	}

	/**
	 * Recupera um atributo da sess�o.
	 * 
	 * @see HttpSession#getAttribute(String)
	 * 
	 * @param name nome do atributo na sess�o
	 * @return valor do atributo
	 */
	protected Object getSessionAttribute(String name) {
		return this.getSession().getAttribute(name);
	}

	/**
	 * Recupera o {@link Usuario} atual do sistema.
	 * 
	 * @return usu�rio logado
	 */
	protected Usuario getUser() {
		return Utils.getUsuario();
	}

	/**
	 * Atribu� um valor ao atributo na sess�o.
	 * 
	 * @see HttpSession#setAttribute(String, Object)
	 * 
	 * @param name nome do atributo na sess�o
	 * @param value valor do atributo
	 */
	protected void setSessionAttribute(String name, Object value) {
		this.getSession().setAttribute(name, value);
	}

	/**
	 * Seta o total de registros retornados pela consulta.
	 * 
	 * @param dto par�metros da consulta
	 * @param resultado resultado parcial da consulta
	 * @param bo bo a realizar a consulta
	 */
	protected void setTotalPesquisa(PesquisaPaginadaDTO dto, ListaResultadoDTO<?> resultado, BaseBO<?, ?> bo) {
		List<?> lista = resultado.getColecaoParcial();
		if ((dto.getPaginaAtual() == null) && !lista.isEmpty()) {
			Integer total = bo.getTotalPesquisa(dto);
			this.setSessionAttribute(ConstantesContexto.TOTAL_PESQUISA, total);
			resultado.setTotalRegistros(total);
		} else {
			resultado.setTotalRegistros((Integer) this.getSessionAttribute(ConstantesContexto.TOTAL_PESQUISA));
		}
	}
}
