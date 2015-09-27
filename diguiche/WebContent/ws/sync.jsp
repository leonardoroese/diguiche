<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="diguiche.Sync, diguiche.mesa.*, diguiche.terminal.*;"%>

<%
	String device = "";
	String tipo = "";
	Sync sync = new Sync(this.getServletConfig());

	if (request.getParameter("device") != null)
		device = request.getParameter("device").trim();
	if (request.getParameter("tipo") != null)
		tipo = request.getParameter("tipo").trim();

	if(tipo.trim().equals("A")){
		Mesa mesa = new Mesa(this.getServletConfig());
		linMesa[] lm = mesa.listMesa(null, device);
		if(lm != null)
			sync.syncDevice(lm[0].id, "A", request.getRemoteHost());		
	}
	
	
	if(tipo.trim().equals("T")){
		Terminal terminal = new Terminal(this.getServletConfig());
		linTerminal[] lt = terminal.listTerminal(null, device);
		if(lt != null)
			sync.syncDevice(lt[0].id, "T", request.getRemoteHost());		
	}
	
	
	%>