<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<div class="bordas" id="BordaExterna" style="height: 476px;">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#gerenciarTarefas" titleKey="dica.tarefa.gerenciar" linkName="gerenciarTarefas" styleId="linkGerenciarTarefas" styleClass="btDesativado" roles="4">
			<html:img srcKey="imagem.tarefa" width="20" height="19" />
		</htmlSGR:link>
		<htmlSGR:link href="#definirFluxoAtividade" titleKey="dica.definirFluxo" onclick="atividade.popupDefinirFluxoAtividades();" linkName="definirFluxoAtividade" styleId="linkDefinirFluxoAtividade" roles="4">
			<html:img srcKey="imagem.fluxo" width="20" height="19" />
		</htmlSGR:link>
		<htmlSGR:link href="#novaAtividade" titleKey="dica.atividade.nova" onclick="atividade.popupNovaAtividade();" linkName="novaAtividade" roles="4">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</htmlSGR:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE ATIVIDADES -->
	<fieldset style="padding: 10px; width: 80%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterAtividade.do" onsubmit="atividade.pesquisar(); return false;" styleId="manterAtividadeForm">
			<div style="float: left;">
				<label style="float: left;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisaAtividade" id="nomePesquisaAtividade"  size="51" maxlength="100" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.descricao" />
					<br />
					<input type="text" name="descricaoPesquisaAtividade" id="descricaoPesquisaAtividade"  size="51" maxlength="200" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.departamento.responsavel" />
					<br />
					<html:select property="departamento" styleId="departamentoPesquisa">
						<html:option value="" key="label.todos" />
						<html:optionsCollection name="manterAtividadeForm" property="listaDepartamentos" label="sigla" value="id" />
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

	<div id="divAtividades" style="clear: left; height: 180px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th style="display: none;">&nbsp;</th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.descricao" /></th>
					<th><bean:message key="label.departamento" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterAtividade" class="corpoTabelaClicavel"/>
		</table>
	</div>
	
	<!-- FIELDSET PARA DADOS DA ATIVIDADE -->
	<html:form action="/manterAtividade.do?method=atualizar" onsubmit="atividade.atualizar(this); return false;" styleId="formAtualizarAtividade" style="display: none; margin-top: 10px;">
		<html:hidden property="processo" styleId="processoAtividade"/>
		<html:hidden property="id"/>
		<fieldset style="padding: 10px; width: 70%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.atividade"/>
			</legend>
			<div style="float: left;">
				<div style="float: left;">
					<label style="float: left;">
						<b>
							<bean:message key="label.nome"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:text property="nome" styleId="nomeAtividade" size="51" maxlength="100" />
					</label> 
				</div>
				<label style="margin-left: 40px;">
					<b>
						<bean:message key="label.departamento.responsavel"/>
						<span class="obrigatorio">*</span>
					</b>
					<html:select property="departamento" styleId="departamentoAtividade">
						<html:option value="" />
						<html:optionsCollection name="manterAtividadeForm" property="listaDepartamentos" label="sigla" value="id" />
					</html:select>
				</label>
				<label style="float: left; margin-top: 3px;">
					<b style="vertical-align: top;">
						<bean:message key="label.descricao" />
						<span class="obrigatorio">*</span>
					</b>
					<html:textarea property="descricao" styleId="descricaoAtividade" rows="4" cols="50" 
					onkeypress="atividade.contaChar(false);" 
					onkeydown="atividade.contaChar(false);" 
					onkeyup="atividade.contaChar(false);" />
					<br />
					<bean:message key="label.maximoCaracteres" arg0="200"/>:
					<span id="contagemAtividade" style="color: red;"></span>
				</label> 
			</div>
			<div style="clear: both; padding: 5px;" align="center" id="divBotoes">
				<htmlSGR:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar" roles="4">
					<bean:message key="botao.atualizar"/>
				</htmlSGR:submit>
				<htmlSGR:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="atividade.excluir();" roles="4">
					<bean:message key="botao.excluir"/>
				</htmlSGR:button>
			</div>
		</fieldset>
	</html:form>
</div>
	