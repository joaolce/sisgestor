<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/ManterDepartamentoDWR.js"></script>
<script type="text/javascript" src="js/manterDepartamento/manterDepartamento.js"></script>

<div class="bordas" id="BordaExterna">

	<div id="divMenuOpcoes">
		<html:link href="#novoDepartamento" titleKey="dica.departamento.novo" onclick="departamento.popupNovoDepartamento();" linkName="novoDepartamento">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</html:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE DEPARTAMENTOS -->
	<fieldset style="padding: 10px; width: 50%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterDepartamento.do" onsubmit="departamento.pesquisar(); return false;" styleId="manterUsuarioForm">
			<div style="float: left; width: 350px;">
				<div style="float: left;">
					<label>
						<bean:message key="label.sigla" />
						<br />
						<input type="text" name="siglaPesquisa" id="siglaPesquisa" size="11" maxlength="10" />
					</label>
				</div>
				<div style="float: right;">
					<label>
						<bean:message key="label.nome" />
						<br />
						<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="50" />
					</label>
				</div>
			</div>
			<div style="padding-top: 14px; clear: both;">
				<html:submit styleClass="botaoOkCancelar">
					<bean:message key="botao.pesquisar" />
				</html:submit>
			</div>
		</html:form>
	</fieldset>

	<div id="divDepartamentos" style="clear: left; width: 956px; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 99.9%">
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
	
	<html:form action="/manterDepartamento.do?method=atualizar" onsubmit="departamento.atualizar(this); return false;" styleId="formSalvar" style="display: none; margin-top: 10px;">
		<!-- FIELDSET PARA DADOS DO DEPARTAMENTO -->
		<html:hidden property="id"/>
		<fieldset style="padding: 15px; width: 50%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.departamento"/>
			</legend>
			<div>
				<label>
					<b><bean:message key="label.sigla"/>:</b>
					<html:text property="sigla" size="11" maxlength="10" />
				</label> 
				<br />
				<label style="margin-top: 3px;">
					<b><bean:message key="label.nome"/>:</b>
					<html:text property="nome" size="51" maxlength="50" />
				</label> 
				<br />
				<label style="margin-top: 3px;">
					<b><bean:message key="label.departamentoSuperior"/>: </b>
					<html:select property="departamentoSuperior" styleId="departamentoSuperior">
						<html:option value="" />
						<html:optionsCollection name="manterDepartamentoForm" property="listaDepartamentos" label="sigla" value="id" />
					</html:select>
				</label>
				<br />
				<label style="margin-top: 3px;">
					<b><bean:message key="label.email"/>: </b>
					<html:text property="email" size="41" maxlength="40" />
				</label> 
			</div>
			<div style="clear: both; padding: 5px;" align="center">
				<html:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar">
					<bean:message key="botao.atualizar"/>
				</html:submit>
				<html:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="departamento.excluir();">
					<bean:message key="botao.excluir"/>
				</html:button>
			</div>
		</fieldset>
	</html:form>
</div>
	