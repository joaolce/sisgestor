<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<script type="text/javascript" src="dwr/interface/ManterTarefaDWR.js"></script>
<script type="text/javascript" src="js/manterTarefa/manterTarefa.js"></script>

<div class="bordas" id="BordaExterna" style="height: 496px;">
	<div id="divMenuOpcoes">
		<htmlSGR:link href="#novaTarefa" titleKey="dica.tarefa.nova" onclick="tarefa.popupNovaTarefa();" linkName="novaTarefa" roles="4">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</htmlSGR:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE TAREFAS -->
	<fieldset style="padding: 10px; width: 70%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterTarefa.do" onsubmit="tarefa.pesquisar(); return false;" styleId="manterTarefaForm">
			<div style="float: left;">
				<label style="float: left;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisaTarefa" id="nomePesquisaTarefa"  size="51" maxlength="100" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.descricao" />
					<br />
					<input type="text" name="descricaoPesquisaTarefa" id="descricaoPesquisaTarefa"  size="51" maxlength="200" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.usuario.responsavel" />
					<br />
					<html:select property="usuarioResponsavel" styleId="usuarioPesquisa">
						<html:option value="" key="label.todos" />
						<html:optionsCollection name="manterTarefaForm" property="listaUsuarios" label="nome" value="id" />
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

	<div id="divTarefas" style="clear: left; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.descricao" /></th>
					<th><bean:message key="label.usuario" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterTarefa" class="corpoTabelaClicavel"/>
		</table>
	</div>
	
	<!-- FIELDSET PARA DADOS DA TAREFA -->
	<html:form action="/manterTarefa.do?method=atualizar" onsubmit="tarefa.atualizar(this); return false;" 
			  styleId="formSalvarTarefa" style="display: none; margin-top: 10px;">
	    <html:hidden property="atividade" styleId="atividade"/>
		<html:hidden property="id"/>
		<fieldset style="padding: 10px; width: 50%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.tarefa"/>
			</legend>
			<div style="float: left;">
				<div style="float: left;">
					<label style="float: left;">
						<b>
							<bean:message key="label.nome"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:text property="nome" size="51" maxlength="100" styleId="nomeTarefa"/>
					</label> 
				</div>
				<br />
				<label style="float: left; margin-top: 3px;">
					<b style="vertical-align: top;">
						<bean:message key="label.descricao" />
						<span class="obrigatorio">*</span>
					</b>
					<html:textarea property="descricao" styleId="descricaoTarefa" rows="4" cols="50" 
					onkeypress="tarefa.contaChar(false);" 
					onkeydown="tarefa.contaChar(false);" 
					onkeyup="tarefa.contaChar(false);" />
					<br />
					<bean:message key="label.maximoCaracteres" arg0="200"/>:
					<span id="contagemTarefa" style="color: red;"></span>
				</label> 
				<br />
				<label>
					<b>
						<bean:message key="label.usuario.responsavel"/>
					</b>
					<html:select property="usuarioResponsavel" styleId="usuarioResponsavel">
						<html:option value="" />
						<html:optionsCollection name="manterTarefaForm" property="listaUsuarios" label="nome" value="id" />
					</html:select>
				</label>
			</div>
			<div style="clear: both; padding: 5px;" align="center" id="divBotoes">
				<htmlSGR:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar" roles="4">
					<bean:message key="botao.atualizar"/>
				</htmlSGR:submit>
				<htmlSGR:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="tarefa.excluir();" roles="4">
					<bean:message key="botao.excluir"/>
				</htmlSGR:button>
			</div>
		</fieldset>
	</html:form>
</div>
	