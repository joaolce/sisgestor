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
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</html:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE DEPARTAMENTOS -->
	<html:form action="/manterDepartamento.do" onsubmit="departamento.pesquisar(); return false;" styleId="manterUsuarioForm">
		<div style="float: left; margin-right: 10px;">
			<label>
				<bean:message key="label.nome" />
				<br />
				<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="50" />
			</label>
		</div>
		<div style="padding-top: 14px; clear: both;">
			<html:submit styleClass="botaoOkCancelar">
				<bean:message key="label.pesquisar" />
			</html:submit>
		</div>
	</html:form>

	<div id="divDepartamentos" style="clear: left; width: 956px; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 20px;">
		<table style="width: 98%">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th><bean:message key="label.sigla" /></th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.email" /></th>
					<th><bean:message key="label.departamentoSuperior" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterDepartamento" class="corpoTabelaClicavel"></tbody>
		</table>
	</div>
	
	<html:form action="/manterDepartamento.do?method=atualizar" onsubmit="departamento.atualizar(this); return false;" styleId="formSalvar" style="display: none; margin-top: 5px;">
		<!-- FIELDSET PARA DADOS DO DEPARTAMENTO -->
		<html:hidden property="id"/>
		<fieldset style="padding: 15px; width: 50%;  margin: 5 auto; ">
			<legend>
				<bean:message key="label.dados.departamento"/>
			</legend>
			<div>
				<label>
					<b><bean:message key="label.sigla"/>:</b>
					<html:text property="sigla" size="11" maxlength="10" />
				</label> <br />
				<label>
					<b><bean:message key="label.nome"/>:</b>
					<html:text property="nome" size="51" maxlength="50" />
				</label> <br />
				<label>
					<b><bean:message key="label.email"/>: </b>
					<html:text property="email" size="41" maxlength="40" />
				</label> <br />
				<label>
					<b><bean:message key="label.departamentoSuperior"/>: </b>
				</label>
			</div>
			<div style="clear: both; padding: 5px;" align="center">
				<html:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="departamento.excluir();">
					<bean:message key="label.excluir"/>
				</html:button>
				<html:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar">
					<bean:message key="label.atualizar"/>
				</html:submit>
			</div>
		</fieldset>
	</html:form>
</div>
	