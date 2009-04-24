<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<div class="bordas" id="BordaExterna" style="height: 496px;">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#novoCampo" titleKey="dica.campo.novo" onclick="campo.popupNovoCampo();" linkName="novoCampo" roles="4">
			<html:img srcKey="imagem.novo" width="23" height="22" />
		</htmlSGR:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE CAMPO -->
	<fieldset style="float:left; padding: 10px; width: 70%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterCampo.do" onsubmit="campo.pesquisar(); return false;" styleId="manterCampoForm">
			<div style="float: left;">
				<label style="float: left;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisaCampo" id="nomePesquisaCampo"  size="51" maxlength="100" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.tipo" />
					<br />
					<html:select property="tipo" styleId="tipoPesquisaCampo">
						<html:option value="" key="label.todos" />
						<html:optionsCollection name="manterCampoForm" property="tipos" label="descricao" value="id" />
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

	<div id="divCampos" style="clear: left; height: 180px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th style="display: none;">&nbsp;</th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.obrigatorio" /></th>
					<th><bean:message key="label.tipo" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterCampo" class="corpoTabelaClicavel"/>
		</table>
	</div>
	
	<!-- FIELDSET PARA DADOS DO CAMPO -->
	<html:form action="/manterCampo.do?method=atualizar" onsubmit="campo.atualizar(this); return false;" styleId="formAtualizarCampo" style="display: none; margin-top: 10px;">
		<html:hidden property="workflow" styleId="workflowCampo" />
		<html:hidden property="id" styleId="idCampo" />
		<fieldset id="fieldsetCampo" style="float:left; padding: 10px; width: 70%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.campo"/>
			</legend>
			<div style="float: left;">
				<div style="float: left;">
					<label style="float: left;">
						<b>
							<bean:message key="label.nome"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:text property="nome" styleId="nomeCampo" size="51" maxlength="100" />
					</label>
					<label style="float: left; margin-left: 10px;">
						<b>
							<bean:message key="label.tipo" />
							<span class="obrigatorio">*</span>
						</b>
						<html:select property="tipo" styleId="tipoCampo" onchange="campo.gerenciarPreDefinidos(false);">
							<html:option value="" />
							<html:optionsCollection name="manterCampoForm" property="tipos" label="descricao" value="id" />
						</html:select>
					</label>
					<label style="float: left; margin-left: 10px;">
						<b>
							<bean:message key="label.obrigatorio"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:select property="obrigatorio" styleId="obrigatorioCampo">
							<html:option value="0" key="label.nao" />
							<html:option value="1" key="label.sim" />
						</html:select>
					</label> 
				</div>
				<div style="float: left; margin-top: 10px;">
					<label style="float: left;">
						<b style="vertical-align: top;">
							<bean:message key="label.descricao"/>
						</b>
						<html:textarea property="descricao" styleId="descricaoCampo" rows="4" cols="50" onkeypress="campo.contaChar(false);" onkeydown="campo.contaChar(false);" onkeyup="campo.contaChar(false);" />
						<br />
						<bean:message key="label.maximoCaracteres" arg0="200"/>:
						<span id="contagemCampo" style="color: red;"></span>
					</label>
				</div>
				<div id="divOpcoesCampo" style="float:left; display: none; margin-left: 10px; margin-top: 10px; padding: 7px;" class="bordas">
					<div style="float: left;">
						<label>
							<b><bean:message key="label.opcao" /></b>
							<input type="text" name="opcao" id="opcaoCampo" size="21" maxlength="20" />
						</label>
						<br /> <br />
						<div align="center">
							<html:button property="adicionarOpcao" titleKey="dica.campo.adicionarOpcao" onclick="campo.adicionaOpcao(false);" styleClass="botaoOkCancelar">
								<bean:message key="botao.adicionar"/>
							</html:button>
							<html:button property="removerOpcao" titleKey="dica.campo.removerOpcao" onclick="campo.removeOpcao(false);" styleClass="botaoOkCancelar">
								<bean:message key="botao.remover"/>
							</html:button>
						</div>
					</div>
					<div style="float: left; margin-left: -25px;">
						<label>
							<b style="vertical-align: top;">
								<bean:message key="label.opcoes" />
								<span class="obrigatorio">*</span>
							</b>
							<select name="opcoes" id="opcoesCampo" size="6" style="width: 165px;">
							</select>
						</label>
					</div>
				</div>
			</div>
			<div style="clear: both; padding: 5px;" align="center" id="divBotoes">
				<htmlSGR:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar" roles="4">
					<bean:message key="botao.atualizar"/>
				</htmlSGR:submit>
				<htmlSGR:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="campo.excluir();" roles="4">
					<bean:message key="botao.excluir"/>
				</htmlSGR:button>
			</div>
		</fieldset>
	</html:form>
</div>
	