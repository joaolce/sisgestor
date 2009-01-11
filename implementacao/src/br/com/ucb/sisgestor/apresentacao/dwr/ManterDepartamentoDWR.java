/*
 * Projeto: sisgestor
 * Criação: 10/01/2009 por Thiago
 */
package br.com.ucb.sisgestor.apresentacao.dwr;

import java.util.List;

import br.com.ucb.sisgestor.entidade.Departamento;
import br.com.ucb.sisgestor.negocio.DepartamentoBO;
import br.com.ucb.sisgestor.negocio.impl.DepartamentoBOImpl;
import br.com.ucb.sisgestor.util.ListaResultadoDTO;
import br.com.ucb.sisgestor.util.constantes.DadosContexto;

/**
 * Objeto ManterDepartamentoDWR do projeto.
 *
 * @author Thiago
 * @since 10/01/2009
 */
public class ManterDepartamentoDWR extends BaseDWR{
	
	/**
	 * Pesquisar os departamentos com os parâmetros preenchidos
	 * 
	 * @param nome
	 * @param login
	 * @return List
	 */
	public ListaResultadoDTO pesquisar(String nome, Integer paginaAtual) {
		DepartamentoBO departamentoBO = DepartamentoBOImpl.getInstancia();
		
		List listaDepartamentos = departamentoBO.getByNome(nome, paginaAtual);
		
		ListaResultadoDTO dto = new ListaResultadoDTO();
		
		dto.setColecaoParcial(listaDepartamentos);
		
		//Busca o total de registros
		if(paginaAtual == null && !listaDepartamentos.isEmpty()){
			Integer total = departamentoBO.getTotalRegistros(nome);
			setSessionAttribute(DadosContexto.TOTAL_PESQUISA_SESSAO, total);
			dto.setTotalRegistros(total);
		}else{
			dto.setTotalRegistros((Integer) getSessionAttribute(DadosContexto.TOTAL_PESQUISA_SESSAO));
		}
	
		return dto;
	}
	
	/**
	 * Pesquisa o {@link Departamento} pelo id 
	 * 
	 * @param idDepartamento
	 * @return Departamento
	 */
	public Departamento getById(Integer idDepartamento){
		DepartamentoBO departamentoBO = DepartamentoBOImpl.getInstancia();
		return departamentoBO.obter(idDepartamento);
	}
	
	/**
	 * 
	 * Obtem um {@link List} de todos {@link Departamento}
	 *
	 * @return
	 */
	public List<Departamento> obterTodos(){
		DepartamentoBO departamentoBO = DepartamentoBOImpl.getInstancia();
		return departamentoBO.obterTodos();
	}

}
