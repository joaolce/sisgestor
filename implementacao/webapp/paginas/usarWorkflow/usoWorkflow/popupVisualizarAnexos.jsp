<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/usarWorkflow.do?method=incluirAnexos" styleId="visualizarAnexos">
	<div id="divInserirArquivoAnexo" style="padding: 20px;">
		<label>
			<bean:message key="label.enderecoArquivo"/>
			<html:file property="enderecoArquivo" style="width:400px;" />
		</label>
		<br />
		<html:submit property="anexar" styleId="anexar" titleKey="dica.incluir" onclick="usarWorflow.incluirAnexo(); return false;" styleClass="botaoOkCancelar">
			<bean:message key="botao.incluir"/>
		</html:submit>
	</div>
	
	<div id="divAnexos" style="clear: left; height: 160px; overflow: auto; border: 1px solid gray; margin-top: 5px;">
	<logic:notEmpty name="usarWorkflowForm" property="listaAnexos">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th width = "2%">
						<html:multibox property="anexosSelecionados" onclick="usarWorflow.marcarDesmarcarTodos();">
							<bean:write name="usoWorkflow" property="id"/>
						</html:multibox></th>
					<th width="30%"><bean:message key="label.dataCriacao" /></th>
					<th><bean:message key="label.nomeArquivo" /></th>
				</tr>
			</thead>
			<tbody id="corpoAnexos" class="corpoTabelaClicavel">
				<logic:iterate id="anexo" name="usarWorkflowForm" property="listaAnexos">
					<tr>
						<td>
							<html:multibox property="anexosSelecionados">
								<bean:write name="anexo" property="id"/>
							</html:multibox>
						</td>
						<td><bean:write name="anexo" property="dataCriacao" format="dd/MM/yyyy HH:mm"/></td>
						<td>
							<html:link href="usarWorkflow.do?method=download" paramName="anexo" paramProperty="id" paramId="idAnexo" titleKey="dica.download" target="_blank">
								<bean:write name="anexo" property="nome"/>
							</html:link>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</table>
		</logic:notEmpty>
		<logic:empty name="usarWorkflowForm" property="listaAnexos">
			<span class="mensagemEmVermelho"><bean:message key="mensagem.semAnexo"/></span>
		</logic:empty>
	</div>
	
	<div style="margin-top: 10px; margin-right: 1px;">
		<html:button property="excluir" style="width: 110px;" onclick="usarWorflow.excluirAnexo(); return false;" titleKey="dica.excluir">
				<bean:message key="botao.excluir"/>
		</html:button>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>