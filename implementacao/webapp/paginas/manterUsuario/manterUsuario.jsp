<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ucb.br/sisgestor/taglib" prefix="htmlSGR" %>

<script type="text/javascript" src="dwr/interface/ManterUsuarioDWR.js"></script>
<script type="text/javascript" src="js/manterUsuario/manterUsuario.js"></script>

<div class="bordas" id="BordaExterna">
	<div id="divMenuOpcoes">
		<html:link href="#editarSenha" titleKey="dica.senha" onclick="usuario.popupEditarSenha();" linkName="editarSenha">
			<html:img srcKey="imagem.senha" width="20" height="19" />
		</html:link>
		<htmlSGR:link href="#novoUsuario" titleKey="dica.usuario.novo" onclick="usuario.popupNovoUsuario();" linkName="novoUsuario" roles="3">
			<html:img srcKey="imagem.novo" width="20" height="19" />
		</htmlSGR:link>
	</div>
	<!-- FIELDSET PARA PESQUISA DE USUARIO -->
	<fieldset style="padding: 10px; width: 60%; margin: 5 auto;">
		<legend>
			<bean:message key="label.criterioPesquisa" />
		</legend>
		<html:form action="/manterUsuario.do" onsubmit="usuario.pesquisar(); return false;" styleId="manterUsuarioForm">
			<div style="float: left;">
				<label style="float: left;">
					<bean:message key="label.login" />
					<br />
					<input type="text" name="loginPesquisa" id="loginPesquisa" size="16" maxlength="15" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.nome" />
					<br />
					<input type="text" name="nomePesquisa" id="nomePesquisa"  size="51" maxlength="150" />
				</label>
				<label style="float: left; margin-left: 20px;">
					<bean:message key="label.departamento" />
					<br />
					<html:select property="departamento" styleId="departamentoPesquisa">
						<html:option value="" key="label.todos" />
						<html:optionsCollection name="manterUsuarioForm" property="listaDepartamentos" label="sigla" value="id" />
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

	<div id="divUsuarios" style="clear: left; height: 240px; overflow: auto; border: 1px solid gray; margin-top: 10px;">
		<table style="width: 99.9%">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th><bean:message key="label.login" /></th>
					<th><bean:message key="label.nome" /></th>
					<th><bean:message key="label.email" /></th>
					<th><bean:message key="label.departamento" /></th>
					<th><bean:message key="label.chefe" /></th>
				</tr>
			</thead>
			<tbody id="corpoManterUsuario" class="corpoTabelaClicavel"></tbody>
		</table>
	</div>
	
	<!-- FIELDSET PARA DADOS DO USUARIO -->
	<html:form action="/manterUsuario.do?method=atualizar" onsubmit="usuario.atualizar(this); return false;" styleId="formSalvar" style="display: none; margin-top: 10px;">
		<html:hidden property="id"/>
		<fieldset style="padding: 10px; width: 90%; margin: 5 auto;">
			<legend>
				<bean:message key="label.dados.usuario"/>
			</legend>
			<div style="float: left;">
				<label style="float: left;">
					<b>
						<bean:message key="label.login"/>
						<span class="obrigatorio">*</span>
					</b>
					<html:text property="login" size="16" maxlength="15" />
				</label>
				<br />
				<label style="float: left; margin-top: 3px;">
					<b>
						<bean:message key="label.nome"/>
						<span class="obrigatorio">*</span>
					</b>
					<html:text property="nome" size="51" maxlength="100" />
				</label> 
				<br />
				<div style="float: left; margin-top: 3px;">
					<label>
						<b>
							<bean:message key="label.departamento"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:select property="departamento" styleId="departamento">
							<html:option value="" />
							<html:optionsCollection name="manterUsuarioForm" property="listaDepartamentos" label="sigla" value="id" />
						</html:select>
					</label>
					<label style="margin-left: 7px;">
						<b>
							<bean:message key="label.chefe"/>
							<span class="obrigatorio">*</span>
						</b>
						<html:select property="chefe" styleId="chefe">
							<html:option value="0" key="label.nao" />
							<html:option value="1" key="label.sim" />
						</html:select>
					</label>
				</div>
				<br />
				<label style="float: left; margin-top: 3px;">
					<b><bean:message key="label.email"/></b>
					<html:text property="email" size="51" maxlength="50" />
				</label> 
			</div>
			<div id="tabPermissoes" style="float: right; width: 60%;">
				<div style="float: left; margin-right: 5px;">
					<label class="labelComboTransferencia">
						<span id="itensPermissoesSelecionadas">
							<b>
								<bean:message key="label.permissoesSelecionadas"/>
								<span class="obrigatorio">*</span>
							</b>
						</span>
						<br/>
						<html:select multiple="true"  altKey="dica.desselecionar" titleKey="dica.desselecionar"  
			 				property="permissoes"  size="7" style="width: 220px;" styleId="permissoes"
			 				ondblclick="usuario.transferePermissao('permissoes', 'permissoesInform');" >
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
						</html:select>
					</label>
				</div>
			</div>
			<div style="clear: both; padding: 5px;" align="center" id="divBotoes">
				<htmlSGR:submit titleKey="dica.atualizar" styleClass="botaoOkCancelar" roles="3">
					<bean:message key="botao.atualizar"/>
				</htmlSGR:submit>
				<htmlSGR:button property="excluir" titleKey="dica.excluir" styleClass="botaoOkCancelar" onclick="usuario.excluir();" roles="3">
					<bean:message key="botao.excluir"/>
				</htmlSGR:button>
			</div>
		</fieldset>
	</html:form>
</div>
	