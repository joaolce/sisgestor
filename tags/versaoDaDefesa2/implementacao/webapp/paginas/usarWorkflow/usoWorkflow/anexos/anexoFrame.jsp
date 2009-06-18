<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
		<script type="text/javascript" src="js/libs/prototype.js"></script>
		<script type="text/javascript" src="js/usarWorkflow/anexoUsoWorkflow.js"></script>
	</head>
	<body>
		<%-- exibir mensagens de erro geradas --%>
		<logic:messagesPresent>
			<html:messages id="errorMessagesStruts">
				<div class="mensagemErro"><bean:write name="errorMessagesStruts"/></div>
			</html:messages>
		</logic:messagesPresent>
		<%-- remover os actionerrors da sessão para exibi-lo somente uma vez --%>
		<c:remove var="org.apache.struts.action.ERROR" scope="session"/>
		<html:form action="/anexoUsoWorkflow.do?method=incluirAnexo" enctype="multipart/form-data" onsubmit="$('cancelar').disabled = true;">
			<html:hidden property="usoWorkflow"/>
			<label>
				<bean:message key="label.enderecoArquivo" /> 
				(<bean:message key="label.tamanhoMaximoArquivo" />) 
				<br />
				<html:file property="arquivo" style="width:300px;" />
			</label>
			<br />
			<div align="center" style="margin-top: 10px; margin-right: 1px;">
				<htmlSGR:submit titleKey="dica.fechar" styleClass="botaoOkCancelar" property="cancelar" roles="6">
					<bean:message key="botao.incluir"/>
				</htmlSGR:submit>
				<html:button property="cancelar" styleId="cancelar" titleKey="dica.cancelar" onclick="window.parent.JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
					<bean:message key="botao.cancelar"/>
				</html:button>
			</div>
		</html:form>
	</body>
</html>