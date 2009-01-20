<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/ManterUsuarioDWR.js"></script>
<script type="text/javascript" src="js/manterUsuario/manterUsuario.js"></script>

<html:form action="/manterUsuario.do" onsubmit="usuario.atualizarPermissoes(this); JanelaFactory.fecharJanelaAtiva(); return false;" styleId="manterUsuarioForm">
	<fieldset style="margin: 5pt auto; padding: 10px; width: 95%;">
		<div id="tabPermissoes" style="margin: 6px;">
			<tiles:insert definition="includeTabPermissoes"/>
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