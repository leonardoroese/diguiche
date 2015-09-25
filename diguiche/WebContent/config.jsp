<%
/*
Copyright (c) 2015 DIGUICHE
Author: Leonardo Germano Roese (leonardoroese@hotmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
%>
<%@page import="org.postgresql.Driver"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="diguiche.TestEnv;"%>
<%

	//########################### VAR DEFINITION

	String pagMsg = "";
	TestEnv test = new TestEnv(this.getServletConfig());
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

	<p style="margin-left: 10px;">
	Esta é a página de configuração do sistema, verificação de dependências.
	<br>
	Após instalação, recomendamos que bloqueie ou remova a pasta cnfigscripts e esta página de configuração.
	<br>
	O diretório adm/ deve ser colocado em uma pasta segura, com acesso por login.
	</p>
	

	<h4 style="margin-left: 10px;">Java - Dependências</h4>
	
	<div class="cfglst">
		<strong>JDBC4 (pgsql)</strong>
		<%
			try{
				Class.forName("org.postgresql.Driver");
				out.print("<span class='cfgok'>OK</span>");
			}catch(Exception ex){
				out.print("<span class='cfgnok'>Driver não Localizado. Inclua no diretório de classes do seu servidor.</span>");
			}

		%>
	</div>
	<div class="cfglst">
		<strong>ESAPI 2.1</strong>
		<%
			try{
				Class.forName("org.owasp.esapi.ESAPI");
				out.print("<span class='cfgok'>OK</span>");
			}catch(Exception ex){
				out.print("<span class='cfgwok'>Driver não Localizado. Inclua no diretório de classes do seu servidor.</span>");
			}

		%>
		
	</div>
	<div class="cfglst">
		<strong>JSOUP</strong>
		<%
			try{
				Class.forName("org.jsoup.Jsoup");
				out.print("<span class='cfgok'>OK</span>");
			}catch(Exception ex){
				out.print("<span class='cfgwok'>Driver não Localizado. Inclua no diretório de classes do seu servidor.</span>");
			}

		%>
	</div>
	<br style="clear: both;">
	<p style="margin-left: 10px; font-size: 11px;">
	As bibliotecas ESAPI e JSOUP ainda não são obrigatórias, mas serverm para implementar sistema anti-falhas e anti-fraude em requisições SQL no Banco (injection, etc..)	
	</p>
	
	<br style="clear: both;">
	
	<h4 style="margin-left: 10px;">Banco de Dados - PostgreSQL</h4>
	
	<div class="cfglst">
		<strong>Conexão</strong>
		<%
			if(test.test())
				out.print("<span class='cfgok'>OK</span>");
			else
				out.print("<span class='cfgnok'>Sem conexão. Verifique Drivers ou arquivo WEB.xml (dados de conexão)</span>");
		%>
	</div><br>
	<div class="cfglst">
		<strong>Tabelas</strong>
		<%
			if(test.testTables())
				out.print("<span class='cfgok'>OK</span>");
			else
				out.print("<span class='cfgnok'>"+test.resMsg+"</span>");
		%>	
	</div><br>
	<div class="cfglst">
		<strong>Permissões</strong>
		<%
			if(test.testPermissions())
				out.print("<span class='cfgok'>OK</span>");
			else
				out.print("<span class='cfgnok'>"+test.resMsg+"</span>");
		%>	
	</div><br>
	
	
	
	
	
	<%
		if (pagMsg.trim().length() > 0)
			out.print("<script>alert('" + pagMsg + "')</script>");
	%>
</body>
</html>