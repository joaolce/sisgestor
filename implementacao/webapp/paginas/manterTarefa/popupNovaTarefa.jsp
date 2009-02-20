<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/manterTarefa.do?method=salvar" onsubmit="tarefa.salvar(this); return false;" styleId="manterTarefaForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
	<html:hidden property="atividade" styleId="atividadeNovaTarefa" />
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.nome"/>
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:text property="nome" size="51" maxlength="100" />
		</label> 
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.usuario.responsavel"/>
			</b>
			<html:select property="usuario" styleId="usuario" style="width: 150px;">
				<html:option value="" />
				<html:optionsCollection name="manterTarefaForm" property="listaUsuarios" label="nome" value="id" />
			</html:select>
		</label>
		<label style="float: left; margin-top: 15px;">
			<b>
				<bean:message key="label.descricao"/>
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:textarea property="descricao" styleId="descricaoNovo" rows="4" cols="50" 
			onkeypress="tarefa.contaChar(true);" 
			onkeydown="tarefa.contaChar(true);" 
			onkeyup="tarefa.contaChar(true);" />
			<br />
			<bean:message key="label.maximoCaracteres" arg0="200"/>:
			<span id="contagemNovo" style="color: red;"></span>
		</label>
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