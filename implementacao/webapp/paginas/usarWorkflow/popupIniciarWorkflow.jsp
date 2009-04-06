<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/usarWorkflow.do?method=iniciarWorkflow" onsubmit="usarWorflow.iniciarWorkflow(); return false;" styleId="usarWorkflowForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<div style="float: left;">
			<label>
				<b><bean:message key="label.workflow"/></b>
				<html:select property="workflow" styleId="workflow" style="width: 150px;">
						<html:option value="" />
						<html:optionsCollection name="usarWorkflowForm" property="listaWorkflows" label="nome" value="id" />
				</html:select>
			</label>
		</div>
	</fieldset>

	<div style="clear: both; padding: 5px;" align="center">	
		<html:submit titleKey="dica.iniciar" styleClass="botaoOkCancelar">
			<bean:message key="botao.iniciar"/>
		</html:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>