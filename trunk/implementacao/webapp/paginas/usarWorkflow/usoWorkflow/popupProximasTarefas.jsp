<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/usarWorkflow.do?method=proximaTarefa" onsubmit="usarWorkflow.proximaTarefa(this); return false;" styleId="usarWorkflowForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<div style="float: left;">
			<label>
				<b>
					<bean:message key="label.proximaTarefa"/>
					<span class="obrigatorio">*</span>
				</b>
				<html:select property="tarefa" styleId="proximaTarefa" style="width: 180px;">
						<html:option value="" />
						<logic:notEmpty name="usarWorkflowForm" property="proximasTarefas">
							<html:optionsCollection name="usarWorkflowForm" property="proximasTarefas" label="nome" value="id" />
						</logic:notEmpty>
						<logic:empty name="usarWorkflowForm" property="proximasTarefas">
							<html:option value="-1">
								<bean:message key="label.finalizarWorkflow" />
							</html:option>					
						</logic:empty>
				</html:select>
			</label>
		</div>
	</fieldset>

	<div style="clear: both; padding: 5px;" align="center">	
		<htmlSGR:submit titleKey="dica.salvar" styleClass="botaoOkCancelar" roles="5">
			<bean:message key="botao.salvar"/>
		</htmlSGR:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>