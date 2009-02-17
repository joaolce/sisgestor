<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/manterAtividadeDWR.js"></script>
<script type="text/javascript" src="js/manterAtividade/manterAtividade.js"></script>

<html:form action="/manterAtividade.do?method=salvar" onsubmit="atividade.salvar(this); return false;" styleId="manterAtividadeForm">
	<fieldset style="margin: 5pt auto; padding: 10px;">
		<html:hidden property="processo" styleId="idProcesso"/>
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
				<bean:message key="label.descricao"/>
				<span class="obrigatorio">*</span>
			</b>
			<br />
			<html:textarea property="descricao" styleId="descricaoNovo" rows="4" cols="50" 
			onkeypress="atividade.contaChar(true);" 
			onkeydown="atividade.contaChar(true);" 
			onkeyup="atividade.contaChar(true);" />
			<br />
			<bean:message key="label.maximoCaracteres" arg0="200"/>:
			<span id="contagemNovo" style="color: red;"></span>
		</label>
		<br />
		<label>
			<b>
				<bean:message key="label.departamento.responsavel"/>
			</b>
			<html:select property="departamentoResponsavel" styleId="departamentoResponsavel">
				<html:option value="" />
				<html:optionsCollection name="manterAtividadeForm" property="listaDepartamentos" label="sigla" value="id" />
			</html:select>
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