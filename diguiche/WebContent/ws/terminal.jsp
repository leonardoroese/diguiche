<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="diguiche.terminal.*, diguiche.key.*;"%>
 
<%
	String device = "";
	String tipo = "";
	String genkey = "";
	Key key = new Key(this.getServletConfig());
	Terminal terminal = new Terminal(this.getServletConfig());

	linKey[] keyso = null;
	linKey[] keysn = null;

	if (request.getParameter("device") != null)
		device = request.getParameter("device").trim();
	if (request.getParameter("tipo") != null)
		tipo = request.getParameter("tipo").trim();

	if (terminal.listTerminal(null, device) == null) {
		out.print("E: Dispositivo não encontrado");

	} else {
		String nkey = key.newKey(tipo, device);
		if(nkey != null){
			out.print("S:"+nkey);
		}else{
			out.print("E: Problemas ao criar senha.");
		}
}
%>