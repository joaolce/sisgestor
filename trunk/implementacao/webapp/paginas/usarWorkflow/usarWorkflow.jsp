<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<script type="text/javascript" src="dwr/interface/UsarWorkflowDWR.js"></script>
<script type="text/javascript" src="js/usarWorkflow/usarWorkflow.js"></script>
<script type="text/javascript" src="js/usarWorkflow/anexoUsoWorkflow.js"></script>

<div class="bordas" id="BordaExterna">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#iniciarWorkflow" titleKey="dica.workflow.iniciar" onclick="usarWorkflow.popupIniciarWorkflow();" linkName="iniciarWorkflow" roles="5">
			<html:img srcKey="imagem.iniciarWorkflow" width="20" height="19" />
		</htmlSGR:link>
	</div>
	<div id="divWorkflows" style="clear: left; height: 420px; overflow: auto; border: 1px solid gray; margin-top: 50px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th style="display: none;">&nbsp;</th>
					<th width="10%"><bean:message key="label.registro" /></th>
					<th><bean:message key="label.workflow" /></th>
					<th><bean:message key="label.processo" /></th>
					<th><bean:message key="label.atividade" /></th>
					<th><bean:message key="label.tarefa" /></th>
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
		<html:link styleId="botaoUsarTarefa" href="#">
			<bean:message key="link.cliqueAqui"/>
		</html:link>
	</div>
</div>