<jsp:directive.page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" /> 
<jsp:directive.taglib uri="http://struts.apache.org/tags-bean" prefix="bean" /> 
<jsp:directive.taglib uri="http://java.sun.com/jstl/core" prefix="c" /> 
<jsp:directive.taglib uri="http://struts.apache.org/tags-html" prefix="html" />
<jsp:directive.taglib uri="http://struts.apache.org/tags-logic" prefix="logic" /> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
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
	</body>
</html>