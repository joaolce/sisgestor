<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<script type="text/javascript" src="dwr/interface/ManterWorkflowDWR.js"></script>
<script type="text/javascript" src="js/manterWorkflow/manterWorkflow.js"></script>

<div class="bordas" id="BordaExterna">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#novoWorkflow" titleKey="dica.workflow.novo" onclick="workflow.popupNovoWorkflow();" linkName="novoWorkflow" roles="4">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</htmlSGR:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE Workflow -->
	<fieldset style="padding: 10px; width: 70%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterWorkflow.do" onsubmit="workflow.pesquisar(); return false;" styleId="manterWorkflowForm">
			<div style="float: left;">
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="150" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.descricao" />
					<br />
					<input type="text" name="descricaoPesquisa" id="descricaoPesquisa"  size="51" maxlength="150" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.ativo" />
					<br />
					<html:select property="ativo" styleId="ativoPesquisa">
						<html:option value=""  key="label.todos" />
						<html:option value="1" key="label.sim" />
						<html:option value="0" key="label.nao" />
					</html:select>
				</label>
			</div>
			<div style="padding-top: 14px; clear: both;">
				<html:submit styleClass="botaoOkCancelar">
					<bean:message key="botao.pesquisar" />
				</html:submit>
			</div>
		</html:form>
	</fieldset>

	<div id="divWorkflows" style="clear: left; width: 956px; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.descricao" /></th>
					<th><bean:message key="label.ativo" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterWorkflow" class="corpoTabelaClicavel"/>
		</table>
	</div>
	
	<!-- FIELDSET PARA DADOS DO WORKFLOW -->
	<html:form action="/manterWorkflow.do?method=atualizar" onsubmit="workflow.atualizar(this); return false;" styleId="formSalvar" style="display: none; margin-top: 10px;">
		<html:hidden property="id"/>
		<fieldset style="padding: 5px; padding-right: 0px; width: 50%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.workflow"/>
			</legend>
			<div style="float: left; margin-top: 14px;">
				<label style="float: left; margin-top: 3px;">
					<b><bean:message key="label.nome"/>:</b>
					<html:text property="nome" size="51" maxlength="150" />
				</label> 
				<br />
				<label style="float: left; margin-top: 3px;">
					<b><bean:message key="label.descricao"/>:</b>
					<html:text property="descricao" size="51" maxlength="150" />
				</label> 
				<br />
				<label style="float: left; margin-top: 3px;">
					<b><bean:message key="label.ativo"/></b>
					<html:select property="ativo" styleId="ativo">
						<html:option value="0" key="label.nao" />
						<html:option value="1" key="label.sim" />
					</html:select>
				</label> 
			</div>
			<div style="clear: both; padding: 5px;" align="center" id="divBotoes">
				<htmlSGR:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar" roles="4">
					<bean:message key="botao.atualizar"/>
				</htmlSGR:submit>
				<htmlSGR:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="workflow.excluir();" roles="4">
					<bean:message key="botao.excluir"/>
				</htmlSGR:button>
			</div>
		</fieldset>
	</html:form>
</div>
	