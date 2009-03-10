<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/manterCampo.do?method=salvar" onsubmit="campo.salvar(this); return false;" styleId="manterCampoForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<html:hidden property="workflow" styleId="workflowNovoCampo" />
		<div style="float: left;">
			<label style="float: left; margin-top: 15px;">
				<b>
					<bean:message key="label.nome"/>
					<span class="obrigatorio">*</span>
				</b>
				<br />
				<html:text property="nome" styleId="nomeNovoCampo" size="51" maxlength="100" />
			</label>
			<label style="float: left; margin-left: 20px; margin-top: 15px;">
				<b>
					<bean:message key="label.tipo" />
					<span class="obrigatorio">*</span>
				</b>
				<br />
				<html:select property="tipo" styleId="tipoNovoCampo" onchange="campo.gerenciarPreDefinidos(true);">
					<html:option value="" />
					<html:optionsCollection name="manterCampoForm" property="tipos" label="descricao" value="id" />
				</html:select>
			</label>
		</div>
		<div id="divOpcoesNovoCampo" style="float:left; display: none; width: 380px; margin-top: 10px; padding: 10px;" class="bordas">
			<div style="float: left; margin-left: 10px;">
				<label>
					<b><bean:message key="label.opcao" /></b>
					<br />
					<input type="text" name="opcao" id="opcaoNovoCampo" size="21" maxlength="20" />
				</label>
				<br /> <br />
				<html:button property="adicionarOpcao" titleKey="dica.campo.adicionarOpcao" onclick="campo.adicionaOpcao(true);" styleClass="botaoOkCancelar">
					<bean:message key="botao.adicionar"/>
				</html:button>
				<br /> <br />
				<html:button property="removerOpcao" titleKey="dica.campo.removerOpcao" onclick="campo.removeOpcao(true);" styleClass="botaoOkCancelar">
					<bean:message key="botao.remover"/>
				</html:button>
			</div>
			<div style="float: left; margin-left: 30px;">
				<label>
					<b>
						<bean:message key="label.opcoes" />
						<span class="obrigatorio">*</span>
					</b>
					<br />
					<select name="opcoes" id="opcoesNovoCampo" size="8" style="width: 165px;">
					</select>
				</label>
			</div>
		</div>
		<div style="float: left;">
			<label style="float: left; margin-top: 15px;">
				<b>
					<bean:message key="label.descricao"/>
				</b>
				<br />
				<html:textarea property="descricao" styleId="descricaoNovoCampo" rows="4" cols="50" onkeypress="campo.contaChar(true);" onkeydown="campo.contaChar(true);" onkeyup="campo.contaChar(true);" />
				<br />
				<bean:message key="label.maximoCaracteres" arg0="200"/>:
				<span id="contagemNovoCampo" style="color: red;"></span>
			</label>
			<label style="float: left; margin-left: 10px; margin-top: 15px;">
				<b>
					<bean:message key="label.obrigatorio"/>
					<span class="obrigatorio">*</span>
				</b>
				<br />
				<html:select property="obrigatorio" styleId="obrigatorioNovoCampo">
					<html:option value="0" key="label.nao" />
					<html:option value="1" key="label.sim" />
				</html:select>
			</label>
		</div>
	</fieldset>
	<div style="clear: both; padding: 5px;" align="center">	
		<html:submit titleKey="dica.salvar" styleClass="botaoOkCancelar">
			<bean:message key="botao.salvar"/>
		</html:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>