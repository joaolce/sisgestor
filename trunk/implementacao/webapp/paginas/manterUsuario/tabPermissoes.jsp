<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script type="text/javascript" src="dwr/interface/ManterUsuarioDWR.js"></script>
<script type="text/javascript" src="js/manterUsuario/manterUsuario.js"></script>

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
			 	<html:optionsCollection name="manterUsuarioForm" property="roles" label="descricao" value="id"/>
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
				<b><bean:message key="label.permissoes"/></b>
			</span>
			<br />
			<html:select multiple="true" altKey="dica.selecionar" titleKey="dica.selecionar" 
				property="permissoesInform" value="id" size="10" style="width: 220px;" styleId="permissoesInform" 
				ondblclick="usuario.transferePermissao('permissoesInform', 'permissoes');">
				<html:optionsCollection name="manterUsuarioForm" property="permissoesInformadas" label="descricao" value="id"/>
			</html:select>
		</label>
	</div>
</div>