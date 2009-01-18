<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/ManterUsuarioDWR.js"></script>
<script type="text/javascript" src="js/manterDepartamento/manterUsuario.js"></script>

<html:form action="/manterUsuario.do?method=salvar" onsubmit="usuario.salvar(this); JanelaFactory.fecharJanelaAtiva(); return false;" styleId="manterUsuarioForm">

	<fieldset style="margin: 5pt auto; padding: 10px; width: 95%;">
		<div style="float: left;">
			<label>
				<b><bean:message key="label.login"/>:</b>
				<html:text property="login" size="11" maxlength="10" />
			</label> 
			<br />
			<label>
				<b><bean:message key="label.nome"/>:</b>
				<html:text property="nome" size="51" maxlength="50" />
			</label> 
			<br />
			<label>
				<b><bean:message key="label.email"/>: </b>
				<html:text property="email" size="41" maxlength="40" />
			</label>
			<br />
		</div>
			<div style="float: left;">
				<label>
					<b><bean:message key="label.departamento"/>: </b>
					<html:select property="departamento" styleId="departamento">
						<html:option value="" />
						<html:optionsCollection name="manterUsuarioForm" property="listaDepartamentos" label="sigla" value="id" />
					</html:select>
				</label>
			</div>
	</fieldset>
	<div style="clear: both; padding: 5px;" align="center">	
		<html:submit titleKey="dica.salvar" styleClass="botaoOkCancelar">
			<bean:message key="label.salvar"/>
		</html:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>