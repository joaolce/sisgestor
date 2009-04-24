<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<html:form styleId="formAnexos" action="/anexoUsoWorkflow.do?method=excluirAnexo">
	<html:hidden property="usoWorkflow" styleId="idUsoWorkflowAnexo" />
	
	<div id="divMenuOpcoes" style="height: 22px;">
		<logic:present name="TarefaIniciada">
			<htmlSGR:link href="#incluirAnexo" titleKey="dica.anexo.incluir" onclick="anexoUsoWorkflow.telaInserirAnexo();" linkName="incluirAnexo" roles="6">
				<html:img srcKey="imagem.incluirAnexo" width="23" height="22" />
			</htmlSGR:link>
		</logic:present>
	</div>
	<div id="divAnexos" align="center" style="clear: both; height: 160px; overflow: auto; border: 1px solid gray;">
		<logic:notEmpty name="anexoUsoWorkflowForm" property="anexos">
			<table style="width: 99.9%">
				<thead>
					<tr>
						<th width="2%" >
							<input type="checkbox" onclick="CheckboxFunctions.marcar(this.checked, 'anexosSelecionados');" />
						</th>
						<th width="30%"><bean:message key="label.dataCriacao" /></th>
						<th><bean:message key="label.nomeArquivo" /></th>
					</tr>
				</thead>
				<tbody id="corpoAnexos" class="corpoTabelaClicavel">
					<logic:iterate id="anexo" name="anexoUsoWorkflowForm" property="anexos">
						<tr>
							<td>
								<html:multibox property="anexosSelecionados">
									<bean:write name="anexo" property="id"/>
								</html:multibox>
							</td>
							<td>
								<bean:write name="anexo" property="dataCriacao" format="dd/MM/yyyy HH:mm"/>
							</td>
							<td>
								<html:link href="anexoUsoWorkflow.do?method=download" paramName="anexo" paramProperty="id" paramId="id" titleKey="dica.download" target="_blank">
									<bean:write name="anexo" property="nome"/>
								</html:link>
							</td>
						</tr>
					</logic:iterate>
				</tbody>
			</table>
		</logic:notEmpty>
		<logic:empty name="anexoUsoWorkflowForm" property="anexos">
			<span class="mensagemErro">
				<bean:message key="mensagem.semAnexo" />
			</span>
		</logic:empty>
	</div>
	
	<div style="margin-top: 10px; margin-right: 1px;" align="center">
		<logic:present name="TarefaIniciada">
			<htmlSGR:button property="excluir" style="width: 110px;" onclick="anexoUsoWorkflow.excluirAnexos();" titleKey="dica.excluir" roles="6">
					<bean:message key="botao.excluir"/>
			</htmlSGR:button>
		</logic:present>
		<html:button property="cancelar" titleKey="dica.voltar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.voltar"/>
		</html:button>
	</div>
</html:form>