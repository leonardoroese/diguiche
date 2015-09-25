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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="diguiche.mesa.*, diguiche.terminal.*, diguiche.display.*, diguiche.key.*;"%>
<%
	Mesa mesa = new Mesa(this.getServletConfig());
	Display display = new Display(this.getServletConfig());
	Terminal terminal = new Terminal(this.getServletConfig());
	Key key = new Key(this.getServletConfig());

	linMesa linemesa = new linMesa();
	linDisplay linedisplay = new linDisplay();
	linTerminal lineterminal = new linTerminal();
	linDisplay[] displays = null;
	linMesa[] mesas = null;
	linTerminal[] terminais = null;
	linKey[] keyso = null;
	linKey[] keysnm = null;

	//########################### VAR DEFINITION
	String op = "";
	String opid = "";
	String subMesa = "";
	String subDisplay = "";
	String subTerminal = "";
	String mesaNum = "";
	String mesaCode = "";
	String displayNum = "";
	String displayCode = "";
	String terminalNum = "";
	String terminalCode = "";
	String keyDev = "";
	String subKey = "";
	String keyTipo = "";
	String subReset = "";
	String pagMsg = "";

	//########################### GET REQUESTS
	if (request.getParameter("op") != null)
		op = request.getParameter("op").trim();
	if (request.getParameter("opid") != null)
		opid = request.getParameter("opid").trim();
	if (request.getParameter("subMesa") != null)
		subMesa = request.getParameter("subMesa").trim();
	if (request.getParameter("subDisplay") != null)
		subDisplay = request.getParameter("subDisplay").trim();
	if (request.getParameter("subTerminal") != null)
		subTerminal = request.getParameter("subTerminal").trim();
	if (request.getParameter("subKey") != null)
		subKey = request.getParameter("subKey").trim();
	if (request.getParameter("subReset") != null)
		subReset = request.getParameter("subReset").trim();
	if (request.getParameter("mesaNum") != null)
		mesaNum = request.getParameter("mesaNum").trim();
	if (request.getParameter("mesaCode") != null)
		mesaCode = request.getParameter("mesaCode").trim();
	if (request.getParameter("displayNum") != null)
		displayNum = request.getParameter("displayNum").trim();
	if (request.getParameter("displayCode") != null)
		displayCode = request.getParameter("displayCode").trim();
	if (request.getParameter("terminalNum") != null)
		terminalNum = request.getParameter("terminalNum").trim();
	if (request.getParameter("terminalCode") != null)
		terminalCode = request.getParameter("terminalCode").trim();
	if (request.getParameter("keyDev") != null)
		keyDev = request.getParameter("keyDev").trim();
	if (request.getParameter("keyTipo") != null)
		keyTipo = request.getParameter("keyTipo").trim();

	//########################### ACTIONS

	if (subMesa.toUpperCase().equals("NOVA")) {
		linemesa.num = mesaNum;
		linemesa.code = mesaCode;

		linMesa createdmesa = mesa.newMesa(linemesa);
		if (createdmesa != null) {
			pagMsg = "Nova mesa criada com sucesso.";
		} else {
			pagMsg = mesa.resMsg;
		}
	}

	if (subDisplay.toUpperCase().equals("NOVO")) {
		linedisplay.num = displayNum;
		linedisplay.code = displayCode;

		linDisplay createddisplay = display.newDisplay(linedisplay);
		if (createddisplay != null) {
			pagMsg = "Novo display criado com sucesso.";
		} else {
			pagMsg = mesa.resMsg;
		}
	}

	if (subTerminal.toUpperCase().equals("NOVO")) {
		lineterminal.num = terminalNum;
		lineterminal.code = terminalCode;

		linTerminal createdterminal = terminal.newTerminal(lineterminal);
		if (createdterminal != null) {
			pagMsg = "Novo terminal criado com sucesso.";
		} else {
			pagMsg = mesa.resMsg;
		}
	}

	if (subKey.toUpperCase().equals("GERAR")) {
		if (key.newKey(keyTipo, keyDev) != null) {
			pagMsg = "Senha gerada.";
		} else {
			pagMsg = key.resMsg;
		}
	}

	if (subReset.toUpperCase().equals("REINICIAR")) {
		key.resetKey();
		pagMsg = key.resMsg;
	}

	// Delete Display (id)
	if (op.trim().toUpperCase().equals("DD")) {
		display.delDisplay(opid);
		pagMsg = display.resMsg;
	}
	// Delete Mesa (id)
	if (op.trim().toUpperCase().equals("DM")) {
		mesa.delMesa(opid);
		pagMsg = mesa.resMsg;
	}
	// Delete Terminal (id)
	if (op.trim().toUpperCase().equals("DT")) {
		terminal.delTerminal(opid);
		pagMsg = terminal.resMsg;
	}

	//########################### LOAD DATA

	mesas = mesa.listMesa(null, null);
	displays = display.listDisplay(null, null);
	terminais = terminal.listTerminal(null, null);
	keyso = key.listKeys(null, null, false, true, false, true);
	keysnm = key.listKeys(null, null, true, true, true, true);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DIGUICHE - Admin</title>
<link href="../css/admin.css" rel="stylesheet">
<script>
	function delDisp(op, id) {
		document.getElementById('op').value = op;
		document.getElementById('opid').value = id;
		document.getElementById('frmAdmin').submit();
	}
</script>
</head>
<body>
	<form name="frmAdmin" id="frmAdmin" method="post" action="admin.jsp">
		<input type="hidden" name="op" id="op" value="" /> <input
			type="hidden" name="opid" id="opid" value="" />
	</form>
	<div class="tophead">
		<div class="toplogo">DIGUICHE</div>
		<div class="topversion">v1.0</div>
	</div>
	<br>
	<h2 style="margin-left: 10px;">Administração e Monitoramento</h2>

	<div class="admbox" style="width: 450px; height: 200px;">
		<div class="admboxtit">MESAS</div>
		<div class="admboxcontent">
			<form name="frmMesas" id="frmMesas" action="admin.jsp" method="post">
				<label>Num</label> <input type="text" style="width: 50px;"
					name="mesaNum" id="mesaNum" value="" /> <label>Código</label> <input
					type="text" name="mesaCode" id="mesaCode" value="" /> <input
					type="submit" name="subMesa" value="Nova" />
			</form>
			<br>
			<div style="height: 120px; overflow: auto;">
				<table>
					<tr class="thr">
						<td width="100">Número</td>
						<td width="200">Código Dispoitivos</td>
						<td width="100"></td>
					</tr>
					<%
						if (mesas != null) {
							for (linMesa l : mesas) {
								out.print("<tr class='thl'><td>" + l.num + "</td><td>" + l.code
										+ "</td><td><div style='cursor: pointer' onclick=\"delDisp('DM', '" + l.id
										+ "')\">Remover</div></td>");
							}

						}
					%>
				</table>
			</div>
		</div>
	</div>
	<div class="admbox" style="width: 450px; height: 200px;">
		<div class="admboxtit">DISPLAYS</div>
		<div class="admboxcontent">
			<form name="frmDisplays" id="frmDisplays" action="admin.jsp"
				method="post">

				<label>Num</label> <input type="text" style="width: 50px;"
					name="displayNum" id="displayNum" value="" /> <label>Código</label>
				<input type="text" name="displayCode" id="displayCode" value="" />
				<input type="submit" name="subDisplay" value="Novo" />
			</form>
			<br>
			<div style="height: 120px; overflow: auto;">
				<table>
					<tr class="thr">
						<td width="100">Número</td>
						<td width="200">Código Dispoitivos</td>
						<td width="100"></td>
					</tr>
					<%
						if (displays != null) {
							for (linDisplay l : displays) {
								out.print("<tr class='thl'><td>" + l.num + "</td><td>" + l.code
										+ "</td><td><div style='cursor: pointer' onclick=\"delDisp('DD', '" + l.id
										+ "')\">Remover</div></td>");
							}

						}
					%>
				</table>
			</div>
		</div>
	</div>

	<div class="admbox" style="width: 450px; height: 200px;">
		<div class="admboxtit">TERMINAIS</div>
		<div class="admboxcontent">
			<form name="frmDisplays" id="frmDisplays" action="admin.jsp"
				method="post">

				<label>Num</label> <input style="width: 50px;" type="text"
					name="terminalNum" id="terminalNum" value="" /> <label>Código</label>
				<input type="text" name="terminalCode" id="terminalCode" value="" />
				<input type="submit" name="subTerminal" value="Novo" />
			</form>
			<br>
			<div style="height: 120px; overflow: auto;">
				<table>
					<tr class="thr">
						<td width="100">Número</td>
						<td width="200">Código Dispoitivos</td>
						<td width="100"></td>
					</tr>
					<%
						if (terminais != null) {
							for (linTerminal l : terminais) {
								out.print("<tr class='thl'><td>" + l.num + "</td><td>" + l.code
										+ "</td><td><div style='cursor: pointer' onclick=\"delDisp('DT', '" + l.id
										+ "')\">Remover</div></td>");
							}

						}
					%>
				</table>
			</div>
		</div>
	</div>

	<div class="admbox" style="width: 520px; height: 250px;">
		<div class="admboxtit">SENHAS</div>
		<div class="admboxcontent">
			<form name="frmMesas" id="frmMesas" action="admin.jsp" method="post">
				<label>Dispositivo</label> <input type="text" name="keyDev"
					id="keyDev" value="" /> <select name="keyTipo">
					<option value="1">Normal</option>
					<option value="2">Preferencial</option>
				</select> <input type="submit" name="subKey" value="Gerar" />&nbsp;&nbsp;&nbsp;&nbsp;<input
					type="submit" name="subReset" value="Reiniciar" />
			</form>
			<br> <strong>Senhas (últimas na fila)</strong> <br>
			<div style="height: 160px; overflow: auto;">
				<table>
					<tr class="thr">
						<td width="50" align="center">Id</td>
						<td width="50" align="center">Senha</td>
						<td width="120" align="center">Data do registro</td>
					</tr>
					<%
						String msec = "";
						if (keysnm != null) {
							for (linKey l : keysnm) {
								msec = l.seq;
								for(int i = msec.trim().length(); i < 3; i++)
									msec = "0"+msec;
								if(l.tipo.trim().toUpperCase().equals("N"))
									msec = "N"+msec;
								else
									msec = "P"+msec;
								out.print("<tr class='thl' style='background-color:#ffee00'>");
								out.print("<td align='center'>" + l.id + "</td>");
								out.print("<td align='center'>" + msec + "</td>");
								out.print("<td align='center'>" + l.dtreg + "</td>");
								out.print("</tr>");

							}
						}
						if (keyso != null) {
							for (linKey l : keyso) {
								msec = l.seq;
								for(int i = msec.trim().length(); i < 3; i++)
									msec = "0"+msec;
								if(l.tipo.trim().toUpperCase().equals("N"))
									msec = "N"+msec;
								else
									msec = "P"+msec;
								out.print("<tr class='thl'>");
								out.print("<td align='center'>" + l.id + "</td>");
								out.print("<td align='center'>" + msec + "</td>");
								out.print("<td align='center'>" + l.dtreg + "</td>");
								out.print("</tr>");

							}
						}
					%>
				</table>
			</div>
		</div>
	</div>

	<%
		if (pagMsg.trim().length() > 0)
			out.print("<script>alert('" + pagMsg + "')</script>");
	%>
</body>
</html>