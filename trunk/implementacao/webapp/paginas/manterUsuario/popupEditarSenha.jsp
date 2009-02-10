<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/manterUsuario.do?method=atualizarSenha"  onsubmit="usuario.atualizarSenha(this); return false;" styleId="manterUsuarioForm">
	<fieldset style="margin: 5pt auto; padding: 10px; width: 90%;">
		<div>
			<label>
				<b>
					<bean:message key="label.senha.atual"/>
					<span class="obrigatorio">*</span>
				</b>
				<html:password property="senhaAtual" maxlength="20" size="21"/>
			</label>
			<br />	
			<label>
				<b>
					<bean:message key="label.senha.nova"/>
					<span class="obrigatorio">*</span>
				</b>
				<html:password property="novaSenha" maxlength="20" size="21"/>
			</label>
			<br />
			<label>
				<b>
					<bean:message key="label.senha.confirmar"/>
					<span class="obrigatorio">*</span>
				</b>
				<html:password property="confirmarSenha" maxlength="20" size="21"/>
			</label>
			
		</div>
	</fieldset>
	<div style="clear: both; padding: 5px;" align="center">	
		<html:submit titleKey="dica.salvar" styleClass="botaoOkCancelar">
			<bean:message key="botao.ok"/>
		</html:submit>
		<html:button property="cancelar" titleKey="dica.cancelar" onclick="JanelaFactory.fecharJanelaAtiva();" styleClass="botaoOkCancelar">
			<bean:message key="botao.cancelar"/>
		</html:button>
	</div>
</html:form>