<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="diguiche.mesa.*, diguiche.display.*, diguiche.key.*;"%>
<%
	Mesa mesa = new Mesa(this.getServletConfig());
	Display display = new Display(this.getServletConfig());
	Key key = new Key(this.getServletConfig());

	linMesa linemesa = new linMesa();
	linDisplay linedisplay = new linDisplay();
	linDisplay[] displays = null;
	linMesa[] mesas = null;
	linKey[] keyso = null;
	linKey[] keysn = null;

	String subMesa = "";
	String subDisplay = "";
	String mesaNum = "";
	String mesaCode = "";
	String displayNum = "";
	String displayCode = "";
	String keyDev = "";
	String subKey = "";
	String keyTipo = "";
	String subReset = "";
	String pagMsg = "";

	// GET REQUESTS
	if (request.getParameter("subMesa") != null)
		subMesa = request.getParameter("subMesa").trim();
	if (request.getParameter("subDisplay") != null)
		subDisplay = request.getParameter("subDisplay").trim();
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
	if (request.getParameter("keyDev") != null)
		keyDev = request.getParameter("keyDev").trim();
	if (request.getParameter("keyTipo") != null)
		keyTipo = request.getParameter("keyTipo").trim();

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

	mesas = mesa.listMesa(null, null);
	displays = display.listDisplay(null, null);
	keyso = key.listKeys(null, null, false, false);
	keysn = key.listKeys(null, null, true, false);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DIGUICHE - Admin</title>
</head>
<body>


	<form name="frmMesas" id="frmMesas" action="admin.jsp" method="post">

		<label>Num</label> <input type="text" name="mesaNum" id="mesaNum"
			value="" /> <label>Código</label> <input type="text" name="mesaCode"
			id="mesaCode" value="" /> <input type="submit" name="subMesa"
			value="Nova" />
	</form>
	<table>
		<tr bgcolor="#EDEDED">
			<td width="100">Número</td>
			<td width="200">Código Dispoitivos</td>
			<td width="100"></td>
		</tr>
		<%
			if (mesas != null) {
				for (linMesa l : mesas) {
					out.print("<tr><td><a href='admin.jsp?act=&id=" + l.id + "'>" + l.num + "</a></td><td>" + l.code
							+ "</td><td><a href=''>Remover</a></td>");
				}

			}
		%>
	</table>

	<br>
	<br>
	<form name="frmDisplays" id="frmDisplays" action="admin.jsp" method="post">

		<label>Num</label> <input type="text" name="displayNum" id="displayNum"
			value="" /> <label>Código</label> <input type="text" name="displayCode"
			id="displayCode" value="" /> <input type="submit" name="subDisplay"
			value="Novo" />
	</form>
	<table>
		<tr bgcolor="#EDEDED">
			<td width="100">Número</td>
			<td width="200">Código Dispoitivos</td>
			<td width="100"></td>
		</tr>
		<%
			if (displays != null) {
				for (linDisplay l : displays) {
					out.print("<tr><td><a href='admin.jsp?act=&id=" + l.id + "'>" + l.num + "</a></td><td>" + l.code
							+ "</td><td><a href=''>Remover</a></td>");
				}

			}
		%>
	</table>

	<br>
	<br>


	<form name="frmMesas" id="frmMesas" action="admin.jsp" method="post">
		<label>Dispositivo</label> <input type="text" name="keyDev"
			id="keyDev" value="" /> <select name="keyTipo">
			<option value="1">Normal</option>
			<option value="2">Preferencial</option>
		</select> <input type="submit" name="subKey" value="Gerar" /><input
			type="submit" name="subReset" value="Reiniciar" />
	</form>

	<table>
		<tr bgcolor="#EDEDED">
			<td width="100">Id</td>
			<td width="50">Tipo</td>
			<td width="50">Seq.</td>
			<td width="120">Dt Reg</td>
		</tr>
		<%
			if (keyso != null) {
				for (linKey l : keyso) {
					out.print("<tr bgcolor='#F7F7F7'>");
					out.print("<td>" + l.id + "</td>");
					out.print("<td>" + l.tipo + "</td>");
					out.print("<td>" + l.seq + "</td>");
					out.print("<td>" + l.dtreg + "</td>");
					out.print("</tr>");

				}
			}
			if (keysn != null) {
				for (linKey l : keysn) {
					out.print("<tr>");
					out.print("<td>" + l.id + "</td>");
					out.print("<td>" + l.tipo + "</td>");
					out.print("<td>" + l.seq + "</td>");
					out.print("<td>" + l.dtreg + "</td>");
					out.print("</tr>");

				}
			}
		%>
	</table>


	<%
		if (pagMsg.trim().length() > 0)
			out.print("<script>alert('" + pagMsg + "')</script>");
	%>
</body>
</html>