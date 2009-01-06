<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<link rel="stylesheet" type="text/css" media="screen" href="css/coolmenu.css"/>
		
<script language="javascript" src="js/menu/coolmenus4.js" type="text/javascript"></script>
<script language="javascript" src="js/menu/cm_addins.js" type="text/javascript"></script>
<script language="javascript" src="js/menu/coolmenu4-config.js" type="text/javascript"></script>

<div style="clear: left; width: 50%;">
	<logic:present name="usuarioSessao">
		<menu:useMenuDisplayer name="MenuSistema">
			<menu:displayMenu name="MenuUsuario"/>
			<menu:displayMenu name="MenuDepartamento"/>
			<menu:displayMenu name="MenuWorkflow"/>
			<menu:displayMenu name="MenuExtrairRelatorio"/>
			<menu:displayMenu name="MenuSair" />
		</menu:useMenuDisplayer>
	</logic:present>
</div>