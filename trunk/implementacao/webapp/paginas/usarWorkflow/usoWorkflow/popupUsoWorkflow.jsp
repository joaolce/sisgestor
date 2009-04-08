<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<fieldset style="margin: 5pt auto; padding: 10px;">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#anexos" titleKey="dica.anexo" onclick="usarWorkflow.popupVisualizarAnexos();" linkName="anexos" roles="6">
			<html:img srcKey="imagem.anexos" width="20" height="19"/>
		</htmlSGR:link>
	</div>
	
	<div class="bordas" style="width: 50%; padding: 5px; overflow: scroll;">
		<label>
			<b><bean:message key="label.dataInicio" /></b>: DATA INICIO
		</label>
		<br />
		<label style="margin-top: 5px;">
			<b><bean:message key="label.tarefa" /></b>: TAREFAAAA
		</label>
		<br />
		<label style="margin-top: 5px;">
			<b><bean:message key="label.tarefa" /></b>: TAREFAAAA
		</label>
	</div>
	
	<div class="divAba">
		<a href="#" id="tabCamposAncora" accesskey="c" title="IE: Alt + c, Enter / Firefox: Alt + Shift + c" style="font-size: 11pt;">
			<bean:message key="aba.campos" />
		</a>
		<a href="#" id="tabHistoricoAncora" accesskey="h" title="IE: Alt + h, Enter / Firefox: Alt + Shift + h" style="font-size: 11pt;">
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