<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<link href="css/calendar.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript" src="dwr/interface/UsarWorkflowDWR.js"></script>
<script type="text/javascript" src="js/usarWorkflow/usarWorkflow.js"></script>
<script type="text/javascript" src="js/usarWorkflow/anexoUsoWorkflow.js"></script>
<script type="text/javascript" src="js/util/calendar.js"></script>
<script type="text/javascript" src="js/util/calendar-br.js"></script>

<div class="bordas" id="BordaExterna">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#iniciarWorkflow" titleKey="dica.workflow.iniciar" onclick="usarWorkflow.popupIniciarWorkflow();" linkName="iniciarWorkflow" roles="5">
			<html:img srcKey="imagem.iniciarWorkflow" width="23" height="22" />
		</htmlSGR:link>
	</div>
	<div style="height: 35px; padding-left: 20px; padding-top: 5px;">
		<htmlSGR:link href="#atualizarRegistros" titleKey="dica.atualizar.usos" onclick="usarWorkflow.pesquisar();" linkName="atualizarRegistros" roles="5" >
			<html:img srcKey="imagem.atualizarRegistros" width="100" height="21" />
		</htmlSGR:link>
	</div>
	<div id="divWorkflows" style="clear: left; height: 420px; overflow: auto; border: 1px solid gray; margin-top: 5px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th style="display: none;">&nbsp;</th>
					<th width="10%"><bean:message key="label.registro" /></th>
					<th><bean:message key="label.workflow" /></th>
					<th><bean:message key="label.processo" /></th>
					<th><bean:message key="label.atividade" /></th>
					<th><bean:message key="label.tarefa" /></th>
					<th id="thUsuario" style="display: none;"><bean:message key="label.usuario" /></th>
					<th width="15%"><bean:message key="label.dataInicio" /></th>
				</tr>
			</thead>
			<tbody id="corpoUsarWorkflow" class="corpoTabelaClicavel"/>
		</table>
	</div>
	
	<div class="bordas" style="margin-top: 10px; height: 25px; text-align: center; padding: 10px;">
		<label>
			<bean:message key="mensagem.acessoTarefa" />
		</label>
		<htmlSGR:link href="#" titleKey="dica.abrirRegistro" styleId="botaoUsarTarefa" roles="5">
			<bean:message key="link.cliqueAqui"/>
		</htmlSGR:link>
	</div>
</div>
