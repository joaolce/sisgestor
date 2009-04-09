<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<html:form action="/manterAtividade.do?method=salvarFluxo" onsubmit="atividade.salvarFluxo(); return false;" styleId="definirFluxoManterAtividadeForm">
	<html:hidden property="processo" styleId="processoFluxo" />
	<div style="float: left; width: 600px;">
		<label>
			<b><bean:message key="label.definicaoFluxo"/></b>
		</label>
		<div id="divFluxos" style="float: left; width: 830px; height: 400px; overflow: hidden; text-align: center;" class="bordas">
			
		</div>
	</div>
	<div style="clear: both; padding: 5px; vertical-align: sub;" align="center">	
		<htmlSGR:submit titleKey="dica.salvar" styleClass="botaoOkCancelar" roles="4">
			<bean:message key="botao.salvar"/>
		</htmlSGR:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
		<htmlSGR:button property="limpar" titleKey="dica.limpar" onclick="fluxo.limparFluxo();" styleClass="botaoOkCancelar" roles="4">
			<bean:message key="botao.limpar"/>
		</htmlSGR:button>
	</div>
</html:form>