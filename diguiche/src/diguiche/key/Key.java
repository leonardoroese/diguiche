package diguiche.key;

import java.util.ArrayList;

import javax.servlet.ServletConfig;

import diguiche.ConBase;
import diguiche.DBLin;
import diguiche.mesa.Mesa;
import diguiche.mesa.linMesa;

public class Key extends ConBase{
	private ServletConfig sconf = null;
	
	public Key(ServletConfig sconf) {
		super(sconf);
		this.sconf = sconf;
	}

	//###############################################################################
	//# NEW KEY
	//###############################################################################
	public String newKey(String tipo, String devid){
		String q = "";
		
		if(devid == null || devid.trim().length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o número o ID do dispositivo";
			return null;
		}
		
		if(tipo == null || tipo.trim().length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o tipo de requisição";
			return null;
		}
		
		if(!tipo.equals("1") && !tipo.equals("2")){
			this.resType = "E";
			this.resMsg = "Tipo de requisição incorreto.";
			return null;
		}

		
		
		Mesa mesa = new Mesa(this.sconf);
	
		if(mesa.listMesa(null, devid) == null){
			this.resType = "E";
			this.resMsg = "Dispositivo não encontrado";
			return null;
		}
		
		q = "SELECT seq FROM keys ORDER BY dtreg DESC LIMIT 1";
		
		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			long actseq = new Long(al.get(0).getVal("seq")).longValue();
			actseq++;
			q = "INSERT INTO seq (tipo, seq, dtreg) VALUES ('"+tipo+"', "+String.valueOf(actseq)+", CURRENT_TIMESTAMP)";
			if(this.updateDB(q)){
				if(tipo.equals("1"))
					return "N"+String.valueOf(actseq);
				if(tipo.equals("2"))
					return "P"+String.valueOf(actseq);
			}else{
				this.resType = "E";
				this.resMsg = "Não foi possível criar senha";
			}
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível criar senha";
		}
		
		return null;
	}

	
	//###############################################################################
	//# LIST KEYS
	//###############################################################################
	public linKey[] listKeys(String id, boolean recent, boolean limit ){
		
		String q = "SELECT * FROM keys WHERE id > 0 ";
		if(id != null) q = q + " AND id = " + id;
		if(recent){
			q = q + " AND dtview = NULL";
			q = q + " ORDER BY dtreg";	
		}else{
			q = q + " AND dtview IS NOT NULL";
			q = q + " ORDER BY dtview DESC";
		}
		if(limit) q = q + " LIMIT 5"; 

		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			linKey[] keys = new linKey[al.size()];
			int c = 0;
			for (DBLin lin : al) {
				keys[c] = new linKey();
				keys[c].id = lin.getVal("id");
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
	
	
	//###############################################################################
	//# MARK KEY
	//###############################################################################
	public boolean markKey(String id, String devid){

		if(id == null || id.trim().length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o número o ID da senha";
			return false;
		}
		
		if(devid == null || devid.trim().length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o número o ID do dispositivo";
			return false;
		}
		
		Mesa mesa = new Mesa(this.sconf);
		
		if(mesa.listMesa(null, devid) == null){
			this.resType = "E";
			this.resMsg = "Dispositivo não encontrado";
			return false;
		}
		
		if(this.listKeys(id, true, false) == null){
			this.resType = "E";
			this.resMsg = "Senha não encontrada";
			return false;
		}
		
		String q = "UPDATE keys SET dtview = CURRENT_TIMESTAMP, viewer = '" + devid + "' WHERE  id = " + id;
		if(this.updateDB(q)){
			this.resType = "s";
			this.resMsg = "Senha atualizada";
			return true;
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível atualizar senha";
		}
		return false;
	}
	
}
