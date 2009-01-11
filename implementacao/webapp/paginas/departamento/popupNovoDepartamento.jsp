<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>


<html:form action="/manterDepartamento.do?method=salvar" onsubmit="demanda.salvar(this); return false;" styleId="manterDepartamentoForm">

	<fieldset style="margin: 5pt auto; padding: 10px; width: 95%;">
		<div>
			<b><bean:message key="label.nome"/>:</b>
			<html:text property="nome" style="width: 300px;"/>
		</div>
		<br />
		<div>
			<div style="float: left; margin-right: 10px;" >
				<b><bean:message key="label.sigla"/>:</b>
				<html:text property="sigla" style="width: 60px;"/>
				<br />
				<br />
				<b><bean:message key="label.email" />: </b>
				<html:text property="email" style="width: 230px;"/>
				<br />
				<br />
				<b><bean:message key="label.departamentoSuperior"/>: </b>
				<html:select property="departamentoSuperior" style="width: 189px;" styleId="departamentoSuperior" titleKey="dica.departamento.selecione">
					<html:option value="" />
					<html:optionsCollection name="manterDepartamentoForm" property="listaDepartamentos" label="nome" value="id"/>
				</html:select>
			</div>
		</div>
	</fieldset>
	
	<div style="clear: both; padding: 5px;" align="center">	
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
		
		<html:submit titleKey="dica.salvar" styleClass="botaoOkCancelar">
			<bean:message key="label.salvar"/>
		</html:submit>
	</div>
</html:form>