package diguiche.display;

import java.util.ArrayList;

import javax.servlet.ServletConfig;

import diguiche.DBLin;

public class Display extends diguiche.ConBase{

	public Display(ServletConfig sconf) {
		super(sconf);

	}
	

	//###############################################################################
	//# LIST DISPLAYS
	//###############################################################################
	
	public linDisplay[] listDisplay(String id, String code){
		String q = "SELECT * FROM display WHERE id > 0 ";
		if(id != null) q = q + " AND id = " + id;
		if(code != null) q = q + " AND code = '" + code + "' "; 
		
		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			linDisplay[] displays = new linDisplay[al.size()];
			int c = 0;
			for (DBLin lin : al) {
				displays[c] = new linDisplay();
				displays[c].id = lin.getVal("id");
				displays[c].num = lin.getVal("num");
				displays[c].code = lin.getVal("code");
				c++;
			}

			return displays;
		}
		return null;
	}
	
	//###############################################################################
	//# Valid DISPLAY
	//###############################################################################
	private boolean validDisplay(linDisplay display){
		if(display == null){
			this.resType = "E";
			this.resMsg = "Informe os dados da Display";
			return false;
		}
		if(display.num == null || display.num.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o número da Display";
			return false;
		}
		
		if(display.code == null || display.code.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o codigo da Display";
			return false;
		}
		
		String q = "SELECT * FROM display WHERE num = '" + display.num.trim() + "' ";
		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			this.resType = "E";
			this.resMsg = "Número já cadastrado";
			return false;
		}

		q = "SELECT * FROM mesa WHERE num = '" + display.num.trim() + "' AND code = '"+display.code.trim()+"' ";
		al = this.readDb(q);
		if (al != null && al.size() > 0) {
			this.resType = "E";
			this.resMsg = "Código já cadastrado";
			return false;
		}

		
		return true;
	}
	
	//###############################################################################
	//# New DISPLAY
	//###############################################################################
	
	public linDisplay newDisplay(linDisplay display){
		linDisplay ret = null;

		if(!this.validDisplay(display))
			return null;
		
		String q = "INSERT INTO display (num, code) VALUES ('"+display.num.trim()+"','"+display.code.trim()+"') ";
		if (this.updateDB(q)) {
			q = "SELECT * FROM display WHERE num = '" + display.num.trim() + "' AND code = '"+display.code.trim()+"' ";
			ArrayList<DBLin> al = this.readDb(q);
			if (al != null && al.size() > 0) {
				ret = new linDisplay();
				ret.id = al.get(0).getVal("id");
				ret.num = al.get(0).getVal("num");
				ret.code = al.get(0).getVal("code");
				return ret;
			}
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível criar display";
		}
		return null;
	}
	

	//###############################################################################
	//# Change DISPLAY
	//###############################################################################
	
	public linDisplay changeDisplay(linDisplay display){
		if(!this.validDisplay(display))
			return null;
		if(display.id == null || display.id.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o ID do display";
			return null;			
		}
				
		String q = "UPDATE display SET num = '"+display.num.trim()+"', code = '"+display.code.trim()+"' WHERE id = "+ display.id;
		if (this.updateDB(q)) {
			this.resType = "S";
			this.resMsg = "Display alterado";
			return display;
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível alterar display";
		}
		return null;
	}
	
	//###############################################################################
	//# DEL DISPLAY
	//###############################################################################
	
	public boolean delDisplay(String id){
		String q = "DELETE FROM display WHERE id = " + id;
		if (this.updateDB(q)) {
			this.resType = "S";
			this.resMsg = "Registro Excluído";
			return true;
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível excluir registro";
		}
		return false;
	}
	
	
}
