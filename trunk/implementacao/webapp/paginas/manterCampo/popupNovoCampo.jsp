<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/manterCampo.do?method=salvar" onsubmit="campo.salvar(this); return false;" styleId="manterCampoForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<html:hidden property="workflow" styleId="workflowNovoCampo" />
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.nome"/>
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:text property="nome" styleId="nomeNovoCampo" size="51" maxlength="100" />
		</label>
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.obrigatorio"/>
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:select property="obrigatorio" styleId="obrigatorioNovoCampo">
				<html:option value="0" key="label.nao" />
				<html:option value="1" key="label.sim" />
			</html:select>
		</label>
		<label style="float: left; margin-left: 15px; margin-top: 15px;">
			<b>
				<bean:message key="label.tipo" />
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:select property="tipo" styleId="tipoNovoCampo">
				<html:option value="" />
				<html:optionsCollection name="manterCampoForm" property="tipos" label="descricao" value="id" />
			</html:select>
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