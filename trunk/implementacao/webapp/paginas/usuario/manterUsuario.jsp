<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/ManterUsuarioDWR.js"></script>
<script type="text/javascript" src="js/manterUsuario/manterUsuario.js"></script>

<div class="bordas" id="BordaExterna">

	<div id="divMenuOpcoes">
		<html:link href="#" titleKey="dica.novoUsuario" onclick="usuario.popupNovoUsuario();">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</html:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE USUARIO -->
	<fieldset style="padding: 10px; width: 50%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterUsuario.do" onsubmit="usuario.pesquisar(); return false;" styleId="manterUsuarioForm">
			<div style="float: left; width: 350px;">
				<div style="float: left;">
					<label>
						<bean:message key="label.login" />
						<br />
						<input type="text" name="loginPesquisa" id="loginPesquisa" size="16" maxlength="15" />
					</label>
				</div>
				<div style="float: right;">
					<label>
						<bean:message key="label.nome" />
						<br />
						<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="150" />
					</label>
				</div>
				<div style="float: right;">
					<label>
						<bean:message key="label.departamento" />
						<br />
					</label>
				</div>
			</div>
			<div style="padding-top: 14px; clear: both;">
				<html:submit styleClass="botaoOkCancelar">
					<bean:message key="label.pesquisar" />
				</html:submit>
			</div>
		</html:form>
	</fieldset>

	<div id="divUsuarios" style="clear: left; width: 956px; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 98%">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th><bean:message key="label.login" /></th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.email" /></th>
					<th><bean:message key="label.departamento" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterUsuario" class="corpoTabelaClicavel"></tbody>
		</table>
	</div>
	
	<html:form action="/manterUsuario.do?method=atualizar" onsubmit="usuario.atualizar(this); return false;" styleId="formSalvar" style="display: none; margin-top: 10px;">
		<!-- FIELDSET PARA DADOS DO USUARIO -->
		<html:hidden property="id"/>
		<fieldset style="padding: 15px; width: 50%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.usuario"/>
			</legend>
			<div>
				<label>
					<b><bean:message key="label.login"/>:</b>
					<html:text property="login" size="11" maxlength="10" />
				</label> <br />
				<label>
					<b><bean:message key="label.nome"/>:</b>
					<html:text property="nome" size="51" maxlength="50" />
				</label> <br />
				<label>
					<b><bean:message key="label.email"/>: </b>
					<html:text property="email" size="41" maxlength="40" />
				</label> <br />
				<label>
					<b><bean:message key="label.departamento"/>: </b>
					
					
					
					
				</label>
			</div>
			<div style="clear: both; padding: 5px;" align="center">
				<html:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar">
					<bean:message key="label.atualizar"/>
				</html:submit>
				<html:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="usuario.excluir();">
					<bean:message key="label.excluir"/>
				</html:button>
			</div>
		</fieldset>
	</html:form>
</div>
	