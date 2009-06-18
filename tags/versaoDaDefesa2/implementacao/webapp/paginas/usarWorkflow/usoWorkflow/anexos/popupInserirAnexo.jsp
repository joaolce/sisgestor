<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<iframe src="anexoUsoWorkflow.do?method=iframeAnexo&usoWorkflow=<bean:write name="anexoUsoWorkflowForm" property="usoWorkflow" />" height="95" scrolling="no" frameborder="0" width="393">

</iframe>