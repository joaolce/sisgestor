<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html:html xhtml="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title><tiles:getAsString name="titulo"/></title>

	<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="imagens/logoPequeno.jpg" rel="shortcut icon" />
			
	<script type="text/javascript" src="js/libs/prototype.js"></script>
	<script type="text/javascript" src="js/libs/scriptaculous.js"></script>
	<script type="text/javascript" src="js/libs/masks.js"></script>
	<script type="text/javascript" src="js/libs/date.js"></script>
	
	<script type="text/javascript" src="dwr/engine.js"></script>
	<script type="text/javascript" src="dwr/util.js"></script>
	<script type="text/javascript" src="js/util/overideDWR.js"></script>
	
	<script type="text/javascript" src="js/util/oreaculousConfig.js"></script>
	<script type="text/javascript" src="js/util/oreaculous.js"></script>
	<script type="text/javascript" src="js/util/utils.js"></script>
	<script type="text/javascript" src="js/util/loading.js"></script>
	<script type="text/javascript" src="js/util/janelasComuns.js"></script>
	<script type="text/javascript" src="js/util/abas.js"></script>
	<script type="text/javascript" src="js/util/requestUtils.js"></script>
	<script type="text/javascript" src="js/util/tabelas.js"></script>
	
	<script type="text/javascript" src="js/configurador_layout.js"></script>
</head>
<body>
	<tiles:insert attribute="topo"/>
	<tiles:insert attribute="menu"/>
	<noscript class="mensagemErro">
		<bean:message key="mensagem.javascript"/>
	</noscript>
	<div id="principal" style="display: none;">
		<tiles:insert attribute="corpo" />
		
		<%-- exibir mensagens de erro geradas --%>
		<logic:empty name="messageStatus" >
			<c:set var="messageStatus" value="true" />
		</logic:empty>
		<logic:messagesPresent>
			<span style="display: none;" id="messageStatus">
				<bean:write name="messageStatus" />
			</span> 
			<span id="errorMessagesStruts" style="display: none;">
				<html:messages id="errorMessagesStruts">
					<i><bean:write name="errorMessagesStruts" /></i>
				</html:messages>
			</span>
			<%-- remover os actionerrors da sessão para exibi-lo somente uma vez --%>
			<c:remove var="org.apache.struts.action.ERROR" scope="session"/>
			<c:remove var="messageStatus" />
		</logic:messagesPresent>
	</div>
</body>
</html:html>
