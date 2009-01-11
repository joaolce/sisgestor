<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/ManterDepartamentoDWR.js"></script>
<script type="text/javascript" src="js/manterDepartamento/manterDepartamento.js"></script>

<div class="bordas" id="BordaExterna" style="padding: 10px;">

	<div id="divMenuOpcoes">
		<html:link href="#" titleKey="dica.novoDepartamento" onclick="departamento.popupNovoDepartamento();">
			<html:img srcKey="imagem.novo" width="20" height="19"/>
		</html:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE DEPARTAMENTOS -->
	<html:form action="/manterDepartamento.do" onsubmit="departamento.pesquisar(); return false;" styleId="manterUsuarioForm">
		<div style="float: left; margin-right: 10px;">
			<label>
				<bean:message key="label.nome"/>
				<br/>
				<input type="text" style="width: 320px" id="nomePesquisa" maxlength="200"/>
			</label>
		</div>
		<div style="padding-top: 14px; clear: both;">
			
			<html:submit styleClass="botaoOkCancelar">
				<bean:message key="label.pesquisar"/>
			</html:submit>
		</div>
	</html:form>

	<div id="divDepartamentos" style="clear: left; width: 956px; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 20px;">
		<table style="width: 98%">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th><bean:message key="label.sigla"/></th>
					<th><bean:message key="label.nome"/></th>
					<th><bean:message key="label.email"/></th>
					<th><bean:message key="label.departamentoSuperior"/></th>
				</tr>
			</thead>
			<tbody id="corpoManterDepartamento" class="corpoTabelaClicavel"></tbody>
		</table>
	</div>
	<html:form action="/manterDepartamento.do?method=atualizar" onsubmit="departamento.atualizar(this); return false;" styleId="formSalvar" style="display: none; margin-top: 5px;">
		<!-- FIELDSET PARA DADOS DO DEPARTAMENTO -->
		<html:hidden property="id"/>
		<fieldset style="padding: 15px; width: 50%;  margin: 5 auto;">
			<div>
				<b><bean:message key="label.nome"/>:</b>
				<html:text property="nome" style="width: 300px;"/>
			</div>
			<div>
				<div style="float: left; margin-right: 10px;" >
					<b><bean:message key="label.sigla"/>:</b>
					<html:text property="sigla" style="width: 60px;"/>
					<br />
					<b><bean:message key="label.email"/>: </b>
					<html:text property="email" style="width: 230px;"/>
					<br />
					<b><bean:message key="label.departamentoSuperior"/>: </b>
				</div>
			</div>
			<div style="clear: both; padding: 5px;" align="center">
				<html:button property="excluir" titleKey="dica.departamento.excluir" 
						styleClass="botaoOkCancelar" onclick="departamento.excluir(this);">
					<bean:message key="botao.excluir"/>
				</html:button>
				
				<html:submit titleKey="dica.salvar" styleClass="botaoOkCancelar">
					<bean:message key="label.salvar"/>
				</html:submit>
			</div>
		</fieldset>
	</html:form>
</div>
	