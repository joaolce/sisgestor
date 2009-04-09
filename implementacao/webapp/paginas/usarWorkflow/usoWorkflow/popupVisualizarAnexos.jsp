<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<html:form styleId="formAnexos" action="/usarWorkflow.do?method=incluirAnexo" method="POST" enctype="multipart/form-data">
	<html:hidden property="usoWorkflow" styleId="idUsoWorkflowAnexo" />
	<div id="divInserirArquivoAnexo" style="padding: 20px;">
		<label>
			<bean:message key="label.enderecoArquivo"/>
			<html:file property="arquivo" style="width: 400px;" />
		</label>
		<br />
		<htmlSGR:submit titleKey="dica.incluir" styleClass="botaoOkCancelar" roles="6">
			<bean:message key="botao.incluir"/>
		</htmlSGR:submit>
	</div>
	
	<div id="divAnexos" style="clear: left; height: 160px; overflow: auto; border: 1px solid gray; margin-top: 5px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th style="display: none;">&nbsp;</th>
					<th width="2%" >
						<input type="checkbox" onclick="CheckboxFunctions.marcar(this.checked, 'anexosSelecionados');" />
					</th>
					<th width="30%"><bean:message key="label.dataCriacao" /></th>
					<th><bean:message key="label.nomeArquivo" /></th>
				</tr>
			</thead>
			<tbody id="corpoAnexos" class="corpoTabelaClicavel"/>
		</table>
	</div>
	
	<div style="margin-top: 10px; margin-right: 1px;" align="center">
		<htmlSGR:button property="excluir" style="width: 110px;" onclick="usarWorkflow.excluirAnexo(); return false;" titleKey="dica.excluir" roles="6">
				<bean:message key="botao.excluir"/>
		</htmlSGR:button>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>