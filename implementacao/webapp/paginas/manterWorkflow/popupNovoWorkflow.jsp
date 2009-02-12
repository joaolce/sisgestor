<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/ManterWorkflowDWR.js"></script>
<script type="text/javascript" src="js/manterWorkflow/manterWorkflow.js"></script>

<html:form action="/manterWorkflow.do?method=salvar" onsubmit="workflow.salvar(this); return false;" styleId="manterWorkflowForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<div style="float: left;">
			<label style="float: left; margin-top: 15px;">
				<b>
					<bean:message key="label.nome"/>
					<span class="obrigatorio">*</span>
				</b>
				<br />
				<html:text property="nome" size="51" maxlength="100" />
			</label> 
			<label style="float: left; margin-top: 15px; margin-left: 15px;">
				<b>
					<bean:message key="label.ativo"/>
					<span class="obrigatorio">*</span>
				</b>
				<br />
				<html:select property="ativo" styleId="ativo">
					<html:option value="0" key="label.nao" />
					<html:option value="1" key="label.sim" />
				</html:select>
			</label>
		</div>
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.descricao"/>
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:textarea property="descricao" styleId="descricao" rows="4" cols="50" 
							onkeypress="workflow.contaChar();" 
							onkeydown="workflow.contaChar();" 
							onkeyup="workflow.contaChar();" />
			<br />
			<bean:message key="label.maximoCaracteres" arg0="200"/>:
			<span id="contagem" style="color: red;"></span>
		</label>
	</fieldset>
	<div style="clear: both; padding: 5px;" align="center">	
		<html:submit titleKey="dica.salvar" styleClass="botaoOkCancelar">
			<bean:message key="botao.salvar"/>
		</html:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>