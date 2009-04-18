<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<div align= "center" id="divHistoricos" style="clear: left; height: 300px; overflow: auto; border: 1px solid gray; margin-top: 20px;">
<table style="width: 70%">
	<thead>
		<tr>
			<th style="display: none;">&nbsp;</th>
			<th width="15%"><bean:message key="label.historico.data" /></th>
			<th><bean:message key="label.historico.modificacao" /></th>
		</tr>
	</thead>
	<tbody id="corpoHistoricoUsarWorkflow" class="corpoTabelaClicavel" />
</table>
</div>