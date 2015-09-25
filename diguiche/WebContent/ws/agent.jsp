<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="diguiche.mesa.*, diguiche.key.*;"%>

<%
	String device = "";
	String oper = "";
	String genkey = "";
	Key key = new Key(this.getServletConfig());
	Mesa mesa = new Mesa(this.getServletConfig());

	linKey[] keyso = null;
	linKey[] keysn = null;

	if (request.getParameter("device") != null)
		device = request.getParameter("device").trim();
	if (request.getParameter("oper") != null)
		oper = request.getParameter("oper").trim();

	if (mesa.listMesa(null, device) == null) {
		out.print("E: Dispositivo não encontrado");

	} else {
		keyso = key.listKeys(null, null, false, false, false, true);
		keysn = key.listKeys(null, null, true, true, true, false);
		int c = 0;
		if (oper.trim().toUpperCase().equals("C")) {
			if (keyso != null) {
				genkey = key.callKey(null, device, true);
				if (genkey != null) {
					out.print("S:" + genkey);
				} else {
					out.print("E:" + key.resMsg);
				}
			} else {
				out.print("E: Sem registros");
			}
		} else if (oper.trim().toUpperCase().equals("N")) {
			if (keysn != null) {
				genkey = key.callKey(keysn[0].id, device, false);
				if (genkey != null) {
					out.print("S:" + genkey);
				} else {
					out.print("E:" + key.resMsg);
				}
			} else {
				out.print("E: Sem registros");
			}
		} else {
			out.print("E: Operação incorreta");
		}
	}
%>