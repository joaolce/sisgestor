<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script type="text/javascript" src="dwr/interface/ManterUsuarioDWR.js"></script>
<script type="text/javascript" src="js/manterUsuario/manterUsuario.js"></script>

<html:form action="/manterUsuario.do?method=salvar" onsubmit="usuario.salvar(this); return false;"  styleId="manterUsuarioForm">
	<fieldset style="margin: 5pt auto; padding: 10px; width: 95%;">
		<div style="float: left;">
			<label>
				<b>
					<bean:message key="label.login"/>
					<span class="obrigatorio">*</span>
				</b>
				<br />
				<html:text property="login" size="11" maxlength="10" />
			</label> 
			<label style="float: left; margin-top: 15px;">
				<b>
					<bean:message key="label.nome"/>
					<span class="obrigatorio">*</span>
				</b>
				<br />
				<html:text property="nome" size="51" maxlength="100" />
			</label> 
			<div style="width: 60%; margin-top: 15px;">
				<label style="float: left; margin-top: inherit;">
					<b>
						<bean:message key="label.departamento"/>
						<span class="obrigatorio">*</span>
					</b>
					<br />
					<html:select property="departamento" styleId="departamento">
						<html:option value="" />
						<html:optionsCollection name="manterUsuarioForm" property="listaDepartamentos" label="sigla" value="id" />
					</html:select>
				</label>
				<label style="float: right; margin-top: inherit;">
					<b>
						<bean:message key="label.chefe"/>
						<span class="obrigatorio">*</span>
					</b>
					<br />
					<html:select property="chefe" styleId="chefe">
						<html:option value="0" key="label.nao" />
						<html:option value="1" key="label.sim" />
					</html:select>
				</label>
			</div>
			<label style="float: left; margin-top: 15px;">
				<b><bean:message key="label.email"/></b> <br />
				<html:text property="email" size="51" maxlength="50" />
			</label>
			<br />
		</div>
		<div id="tabPermissoes" style="margin: 6px;" class="divInternoAba">
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
					 	property="permissoes"  size="10" style="width: 220px;" styleId="permissoesNovo"
					 	ondblclick="usuario.transferePermissao('permissoesNovo', 'permissoesInformNovo');" >
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
					onclick="usuario.transfereTodasPermissoes('permissoesInformNovo', 'permissoesNovo');">
					<bean:message key="label.direita"/>
				</html:button>
				<br />
				<br />
				<html:button property="esquerda" titleKey="dica.desselecionaVarios" style="width: 30px;" 
					onclick="usuario.transfereTodasPermissoes('permissoesNovo', 'permissoesInformNovo');">
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
						property="permissoesInform" value="id" size="10" style="width: 220px;" styleId="permissoesInformNovo" 
						ondblclick="usuario.transferePermissao('permissoesInformNovo', 'permissoesNovo');">
						<logic:present name="manterUsuarioForm" property="permissoesDisponiveis">
							<html:optionsCollection name="manterUsuarioForm" property="permissoesDisponiveis" label="descricao" value="id" />
						</logic:present>
					</html:select>
				</label>
			</div>
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