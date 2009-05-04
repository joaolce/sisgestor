<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/UsarWorkflowDWR.js"></script>
<script type="text/javascript" src="js/usarWorkflow/usarWorkflow.js"></script>
<script type="text/javascript" src="js/usarWorkflow/registrosFinalizados.js"></script>

<div class="bordas" id="BordaExterna">
	<!-- FIELDSET PARA PESQUISA DE REGISTROS FINALIZADOS -->
	<fieldset style="padding: 10px; width: 60%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/usarWorkflow.do" onsubmit="registrosFinalizados.pesquisar(); return false;" styleId="manterUsuarioForm">
			<div style="float: left;">
				<label style="float: left;">
					<bean:message key="label.login" />
					<br />
					<input type="text" name="numeroRegistroPesquisa" id="numeroRegistroPesquisa" size="11" maxlength="11" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="150" />
				</label>
			</div>
			<div style="padding-top: 14px; clear: both;">
				<html:submit styleClass="botaoOkCancelar">
					<bean:message key="botao.pesquisar" />
				</html:submit>
			</div>
		</html:form>
	</fieldset>

	<div id="divRegistrosFinalizados" style="clear: left; height: 300px; overflow: auto; border: 1px solid gray; margin-top: 5px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th style="display: none;">&nbsp;</th>
					<th width="10%"><bean:message key="label.registro" /></th>
					<th><bean:message key="label.workflow" /></th>
					<th><bean:message key="label.dataHoraFinalizacao" /></th>
				</tr>
			</thead>
			<tbody id="corpoVisualizarRegistros" class="corpoTabelaClicavel" />
		</table>
	</div>
	
	<div class="bordas" style="margin-top: 10px; height: 25px; text-align: center; padding: 10px;">
		<label>
			<bean:message key="mensagem.acessoTarefa.finalizado" />
		</label>
		<html:link href="#" titleKey="dica.abrirRegistro" styleId="botaoVisualizarRegistro">
			<bean:message key="link.cliqueAqui"/>
		</html:link>
	</div>
</div>