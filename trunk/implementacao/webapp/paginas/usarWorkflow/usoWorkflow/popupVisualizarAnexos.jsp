<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<html:form styleId="formIncluirAnexo" action="/usarWorkflow.do?method=incluirAnexo" onsubmit="usarWorkflow.incluirAnexo(this); return false;" method="POST" enctype="multipart/form-data">
	<html:hidden property="id" styleId="idUsoWorkflow" />
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
					<th width="2%" >&nbsp;</th>
					<th width="30%"><bean:message key="label.dataCriacao" /></th>
					<th><bean:message key="label.nomeArquivo" /></th>
				</tr>
			</thead>
			<tbody id="corpoAnexos" class="corpoTabelaClicavel"/>
		</table>
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