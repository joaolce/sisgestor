<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<div class="bordas" id="BordaExterna" style="height: 496px;">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#gerenciarAtividades" titleKey="dica.atividade.gerenciar" linkName="gerenciarAtividades" styleId="linkGerenciarAtividades" styleClass="btDesativado" roles="4">
			<html:img srcKey="imagem.atividade" width="20" height="19" />
		</htmlSGR:link>
		<htmlSGR:link href="#novoProcesso" titleKey="dica.processo.novo" onclick="processo.popupNovoProcesso();" linkName="novoProcesso" roles="4">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</htmlSGR:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE PROCESSOS -->
	<fieldset style="padding: 10px; width: 70%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterProcesso.do" onsubmit="processo.pesquisar(); return false;" styleId="manterProcessoForm">
			<div style="float: left;">
				<label style="float: left;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisaProcesso" id="nomePesquisaProcesso" size="51" maxlength="100" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.descricao" />
					<br />
					<input type="text" name="descricaoPesquisaProcesso" id="descricaoPesquisaProcesso" size="51" maxlength="200" />
				</label>
			</div>
			<div style="padding-top: 14px; clear: both;">
				<html:submit styleClass="botaoOkCancelar">
					<bean:message key="botao.pesquisar" />
				</html:submit>
			</div>
		</html:form>
	</fieldset>

	<div id="divProcessos" style="clear: left; height: 180px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th style="display: none;">&nbsp;</th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.descricao" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterProcesso" class="corpoTabelaClicavel"/>
		</table>
	</div>
	
	<!-- FIELDSET PARA DADOS DO PROCESSO -->
	<html:form action="/manterProcesso.do?method=atualizar" onsubmit="processo.atualizar(this); return false;" styleId="formAtualizarProcesso" style="display: none; margin-top: 10px;">
		<html:hidden property="workflow" styleId="workflowProcesso" />
		<html:hidden property="id" />
		<fieldset style="padding: 10px; width: 50%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.processo"/>
			</legend>
			<div style="float: left;">
				<div style="float: left;">
					<label style="float: left;">
						<b>
							<bean:message key="label.nome"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:text property="nome" styleId="nomeProcesso" size="51" maxlength="100" />
					</label> 
				</div>
				<br />
				<label style="float: left; margin-top: 3px;">
					<b style="vertical-align: top;">
						<bean:message key="label.descricao" />
						<span class="obrigatorio">*</span>
					</b>
					<html:textarea property="descricao" styleId="descricaoProcesso" rows="4" cols="50" onkeypress="processo.contaChar(false);" onkeydown="processo.contaChar(false);" onkeyup="processo.contaChar(false);" />
					<br />
					<bean:message key="label.maximoCaracteres" arg0="200"/>:
					<span id="contagemProcesso" style="color: red;"></span>
				</label> 
				<br />
			</div>
			<div style="clear: both; padding: 5px;" align="center" id="divBotoes">
				<htmlSGR:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar" roles="4">
					<bean:message key="botao.atualizar"/>
				</htmlSGR:submit>
				<htmlSGR:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="processo.excluir();" roles="4">
					<bean:message key="botao.excluir"/>
				</htmlSGR:button>
			</div>
		</fieldset>
	</html:form>
</div>
	