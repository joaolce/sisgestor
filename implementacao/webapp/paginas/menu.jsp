<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>

<script language="javascript" src="js/menu/coolmenus3.js" type="text/javascript"></script>
<script language="javascript" src="js/menu/coolmenu2-config.js" type="text/javascript"></script>

<div style="clear: left; width: 50%;">
		<menu:useMenuDisplayer name="MenuSistema">
			<menu:displayMenu name="MenuRegistros"/>
			<menu:displayMenu name="MenuSair" />
		</menu:useMenuDisplayer>
</div>