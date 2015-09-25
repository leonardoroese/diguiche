<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="diguiche.mesa.*, diguiche.terminal.*, diguiche.display.*, diguiche.key.*;"%>
<%

	//########################### VAR DEFINITION

	String pagMsg = "";

	//########################### GET REQUESTS

	
	//########################### ACTIONS


	//########################### LOAD DATA


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DIGUICHE - Admin</title>
<link href="css/admin.css" rel="stylesheet">

</head>
<body>
	<div class="tophead">
		<div class="toplogo">DIGUICHE</div>
		<div class="topversion">v1.0</div>
	</div>
	<br>
	<h2 style="margin-left: 10px;">CONFIGURAÇÃO</h2>


	<h4 style="margin-left: 10px;">Java - Dependências</h4>
	
	<div class="cfglst"><strong>JDBC4 (pgsql)</strong><span class="cfgok">OK</span></div>
	<div class="cfglst"><strong>ESAPI 2.1</strong><span class="cfgok">OK</span></div>
	<div class="cfglst"><strong>JSOUP</strong><span class="cfgok">OK</span></div>
	
	<br style="clear: both;">
	
	<h4 style="margin-left: 10px;">Banco de Dados - PostgreSQL</h4>
	
	<div class="cfglst"><strong>Conexão</strong><span class="cfgok">OK</span></div><br>
	<div class="cfglst"><strong>Tabelas</strong><span class="cfgok">OK</span></div><br>
	<div class="cfglst"><strong>Permissões</strong><span class="cfgnok">Falta</span></div><br>
	
	
	
	
	
	<%
		if (pagMsg.trim().length() > 0)
			out.print("<script>alert('" + pagMsg + "')</script>");
	%>
</body>
</html>