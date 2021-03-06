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

package diguiche.mesa;

import java.util.ArrayList;

import javax.servlet.ServletConfig;

import diguiche.DBLin;

public class Mesa extends diguiche.ConBase{

	public Mesa(ServletConfig sconf) {
		super(sconf);

	}
	

	//###############################################################################
	//# LIST MESA
	//###############################################################################
	
	public linMesa[] listMesa(String id, String code){
		String q = "SELECT * FROM mesa WHERE id > 0 ";
		if(id != null) q = q + " AND id = " + id;
		if(code != null) q = q + " AND code = '" + code + "' "; 
		
		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			linMesa[] mesas = new linMesa[al.size()];
			int c = 0;
			for (DBLin lin : al) {
				mesas[c] = new linMesa();
				mesas[c].id = lin.getVal("id");
				mesas[c].num = lin.getVal("num");
				mesas[c].code = lin.getVal("code");
				c++;
			}

			return mesas;
		}
		return null;
	}
	
	//###############################################################################
	//# Valid MESA
	//###############################################################################
	private boolean validMesa(linMesa mesa){
		if(mesa == null){
			this.resType = "E";
			this.resMsg = "Informe os dados da Terminal";
			return false;
		}
		if(mesa.num == null || mesa.num.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o número da Terminal";
			return false;
		}
		
		if(mesa.code == null || mesa.code.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o codigo da Terminal";
			return false;
		}
		
		String q = "SELECT * FROM mesa WHERE num = '" + mesa.num.trim() + "' ";
		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			this.resType = "E";
			this.resMsg = "Número já cadastrado";
			return false;
		}

		q = "SELECT * FROM mesa WHERE num = '" + mesa.num.trim() + "' AND code = '"+mesa.code.trim()+"' ";
		al = this.readDb(q);
		if (al != null && al.size() > 0) {
			this.resType = "E";
			this.resMsg = "Código já cadastrado";
			return false;
		}

		
		return true;
	}
	
	//###############################################################################
	//# New MESA
	//###############################################################################
	
	public linMesa newMesa(linMesa mesa){
		linMesa ret = null;

		if(!this.validMesa(mesa))
			return null;
		
		String q = "INSERT INTO mesa (num, code) VALUES ('"+mesa.num.trim()+"','"+mesa.code.trim()+"') ";
		if (this.updateDB(q)) {
			q = "SELECT * FROM mesa WHERE num = '" + mesa.num.trim() + "' AND code = '"+mesa.code.trim()+"' ";
			ArrayList<DBLin> al = this.readDb(q);
			if (al != null && al.size() > 0) {
				ret = new linMesa();
				ret.id = al.get(0).getVal("id");
				ret.num = al.get(0).getVal("num");
				ret.code = al.get(0).getVal("code");
				return ret;
			}
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível criar mesa";
		}
		return null;
	}
	

	//###############################################################################
	//# Change MESA
	//###############################################################################
	
	public linMesa changeMesa(linMesa mesa){
		if(!this.validMesa(mesa))
			return null;
		if(mesa.id == null || mesa.id.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o ID da mesa";
			return null;			
		}
				
		String q = "UPDATE mesa SET num = '"+mesa.num.trim()+"', code = '"+mesa.code.trim()+"' WHERE id = "+ mesa.id;
		if (this.updateDB(q)) {
			this.resType = "S";
			this.resMsg = "Terminal alterada";
			return mesa;
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível alterar mesa";
		}
		return null;
	}
	
	//###############################################################################
	//# DEL MESA
	//###############################################################################
	
	public boolean delMesa(String id){
		String q = "DELETE FROM mesa WHERE id = " + id;
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
