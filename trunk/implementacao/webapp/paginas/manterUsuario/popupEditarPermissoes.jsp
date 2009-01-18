<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<div style="overflow: auto; width: 100%; height: 320px; border: 1px solid gray;">
	<table style="width: 100%;">
		<thead>
			<tr>
			    <th><bean:message key="label.id"/></th>
			    <th><bean:message key="label.descricao"/></th>		    
			</tr>
		</thead>
		<tbody class="corpoTabela">
				<tr>
					<td><c:out value="3" /></td>
					<td><c:out value="descricao afdadfa" /></td>
				</tr>
		</tbody>
	</table>
</div>