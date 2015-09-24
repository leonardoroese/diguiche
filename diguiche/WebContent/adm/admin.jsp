<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="diguiche.mesa.*;"%>
<%
Mesa mesa = new Mesa(this.getServletConfig());
linMesa linemesa = new linMesa();
linMesa[] mesas = null;
String subMesa = "";
String mesaNum = "";
String mesaCode = "";
String pagMsg = "";

// GET REQUESTS
if(request.getParameter("subMesa") != null)	subMesa = request.getParameter("subMesa").trim();
if(request.getParameter("mesaNum") != null)	mesaNum = request.getParameter("mesaNum").trim();
if(request.getParameter("mesaCode") != null)	mesaCode = request.getParameter("mesaCode").trim();

if(subMesa.toUpperCase().equals("NOVA")){
	linemesa.num = mesaNum;
	linemesa.code = mesaCode;
	
	linMesa createdmesa = mesa.newMesa(linemesa);
	if(createdmesa != null){
		pagMsg = "Nova mesa criada com sucesso.";
	}else{
		pagMsg = mesa.resMsg;
	}
	
	mesas = mesa.listMesa(null, null);
}
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
	<tr>
		<td width="100">Número</td>
		<td width="200">Código Dispoitivos</td>
		<td width="100"></td>
	</tr>
<%
	if(mesas != null){
		for(linMesa l : mesas){
			out.print("<tr><td><a href='admin.jsp?act=&id="+l.id+"'>"+l.num+"</a></td><td>"+l.code+"</td><td><a href=''>Remover</a></td>");
		}
		
	}
%>
	</table>

<%
if(pagMsg.trim().length() > 0)
	out.print("<script>alert('" + pagMsg + "')</script>");

%>
</body>
</html>