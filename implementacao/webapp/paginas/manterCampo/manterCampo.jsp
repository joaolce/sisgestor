<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<div class="bordas" id="BordaExterna" style="height: 496px;">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#novoCampo" titleKey="dica.campo.novo" onclick="campo.popupNovoCampo();" linkName="novoCampo" roles="4">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</htmlSGR:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE CAMPO -->
	<fieldset style="padding: 10px; width: 70%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterCampo.do" onsubmit="campo.pesquisar(); return false;" styleId="manterCampoForm">
			<div style="padding-top: 14px; clear: both;">
				<html:submit styleClass="botaoOkCancelar">
					<bean:message key="botao.pesquisar" />
				</html:submit>
			</div>
		</html:form>
	</fieldset>

	<div id="divCampos" style="clear: left; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		Implemente-me
	</div>
	
	<!-- FIELDSET PARA DADOS DO CAMPO -->
	<html:form action="/manterCampo.do?method=atualizar" onsubmit="campo.atualizar(this); return false;" styleId="formSalvarCampo" style="display: none; margin-top: 10px;">
		
	</html:form>
</div>
	