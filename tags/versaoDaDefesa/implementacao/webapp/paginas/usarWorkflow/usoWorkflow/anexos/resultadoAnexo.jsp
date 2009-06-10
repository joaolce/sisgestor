<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
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
		<%-- remover os actionerrors da sessão para exibi-lo somente uma vez --%>
		<c:remove var="org.apache.struts.action.ERROR" scope="session"/>
		<script type="text/javascript">
			anexoUsoWorkflow.anexoConcluido();
		</script>
	</body>
</html>