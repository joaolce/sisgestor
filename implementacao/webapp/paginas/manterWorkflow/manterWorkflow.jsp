<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<script type="text/javascript" src="dwr/interface/ManterWorkflowDWR.js"></script>
<script type="text/javascript" src="js/manterWorkflow/manterWorkflow.js"></script>

<div class="bordas" id="BordaExterna">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#gerenciarProcessos" titleKey="dica.processo.gerenciar" linkName="gerenciarProcessos" styleId="linkGerenciarProcessos" styleClass="btDesativado" roles="4">
			<html:img srcKey="imagem.processo" width="20" height="19" />
		</htmlSGR:link>
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
				<label style="float: left;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="100" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.descricao" />
					<br />
					<input type="text" name="descricaoPesquisa" id="descricaoPesquisa"  size="51" maxlength="200" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.ativo" />
					<br />
					<select name="ativoPesquisa" id="ativoPesquisa">
						<option value="">
							<bean:message key="label.todos" />
						</option>
						<option value="true">
							<bean:message key="label.sim" />
						</option>
						<option value="false">
							<bean:message key="label.nao" />
						</option>
					</select>
				</label>
			</div>
			<div style="padding-top: 14px; clear: both;">
				<html:submit styleClass="botaoOkCancelar">
					<bean:message key="botao.pesquisar" />
				</html:submit>
			</div>
		</html:form>
	</fieldset>

	<div id="divWorkflows" style="clear: left; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
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
		<fieldset style="padding: 10px; width: 50%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.workflow"/>
			</legend>
			<div style="float: left;">
				<div style="float: left;">
					<label style="float: left;">
						<b>
							<bean:message key="label.nome"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:text property="nome" size="51" maxlength="100" />
					</label> 
					<label style="float: left; margin-left: 10px;">
						<b>
							<bean:message key="label.ativo"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:select property="ativo" styleId="ativo">
							<html:option value="0" key="label.nao" />
							<html:option value="1" key="label.sim" />
						</html:select>
					</label> 
				</div>
				<br />
				<label style="float: left; margin-top: 3px;">
					<b style="vertical-align: top;">
						<bean:message key="label.descricao" />
						<span class="obrigatorio">*</span>
					</b>
					<html:textarea property="descricao" styleId="descricao" rows="4" cols="50" onkeypress="workflow.contaChar();" onkeydown="workflow.contaChar();" onkeyup="workflow.contaChar();" />
					<br />
					<bean:message key="label.maximoCaracteres" arg0="200"/>:
					<span id="contagem" style="color: red;"></span>
				</label> 
				<br />
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
	