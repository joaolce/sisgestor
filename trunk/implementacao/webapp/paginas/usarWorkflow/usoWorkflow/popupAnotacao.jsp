<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<script type="text/javascript" src="js/usarWorkflow/usarWorkflow.js"></script>

<html:form action="/usarWorkflow.do?method=salvarAnotacao" onsubmit="usarWorkflow.salvarAnotacao(this); return false;">
	<html:hidden property="id" />
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<div id="divAnotacao" style="float: left;">
			<label style="float: left; margin-top: 3px;">
					<b style="vertical-align: top;">
						<bean:message key="label.anotacao" />
					</b>
					<html:textarea property="anotacao" styleId="anotacao" rows="9" cols="78" onkeypress="usarWorkflow.contaChar();" onkeydown="usarWorkflow.contaChar();" onkeyup="usarWorkflow.contaChar();"/>
					<br />
					<bean:message key="label.maximoCaracteres" arg0="500"/>:
					<span id="contagemAnotacoes" style="color: red;"></span>
				</label> 
		</div>
	</fieldset>

	<div style="clear: both; padding: 5px;" align="center">	
		<htmlSGR:submit titleKey="dica.salvar" styleClass="botaoOkCancelar" roles="5">
			<bean:message key="botao.salvar"/>
		</htmlSGR:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>