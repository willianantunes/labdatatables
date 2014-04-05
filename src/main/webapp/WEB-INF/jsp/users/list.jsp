<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="word.user"/></title>

<link rel="stylesheet" href="<c:url value="/css/datatables/css/jquery.dataTables.css"/>" type="text/css"/>

<!-- Configs: scripts -->

<script src="<c:url value="/js/jquery-1.9.1.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/dataTables/jquery.dataTables.min.js"/>" type="text/javascript"></script> 
<script type="text/javascript">
$(document).ready(function(){

	$.LABDT = new Object();
	$.LABDT.url = "<c:url value="/"/>";
	$.LABDT.dataTables_LanguageFile = $.LABDT.url + "js/dataTables/languages/" + "dataTables." + "<fmt:message key="config.language"/>" + ".txt";
	$.LABDT.dataTables_SearchName = "<fmt:message key="config.datatables.search.name"/>";
	
	$('#usersDataTable').dataTable({
		"bAutoWidth":true, 
		"bPaginate": true,  
		"bFilter": true,  
		"bSort": true,  
		"bInfo": true,  
		"bJQueryUI": false,  
		"sPaginationType": "full_numbers", 
		"aoColumns": [
			             null, // Id
			             null, // Name
			             {
			            	 "mData": null,
			            	 "bSortable": false,		            	 
			            	 "fnRender": function(oObj)
			            	 {
			            		 return "<a href='" + $.LABDT.url + "users/delete/" + oObj.aData[0] + "'>Excluir</a> | "
			            		 + "<a href='" + $.LABDT.url + "users/" + oObj.aData[0] + "'>Editar</a>";
			            	 }
			             }			             
			             ],					             
		"bProcessing": true,
		"oLanguage": {
			"sUrl": $.LABDT.dataTables_LanguageFile,
			"sSearch": $.LABDT.dataTables_SearchName
		},
		"bServerSide": true,  
		"sAjaxSource": '/labdatatables/users/json/datatables/paginate',
		"sServerMethod": "POST"
	});
	
});
</script>

</head>
<body>

<h1 style="color: blue;"><fmt:message key="lbl.user.listOfUsers"/></h1>

	<table id="usersDataTable">
	    <thead>  
	        <tr>  
	            <th>ID</th>  
	            <th><fmt:message key="word.name"/></th>  
	            <th><fmt:message key="word.action"/></th>	            
	        </tr>  
	    </thead> 	
	    <tbody>  
            <tr>  
                <td></td>  
                <td></td>  
                <td></td>
            </tr>
    	</tbody>	
	</table>


<br />

<c:if test="${not empty mensagem}">

<h3><fmt:message key="word.message"/>: <span style="color: red;"> ${mensagem}</span></h3>

</c:if>

<h1 style="color: blue;"><fmt:message key="lbl.user.userRegistration"/></h1>

<form action="<c:url value="/users" />" name="registerForm" method="post">
	<fieldset>
		<legend><fmt:message key="lbl.user.newUser"/></legend>
		<label for="name"><fmt:message key="word.name"/></label>
		<input type="text" name="user.name" />
		
		<input type="submit" value="<fmt:message key="word.register"/>" />
	</fieldset>
</form>

<p><a href="<c:url value="/language/pt_BR" />">PT-BR</a> / <a style="margin-right: 26px;" href="<c:url value="/language/en_US" />">EN-US</a></p>

</body>
</html>