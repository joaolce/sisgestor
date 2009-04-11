<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<html:form action="/usarWorkflow.do?method=iniciarUso" onsubmit="usarWorkflow.iniciarUso(this); return false;" styleId="usarWorkflowForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<div style="float: left;">
			<label>
				<b>
					<bean:message key="label.workflow"/>
					<span class="obrigatorio">*</span>
				</b>
				<html:select property="workflow" styleId="workflow" style="width: 150px;">
						<html:option value="" />
						<html:optionsCollection name="usarWorkflowForm" property="listaWorkflows" label="nome" value="id" />
				</html:select>
			</label>
		</div>
	</fieldset>

	<div style="clear: both; padding: 5px;" align="center">	
		<htmlSGR:submit titleKey="dica.iniciar" styleClass="botaoOkCancelar" roles="5">
			<bean:message key="botao.iniciar"/>
		</htmlSGR:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>