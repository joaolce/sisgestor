<%@ page contentType="text/html; charset=ISO-8859-1" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div class="bordas" id="BordaExterna" style="padding-left: 10px;">
	<span class="titulo3"><bean:message key="label.falhaAplicacao"/></span>
	<br />
	<logic:notPresent name="errorContainer">
		<html:errors />
	</logic:notPresent>
	<logic:present name="errorContainer">
		<bean:message key="label.exceptionDefault"/>
	</logic:present>
	<br />
	<br />
	<div>
		<div style="float: left; margin-right: 5px; height: 50px;">
			<span class="msgErro"><bean:message key="label.mensagemErro"/>: </span>
		</div>
		<div>
			<logic:notPresent name="errorContainer">
				<bean:write name="org.apache.struts.action.EXCEPTION" />
			</logic:notPresent>
			<logic:present name="errorContainer">
				<div>
					<bean:message key="label.statusCode"/>:
					<bean:write name="javax.servlet.error.status_code" ignore="true" />
				</div>
				<div>
					<logic:present name="javax.servlet.error.exception" property="rootCause">
						<c:set var="rootCause">
							<bean:write name="javax.servlet.error.exception" property="rootCause" />
						</c:set>
					</logic:present>
					<logic:present name="javax.servlet.error.message">
						<bean:message key="label.mensagem"/>:
						<bean:write name="javax.servlet.error.message" />
					</logic:present>
					<logic:present name="javax.servlet.error.exception" property="message">
						<bean:write name="javax.servlet.error.exception" property="message" />
					</logic:present>
					<bean:write name="rootCause" ignore="true" />
				</div>
				<logic:present name="javax.servlet.error.exception">
					<logic:notEmpty name="rootCause">
						<c:set var="org.apache.struts.action.EXCEPTION">
							<bean:write name="rootCause" />
						</c:set>
					</logic:notEmpty>
					<logic:empty name="rootCause">
						<c:set var="org.apache.struts.action.EXCEPTION">
							<bean:write name="javax.servlet.error.exception" />
						</c:set>
					</logic:empty>
				</logic:present>
			</logic:present>
		</div>
	</div>
	<div style="clear: both;">
		<span class="msgErro"><bean:message key="label.stackTrace"/>: </span>
		<html:link titleKey="dica.abrir" href="#" onclick="$('divErros123').visible() ? Effect.Fade('divErros123') : Effect.Appear('divErros123');">
			...
		</html:link>
		<div class="bordas" style="display: none; overflow: auto; width: 800px; height: 350px;" id="divErros123">
			<logic:present name="org.apache.struts.action.EXCEPTION" property="stackTrace">
				<logic:iterate id="stack" name="org.apache.struts.action.EXCEPTION" property="stackTrace">
					<bean:write name="stack" />
				</logic:iterate>
			</logic:present>
		</div>
	</div>
</div>