<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<div class="divInternoAba">
	<div style="float: left; margin-right: 5px;">
		<label class="labelComboTransferencia">
			<span id="itensPermissoesSelecionadas">
				<b><bean:message key="label.permissoesSelecionadas"/></b>
			</span>
			<br/>
			<html:select multiple="true"  altKey="dica.desselecionar" titleKey="dica.desselecionar"  
			 	property="permissoes"  size="10" style="width: 220px;" styleId="permissoes"
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
				property="permissoesInform" value="id" size="10" style="width: 220px;" styleId="permissoesInform" 
				ondblclick="usuario.transferePermissao('permissoesInform', 'permissoes');">
				<logic:present name="manterUsuarioForm" property="permissoesDisponiveis">
					<html:optionsCollection name="manterUsuarioForm" property="permissoesDisponiveis" label="descricao" value="id" />
				</logic:present>
			</html:select>
		</label>
	</div>
</div>