<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<fieldset style="margin: 5pt auto; padding: 10px;">
	<div id="divMenuOpcoes">
		<html:link href="#iniciarTarefa" titleKey="dica.iniciarTarefa" linkName="iniciarTarefa" 
			styleClass="btDesativado" styleId="linkIniciarTarefa" >
			<html:img srcKey="imagem.iniciarTarefa" width="23" height="22"/>
		</html:link>
		<html:link href="#proximasTarefa" titleKey="dica.tarefa.proximas" linkName="proximasTarefa" 
			styleClass="btDesativado" styleId="linkProximasTarefa" style="margin-right: 10px;">
			<html:img srcKey="imagem.transicao" width="23" height="22"/>
		</html:link>
		<html:link href="#anotacao" titleKey="dica.anotacao" linkName="anotacao" 
			styleClass="btDesativado" styleId="linkAnotacao" style="margin-right: 10px;">
			<html:img srcKey="imagem.anotacao" width="23" height="22"/>
		</html:link>
		<html:link href="#anexos" titleKey="dica.anexos" onclick="usarWorkflow.popupVisualizarAnexos();" linkName="anexos">
			<html:img srcKey="imagem.anexos" width="23" height="22"/>
		</html:link>
	</div>
	
	<div class="bordas" style="width: 50%; padding: 5px; overflow: auto;">
		<div>
			<label style="white-space: nowrap;">
				<b><bean:message key="label.dataInicio" /></b>: 
				<span id="dataHoraInicioTarefa"></span>
			</label>
		</div>
		<div style="margin-top: 5px;">
			<label style="white-space: nowrap;">
				<b><bean:message key="label.tarefa" /></b>: 
				<span id="nomeTarefa"></span>
			</label>
		</div>
		<div style="margin-top: 5px;">
			<label style="white-space: nowrap;">
				<b><bean:message key="label.descricao" /></b>:
				<span id="descricaoTarefa"></span>
			</label>
		</div>
	</div>
	
	<div class="divAba" style="margin-top: 15px;">
		<a href="#" id="tabCamposAncora" accesskey="c" title="IE: Alt + c, Enter / Firefox: Alt + Shift + c" style="font-size: 11pt;">
			<bean:message key="aba.campos" />
		</a>
		<a href="#" id="tabHistoricoAncora" accesskey="h" title="IE: Alt + h, Enter / Firefox: Alt + Shift + h" style="font-size: 11pt;">
			<bean:message key="aba.historico" />
		</a>
	</div>
	
	<html:form action="/usarWorkflow.do" onsubmit="usarWorkflow.confirmar(); return false;" styleId="usoWorkflowForm">
		<html:hidden property="id" styleId="idUsoWorkflow" />
		<div class="bordas">
			<div id="tabCampos" style="margin: 5px; height: 330px; overflow: scroll;">
				<tiles:insert definition="includeTabCamposWorkflow"/>
			</div>
			<div id="tabHistorico" style="margin: 5px; height: 330px; overflow: scroll;">
				<tiles:insert definition="includeTabHistoricoWorkflow"/>
			</div>
		</div>
		
		<div style="clear: both; padding: 5px;" align="center">	
			<htmlSGR:submit titleKey="dica.salvar" styleClass="botaoOkCancelar" styleId="botaoSalvarUso" disabled="true" roles="5">
				<bean:message key="botao.salvar"/>
			</htmlSGR:submit>
			<html:button property="cancelar" titleKey="dica.voltar" onclick="usarWorkflow.salvarAntesSair();" styleClass="botaoOkCancelar">
				<bean:message key="botao.voltar"/>
			</html:button>
		</div>
	</html:form>
</fieldset>