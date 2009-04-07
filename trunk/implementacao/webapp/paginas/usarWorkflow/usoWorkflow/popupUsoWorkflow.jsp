<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<fieldset style="margin: 5pt auto; padding: 10px;">
	<div id="divMenuOpcoes">
		<html:link href="#anexos" titleKey="dica.anexo" onclick="alert('implemente-me');" linkName="anexos">
			<html:img srcKey="imagem.anexos" width="20" height="19"/>
		</html:link>
	</div>
	
	<div style="height: 60px;">
		<label>
			<bean:message key="label.tarefa" />
		</label>
	</div>
	
	<div class="divAba">
		<a href="#" id="tabCamposAncora" accesskey="c" title="IE: Alt + c, Enter / Firefox: Alt + Shift + c">
			<bean:message key="aba.campos" />
		</a>
		<a href="#" id="tabHistoricoAncora" accesskey="h" title="IE: Alt + h, Enter / Firefox: Alt + Shift + h">
			<bean:message key="aba.historico" />
		</a>
	</div>
	
	<html:form action="/usarWorkflow.do?method=confirmar" onsubmit="usarWorkflow.confirmar(this); return false;" styleId="usoWorkflowForm">
		<div class="bordas">
			<div id="tabCampos" style="margin: 5px; height: 355px;">
				<tiles:insert definition="includeTabCamposWorkflow"/>
			</div>
			<div id="tabHistorico" style="margin: 5px; height: 355px;">
				<tiles:insert definition="includeTabHistoricoWorkflow"/>
			</div>
		</div>
		
		<div style="clear: both; padding: 5px;" align="center">	
			<htmlSGR:submit titleKey="dica.salvar" styleClass="botaoOkCancelar" roles="5">
				<bean:message key="botao.salvar"/>
			</htmlSGR:submit>
			<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
				<bean:message key="botao.cancelar"/>
			</html:button>
		</div>
	</html:form>
</fieldset>