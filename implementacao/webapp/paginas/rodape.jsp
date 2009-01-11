<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<div style="clear: both;">
	<center>
		<div style="width: 25%; padding: 10px;">
			<div align="left" style="float: left; width: 32%;">
				<html:link titleKey="dica.topo" href="#"> 
					<html:img altKey="dica.topo" srcKey="imagem.topo"/>
				</html:link>
			</div>
			<div align="center" style="float: left; width: 32%">
				<html:link titleKey="dica.paginaInicial" href="principal.do?method=entrada">
					<html:img altKey="dica.paginaInicial" srcKey="imagem.home"/>
				</html:link>
			</div>
			<div align="right" style="float: right; width: 32%">
				<html:link titleKey="dica.voltar" href="#" onclick="history.back();">
					<html:img altKey="dica.voltar" srcKey="imagem.voltar"/>
				</html:link>
			</div>
		</div>
	</center>
</div>