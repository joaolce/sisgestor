/*
 * Projeto: sisgestor
 * Criação: 26/05/2009 por João Lúcio
 */
package br.com.sisgestor.apresentacao.dwr.utils;

import br.com.sisgestor.util.constantes.ConstantesContexto;
import org.directwebremoting.impl.DefaultServerLoadMonitor;

/**
 * Monitoramento para o ajax reverso.
 * 
 * @author João Lúcio
 * @since 26/05/2009
 */
public class ReverseAjaxServerLoadMonitor extends DefaultServerLoadMonitor {

	/* 
	 * Utilizado para poder dar timeout no sistema.
	 * O ajax ficaria dando iniciando nova thread no sistema, e nunca ocorreria
	 */
	private static final int	TEMPO_AJAX	= ConstantesContexto.TEMPO_SESSAO + 1000;

	/**
	 * Cria uma nova instância do tipo {@link ReverseAjaxServerLoadMonitor}.
	 */
	public ReverseAjaxServerLoadMonitor() {
		this.setMaxHitsPerSecond(10);
		this.connectedTime = TEMPO_AJAX;
		this.maxConnectedTime = TEMPO_AJAX;
	}
}
