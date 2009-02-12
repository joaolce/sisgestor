<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/manterProcessoDWR.js"></script>
<script type="text/javascript" src="js/manterProcesso/manterProcesso.js"></script>

<html:form action="/manterProcesso.do?method=salvar" onsubmit="processo.salvar(this); return false;" styleId="manterProcessoForm">
	Desenvolver novo processo...
</html:form>