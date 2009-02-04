<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div style="width: 99.9%; background-color:#003366;">
	<div class="cab" style="width: 69%; float: left;">
		<div style="margin-left: 15px; float: left;">
			<html:link href="principal.do?method=entrada" titleKey="dica.paginaInicial">
				<html:img srcKey="imagem.logoPequeno" align="left" />
			</html:link>
		</div>
		<div style="margin-left: 120px; margin-top: 3px;">
			<b><bean:message key="aplicacao.titulo"/></b>
			<br />
			<span id="tituloTela"><bean:message key="label.carregando"/></span>
		</div>
	</div>
	<div class="cab" style="width: 30%; height: 35px; margin-left: auto;" >
		<logic:present name="usuarioSessao">
			<div title="<bean:write name="usuarioSessao" property="nome"/>" style="width: 161px;height: 16px; overflow: hidden; float: left;">
				<bean:write name="usuarioSessao" property="nome"/>
			</div>
			(<bean:write name="usuarioSessao" property="login" />)
			<div class="textoPequeno" style="white-space: nowrap;">
				<bean:write name="dataLogin" /> 
				<bean:write name="horaLogin" format="HH:mm"/>
			</div>
		</logic:present>
		<logic:notPresent name="usuarioSessao">
			<i><bean:message key="mensagem.naologado"/></i>
		</logic:notPresent>
	</div>
</div>