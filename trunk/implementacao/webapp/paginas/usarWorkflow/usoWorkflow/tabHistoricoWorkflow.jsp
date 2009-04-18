<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<table align="center" style="width: 90%">
	<thead>
		<tr>
			<th style="display: none;">&nbsp;</th>
			<th width="19%"><bean:message key="label.historico.data" /></th>
			<th width="45%"><bean:message key="label.usuario" /></th>
			<th width="36%"><bean:message key="label.historico.modificacao" /></th>
		</tr>
	</thead>
	<tbody id="corpoHistoricoUsarWorkflow" class="corpoTabelaClicavel" />
</table>
