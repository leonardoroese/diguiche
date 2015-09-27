<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="diguiche.Sync, diguiche.display.*, diguiche.key.*, org.json.JSONObject, org.json.JSONArray, org.json.JSONException;"%>

<%
	String device = "";
	Key key = new Key(this.getServletConfig());
	Display display = new Display(this.getServletConfig());
	Sync sync = new Sync(this.getServletConfig());
	
	linKey[] keyso = null;
	linKey[] keysn = null;
	JSONObject json = new JSONObject();
	JSONArray jsonArr;
	JSONArray jsonn;
	JSONArray jsonos = new JSONArray();
	JSONArray jsonns = new JSONArray();
	linDisplay[] ld = null;

	if (request.getParameter("device") != null)
		device = request.getParameter("device").trim();

	ld = display.listDisplay(null, device); 
	if (ld == null) {
		out.print("E: Dispositivo não encontrado");
	} else {
		sync.syncDevice(ld[0].id, "D", request.getRemoteHost());
		keyso = key.listKeys(null,device, false, true, false, true);
		keysn = key.listKeys(null,device, true, true, false, false);
		JSONArray jsonArrNames = new JSONArray();
		jsonArrNames.put(0,"id");
		jsonArrNames.put(1,"mesa");
		jsonArrNames.put(2,"tipo");
		jsonArrNames.put(3,"seq");
		jsonArrNames.put(4,"dtreg");
		jsonArrNames.put(5,"dtview");
		jsonArrNames.put(6,"viewer");
		int c = 0;
		if (keyso != null) {
			c = 0;
			for (linKey l : keyso) {
				jsonArr = new JSONArray();
				jsonArr.put(0, l.id);
				jsonArr.put(1, l.mesa);
				jsonArr.put(2, l.tipo);
				jsonArr.put(3, l.seq);
				jsonArr.put(4, l.dtreg);
				jsonArr.put(5, l.dtview);
				jsonArr.put(6, l.viewer);
				jsonos.put(c, jsonArr.toJSONObject(jsonArrNames));
				c++;
			}
		}
		if (keysn != null) {
			c = 0;
			for (linKey l : keysn) {
				jsonArr = new JSONArray();
				jsonArr.put(0, l.id);
				jsonArr.put(1, l.mesa);
				jsonArr.put(2, l.tipo);
				jsonArr.put(3, l.seq);
				jsonArr.put(4, l.dtreg);
				jsonArr.put(5, l.dtview);
				jsonArr.put(6, l.viewer);
				jsonns.put(c, jsonArr.toJSONObject(jsonArrNames));
				c++;
				key.markKey(l.id, device);
				break;
			}
		}
		json.put("kold", jsonos.toString());
		json.put("knew", jsonns.toString());
		json.put("eta", key.getETA());
		out.print(json.toString());
	}
%>