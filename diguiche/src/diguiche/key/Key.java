package diguiche.key;

import java.util.ArrayList;

import javax.servlet.ServletConfig;

import diguiche.ConBase;
import diguiche.DBLin;
import diguiche.display.Display;
import diguiche.display.linDisplay;
import diguiche.mesa.Mesa;
import diguiche.mesa.linMesa;
import diguiche.terminal.Terminal;
import diguiche.terminal.linTerminal;

public class Key extends ConBase {
	private ServletConfig sconf = null;

	public Key(ServletConfig sconf) {
		super(sconf);
		this.sconf = sconf;
	}

	// ###############################################################################
	// # NEW KEY
	// ###############################################################################
	public String newKey(String tipo, String devid) {
		String q = "";

		if (devid == null || devid.trim().length() <= 0) {
			this.resType = "E";
			this.resMsg = "Informe o número o ID do dispositivo";
			return null;
		}

		if (tipo == null || tipo.trim().length() <= 0) {
			this.resType = "E";
			this.resMsg = "Informe o tipo de requisição";
			return null;
		}

		if (!tipo.equals("1") && !tipo.equals("2")) {
			this.resType = "E";
			this.resMsg = "Tipo de requisição incorreto.";
			return null;
		}

		Terminal terminal = new Terminal(this.sconf);
		linTerminal[] lt = terminal.listTerminal(null, devid);
		if (lt == null) {
			this.resType = "E";
			this.resMsg = "Dispositivo não encontrado";
			return null;
		}

		q = "SELECT seq FROM keys ORDER BY dtreg DESC LIMIT 1";

		ArrayList<DBLin> al = this.readDb(q);
		long actseq = 0;
		if (al != null && al.size() > 0) {
			actseq = new Long(al.get(0).getVal("seq")).longValue();
		}
		actseq++;
		q = "INSERT INTO keys (mesa, tipo, seq, dtreg) VALUES (0,'" + tipo + "', " + String.valueOf(actseq)
				+ ", CURRENT_TIMESTAMP)";
		if (this.updateDB(q)) {
			String actseqs = String.valueOf(actseq);
			for (int i = actseqs.trim().length(); i < 3; i++)
				actseqs = "0" + actseqs;
			if (tipo.equals("1"))
				return "N" + actseqs;
			if (tipo.equals("2"))
				return "P" + actseqs;
		} else {
			this.resType = "E";
			this.resMsg = "Não foi possível criar senha";
		}

		return null;
	}

	// ###############################################################################
	// # LIST KEYS
	// ###############################################################################
	public linKey[] listKeys(String id, String display, boolean recent, boolean limit, boolean mesa, boolean desc) {

		String q = "SELECT DISTINCT keys.id, keys.mesa, keys.tipo, keys.seq, keys.dtreg, keys.dtview, keys.viewer FROM keys ";
		if (display != null && display.trim().length() > 0)
			q = q + " INNER JOIN display ON display.code = '" + display + "' ";
		q = q + " LEFT JOIN keysdisplay ON keysdisplay.idkey = keys.id ";
		if (display != null && display.trim().length() > 0)
			q = q + " AND keysdisplay.iddisplay = display.id ";
		q = q + " WHERE keys.id > 0 ";
		if (id != null)
			q = q + " AND keys.id = " + id;
		if (mesa)
			q = q + " AND keys.mesa = 0 ";
		else
			q = q + " AND keys.mesa <> 0 ";

		if (recent) {
			q = q + " AND keysdisplay.dtview IS NULL ";
			if (display != null && display.trim().length() > 0)
				q = q + " AND (keysdisplay.iddisplay IS NULL OR keysdisplay.iddisplay <> display.id) ";
		} else {
			q = q + " AND keysdisplay.dtview IS NOT NULL ";
		}
		if (desc) {
			q = q + " ORDER BY ";
			if (recent)
				q = q + "keys.tipo DESC, ";
			q = q + "keys.dtreg DESC";
		} else {
			q = q + " ORDER BY ";
			if (recent)
				q = q + "keys.tipo DESC, ";
			q = q + "keys.dtreg";
		}
		if (limit)
			q = q + " LIMIT 4 ";

		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			linKey[] keys = new linKey[al.size()];
			int c = 0;
			for (DBLin lin : al) {
				keys[c] = new linKey();
				keys[c].id = lin.getVal("id");
				keys[c].mesa = lin.getVal("mesa");
				keys[c].tipo = lin.getVal("tipo");
				keys[c].seq = lin.getVal("seq");
				keys[c].dtreg = lin.getVal("dtreg");
				keys[c].dtview = lin.getVal("dtview");
				keys[c].viewer = lin.getVal("viewer");
				c++;
			}
			return keys;
		}
		return null;
	}

	// ###############################################################################
	// # MARK KEY
	// ###############################################################################
	public boolean markKey(String id, String devid) {
		String q = "";

		if (id == null || id.trim().length() <= 0) {
			this.resType = "E";
			this.resMsg = "Informe o número o ID da senha";
			return false;
		}

		if (devid == null || devid.trim().length() <= 0) {
			this.resType = "E";
			this.resMsg = "Informe o número o ID do dispositivo";
			return false;
		}

		Display display = new Display(this.sconf);
		linDisplay[] lin = display.listDisplay(null, devid);

		if (lin == null) {
			this.resType = "E";
			this.resMsg = "Dispositivo não encontrado";
			return false;
		}

		if (this.listKeys(id, devid, true, false, false, true) == null) {
			this.resType = "E";
			this.resMsg = "Senha não encontrada";
			return false;
		}

		q = "SELECT * FROM keysdisplay WHERE idkey = " + id + " AND iddisplay = " + lin[0].id;
		if (this.readDb(q) != null) {
			q = "UPDATE keysdisplay SET dtview = CURRENT_TIMESTAMP WHERE idkey = " + id + " AND iddisplay = "
					+ lin[0].id;
		} else {
			q = "INSERT INTO keysdisplay (idkey, iddisplay, dtview) VALUES (" + id + ", " + lin[0].id
					+ ", CURRENT_TIMESTAMP)";
		}

		if (this.updateDB(q)) {
			this.resType = "s";
			this.resMsg = "Senha atualizada";
			return true;
		} else {
			this.resType = "E";
			this.resMsg = "Não foi possível atualizar senha";
		}
		return false;
	}

	// ###############################################################################
	// # CALL KEY
	// ###############################################################################
	public String callKey(String id, String devid, boolean last) {
		String q = "";
		ArrayList<DBLin> al = null;

		Mesa mesa = new Mesa(this.sconf);
		linMesa[] lin = mesa.listMesa(null, devid);

		if (lin == null) {
			this.resType = "E";
			this.resMsg = "Dispositivo não encontrado";
			return null;
		}

		if (last) {
			q = "SELECT * FROM keys WHERE mesa = " + lin[0].num + " ORDER BY dtreg DESC";
			al = this.readDb(q);
			if (al != null && al.size() > 0) {
				id = al.get(0).getVal("id");
			}
		} else {
			if (this.listKeys(id, null, true, false, true, true) == null) {
				this.resType = "E";
				this.resMsg = "Senha não encontrada";
				return null;
			}
		}

		if (id == null || id.trim().length() <= 0) {
			this.resType = "E";
			this.resMsg = "Informe o número o ID da senha";
			return null;
		}

		if (devid == null || devid.trim().length() <= 0) {
			this.resType = "E";
			this.resMsg = "Informe o número o ID do dispositivo";
			return null;
		}

		q = "UPDATE keys SET mesa = " + lin[0].num + " WHERE id = " + id;

		if (this.updateDB(q)) {
			this.resType = "S";
			this.resMsg = "Senha solicitada";
			// Force call
			if (last) {
				q = "DELETE FROM keysdisplay WHERE idkey = " + id;
				this.updateDB(q);
			}
			// Get generated
			q = "SELECT tipo, seq FROM keys WHERE id = " + id;
			al = this.readDb(q);
			if (al != null && al.size() > 0) {
				String seq = al.get(0).getVal("seq");
				for (int i = seq.trim().length(); i < 3; i++)
					seq = "0" + seq;
				if (al.get(0).getVal("tipo").equals("1"))
					return "N" + seq;
				else
					return "P" + seq;
			}
		} else {
			this.resType = "E";
			this.resMsg = "Não foi possível solicitar senha";
		}
		return null;
	}

	// ###############################################################################
	// # RESET KEY
	// ###############################################################################
	public boolean resetKey() {
		String q = "INSERT INTO keys (tipo, seq, dtreg) VALUES ('0', 0, CURRENT_TIMESTAMP)";
		if (this.updateDB(q)) {
			this.resType = "S";
			this.resMsg = "Senha reinicializada";
			return true;
		} else {
			this.resType = "E";
			this.resMsg = "Não foi possível reinicializar senha";
		}
		return false;
	}
}
