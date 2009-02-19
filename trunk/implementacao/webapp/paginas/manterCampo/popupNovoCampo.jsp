<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/manterCampo.do?method=salvar" onsubmit="campo.salvar(this); return false;" styleId="manterCampoForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.nome"/>
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:text property="nome" size="51" maxlength="100" />
		</label> 
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.tipo" />
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:select property="tipo" styleId="tipo">
				<html:option value="" />
				<html:optionsCollection name="manterCampoForm" property="listaTipos" label="descricao" value="id" />
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