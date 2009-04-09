<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<script type="text/javascript" src="dwr/interface/UsarWorkflowDWR.js"></script>
<script type="text/javascript" src="js/usarWorkflow/aposIncluirAnexo.js"></script>

<input type="hidden" id="idUsoWorkflow" name="idUsoWorkflow" value="<bean:write name='idUsoWorkflow' scope='session' />" />
<c:remove var="idUsoWorkflow" scope="session"/>
