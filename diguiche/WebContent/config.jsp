
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
<%@ page import="diguiche.TestEnv;"%>
<%
	//########################### VAR DEFINITION

	String pagMsg = "";
	String subCreate = "";
	
	ServletConfig sconf = this.getServletConfig();
	TestEnv test = new TestEnv(sconf);
	
	if(request.getParameter("dbhost") != null)
		test.forceSetDB(request.getParameter("dbhost"), request.getParameter("dbport"), "postgres", request.getParameter("dbuser"), request.getParameter("dbpass"));
	
	//########################### GET REQUESTS
	if(request.getParameter("subCreate") != null)
		subCreate = request.getParameter("subCreate");
	
	//########################### ACTIONS

	if(subCreate.trim().toUpperCase().equals("CRIAR")){
		test.createAll();
		out.print("<script>alert('" + test.resMsg + "');</script>");
	}
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


	<form name="frmconfig" id="frmconfig" method="post" action="config.jsp">
	<div class="tophead">
		<div class="toplogo"><img src='img/dg.png' alt='DIGUICHE' height='48' /> <span>DIGITAL GUICHÊ</span> </div>
		<div class="topversion">
		<a href='https://github.com/leonardoroese/diguiche' target='_blank' >https://github.com/leonardoroese/diguiche</a>
		<br>
		versão: 1.0</div>
	</div>

	<br>
	<h2 style="margin-left: 10px;">CONFIGURAÇÃO</h2>

	<p style="margin-left: 10px;">
		Esta é a página de configuração do sistema, verificação de
		dependências. <br> Após instalação, recomendamos que bloqueie ou
		remova a pasta cnfigscripts e esta página de configuração. <br> O
		diretório adm/ deve ser colocado em uma pasta segura, com acesso por
		login.
	</p>


	<h4 style="margin-left: 10px;">Java - Dependências</h4>

	<div class="cfglst">
		<strong>JDBC4 (pgsql)</strong>
		<%
			try {
				Class.forName("org.postgresql.Driver");
				out.print("<span class='cfgok'>OK</span>");
			} catch (Exception ex) {
				out.print(
						"<span class='cfgnok'>Driver não Localizado. Inclua no diretório de classes do seu servidor.</span>");
			}
		%>
	</div>
	<div class="cfglst">
		<strong>ESAPI 2.1</strong>
		<%
			try {
				Class.forName("org.owasp.esapi.ESAPI");
				out.print("<span class='cfgok'>OK</span>");
			} catch (Exception ex) {
				out.print(
						"<span class='cfgwok'>Driver não Localizado. Inclua no diretório de classes do seu servidor.</span>");
			}
		%>

	</div>
	<div class="cfglst">
		<strong>JSOUP</strong>
		<%
			try {
				Class.forName("org.jsoup.Jsoup");
				out.print("<span class='cfgok'>OK</span>");
			} catch (Exception ex) {
				out.print(
						"<span class='cfgwok'>Driver não Localizado. Inclua no diretório de classes do seu servidor.</span>");
			}
		%>
	</div>
	<br style="clear: both;">
	<p style="margin-left: 10px; font-size: 11px;">As bibliotecas ESAPI
		e JSOUP ainda não são obrigatórias, mas serverm para implementar
		sistema anti-falhas e anti-fraude em requisições SQL no Banco
		(injection, etc..)</p>

	<br style="clear: both;">

	<h4 style="margin-left: 10px;">Banco de Dados - PostgreSQL</h4>

	<div class="cfglst">
		<strong>Conexão</strong>
		<%
			if (test.test()){
				out.print("<span class='cfgok'>OK</span>");
		%>
		<input type="hidden" name="dbhost" value="<% out.print(request.getParameter("dbhost")); %>" />
		<input type="hidden" name="dbport" value="<% out.print(request.getParameter("dbport")); %>" />
		<input type="hidden" name="dbuser" value="<% out.print(request.getParameter("dbuser")); %>" />
		<input type="hidden" name="dbpass" value="<% out.print(request.getParameter("dbpass")); %>" />
		<%
		
		}else {
				out.print(
						"<span class='cfgnok'>Sem conexão. Verifique Drivers ou arquivo WEB.xml (dados de conexão) </span>");
		%>
		<br><br>
		Informe os dados do usuário do banco para iniciar configuração. (com permissão de Admin)<br>
		<label style="width: 100px; display: inline-block;">host</label><input type="text" name="dbhost"
			value="<%out.print(request.getParameter("dbhost"));%>" /> <br><label style="width: 100px; display: inline-block;" >porta</label><input
			type="text" name="dbport"
			value="<%out.print(request.getParameter("dbport"));%>" /> <br><label style="width: 100px; display: inline-block;">usuario</label><input
			type="text" name="dbuser"
			value="<%out.print(request.getParameter("dbuser"));%>" /> <br><label style="width: 100px; display: inline-block;">senha</label><input
			type="password" name="dbpass"
			value="<%out.print(request.getParameter("dbpass"));%>" /><br><br><input type="submit" name="subdb" value="Atualizar" />

		<%
			}
		%>
	</div>
	<br>
	<div class="cfglst">
		<strong>Tabelas</strong>
		<%
			if (test.testTables()){
				out.print("<span class='cfgok'>OK</span>");
			}else{
				out.print("<span class='cfgnok'>" + test.resMsg + "</span>");
		%>
		<br><br>Criar nova Base de dados: <input type="submit" name="subCreate" value="Criar" /> <i>(somente para primeira instalação)</i>
		<%				
			}
		%>
	</div>
	<br>
	<div class="cfglst">
		<strong>Permissões</strong>
		<%
			if (test.testPermissions())
				out.print("<span class='cfgok'>OK</span>");
			else
				out.print("<span class='cfgnok'>" + test.resMsg + "</span>");
		%>
	</div>
	<br>





	<%
		if (pagMsg.trim().length() > 0)
			out.print("<script>alert('" + pagMsg + "')</script>");
	%>

	</form>
</body>
</html>