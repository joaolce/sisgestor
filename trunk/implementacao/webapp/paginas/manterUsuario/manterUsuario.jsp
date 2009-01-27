<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script type="text/javascript" src="dwr/interface/ManterUsuarioDWR.js"></script>
<script type="text/javascript" src="js/manterUsuario/manterUsuario.js"></script>

<div class="bordas" id="BordaExterna">

	<div id="divMenuOpcoes">
		<html:link href="#editarSenha" titleKey="dica.senha" onclick="usuario.popupEditarSenha();" linkName="editarSenha">
			<html:img srcKey="imagem.senha" width="20" height="19" />
		</html:link>
		<html:link href="#novoUsuario" titleKey="dica.usuario.novo" onclick="usuario.popupNovoUsuario();" linkName="novoUsuario">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</html:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE USUARIO -->
	<fieldset style="padding: 10px; width: 50%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterUsuario.do" onsubmit="usuario.pesquisar(); return false;" styleId="manterUsuarioForm">
			<div style="float: left; width: 80%;">
				<div style="float: left; width: 100%;">
					<label style="float: left;">
						<bean:message key="label.login" />
						<br />
						<input type="text" name="loginPesquisa" id="loginPesquisa" size="16" maxlength="15" />
					</label>
					<label style="float: right;">
						<bean:message key="label.nome" />
						<br />
						<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="150" />
					</label>
				</div>
				<div style="float: left;">
					<label style="float: left;">
						<br />
						<bean:message key="label.departamento" />
						<br />
						<html:select property="departamento" styleId="departamentoPesquisa">
							<html:option value="">
								<bean:message key="label.todos" />
							</html:option>
							<html:optionsCollection name="manterUsuarioForm" property="listaDepartamentos" label="sigla" value="id" />
						</html:select>
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
	
	<!-- FIELDSET PARA DADOS DO USUARIO -->
	<html:form action="/manterUsuario.do?method=atualizar" onsubmit="usuario.atualizar(this); return false;" styleId="formSalvar" style="display: none; margin-top: 10px;">
		<html:hidden property="id"/>
		<fieldset style="padding: 5px; padding-right: 0px; width: 956px; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.usuario"/>
			</legend>
			<div style="float: left;">
				<label style="float: left;">
					<b><bean:message key="label.login"/>:</b>
					<html:text property="login" size="16" maxlength="15" />
				</label>
				<label style="margin-left: 10px;">
					<b><bean:message key="label.departamento"/>: </b>
					<html:select property="departamento" styleId="departamento">
						<html:option value="" />
						<html:optionsCollection name="manterUsuarioForm" property="listaDepartamentos" label="sigla" value="id" />
					</html:select>
				</label>
				<br />
				<label>
					<b><bean:message key="label.nome"/>:</b>
					<html:text property="nome" size="51" maxlength="150" />
				</label> 
				<br />
				<label>
					<b><bean:message key="label.email"/>: </b>
					<html:text property="email" size="41" maxlength="40" />
				</label> 
			</div>
			<div id="tabPermissoes" style="float: right; width: 60%;">
				<div style="float: left; margin-right: 5px;">
					<label class="labelComboTransferencia">
						<span id="itensPermissoesSelecionadas">
							<b><bean:message key="label.permissoesSelecionadas"/></b>
						</span>
						<br/>
						<html:select multiple="true"  altKey="dica.desselecionar" titleKey="dica.desselecionar"  
			 				property="permissoes"  size="7" style="width: 220px;" styleId="permissoes"
			 				ondblclick="usuario.transferePermissao('permissoes', 'permissoesInform');" >
			 				<logic:present name="manterUsuarioForm" property="roles">
			 					<html:optionsCollection name="manterUsuarioForm" property="roles" label="descricao" value="id" />
			 				</logic:present>
						</html:select>
					</label>
				</div>
				<div style="float: left; margin: 20px 3px 0 0; ">
					<br />
					<br />
					<html:button property="direita" titleKey="dica.selecionarVarios" style="width: 30px;" 
						onclick="usuario.transfereTodasPermissoes('permissoesInform', 'permissoes');">
						<bean:message key="label.direita"/>
					</html:button>
					<br />
					<br />
					<html:button property="esquerda" titleKey="dica.desselecionaVarios" style="width: 30px;" 
						onclick="usuario.transfereTodasPermissoes('permissoes', 'permissoesInform');">
						<bean:message key="label.esquerda"/>
					</html:button>
				</div>
				<div>
					<label class="labelComboTransferencia">
						<span id="itensPermissoes">
							<b><bean:message key="label.permissoesDisponiveis"/></b>
						</span>
						<br />
						<html:select multiple="true" altKey="dica.selecionar" titleKey="dica.selecionar" 
							property="permissoesInform" value="id" size="7" style="width: 220px;" styleId="permissoesInform" 
							ondblclick="usuario.transferePermissao('permissoesInform', 'permissoes');">
							<logic:present name="manterUsuarioForm" property="permissoesDisponiveis">
								<html:optionsCollection name="manterUsuarioForm" property="permissoesDisponiveis" label="descricao" value="id" />
							</logic:present>
						</html:select>
					</label>
				</div>
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
	