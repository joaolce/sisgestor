<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/manterProcesso.do?method=salvarFluxo" onsubmit="processo.salvarFluxo(); return false;" styleId="definirFluxoManterProcessoForm">
	<html:hidden property="workflow" styleId="workflowFluxo" />
	<div style="float: left; width: 600px;">
		<label>
			<b><bean:message key="label.definicaoFluxo"/></b>
		</label>
		<div id="divFluxos" style="float: left; width: 830px; height: 400px; overflow: scroll; text-align: center;" class="bordas">
			
		</div>
	</div>
	<div style="clear: both; padding: 5px; vertical-align: sub;" align="center">	
		<html:submit titleKey="dica.salvar" styleClass="botaoOkCancelar">
			<bean:message key="botao.salvar"/>
		</html:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
		<html:button property="limpar" titleKey="dica.limpar" onclick="fluxo.limparFluxo();" styleClass="botaoOkCancelar">
			<bean:message key="botao.limpar"/>
		</html:button>
	</div>
</html:form>