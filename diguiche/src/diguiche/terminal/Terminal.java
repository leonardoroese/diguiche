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

package diguiche.terminal;

import java.util.ArrayList;

import javax.servlet.ServletConfig;

import diguiche.DBLin;

public class Terminal extends diguiche.ConBase{

	public Terminal(ServletConfig sconf) {
		super(sconf);

	}
	

	//###############################################################################
	//# LIST TERMINALS
	//###############################################################################
	
	public linTerminal[] listTerminal(String id, String code){
		String q = "SELECT * FROM terminal WHERE id > 0 ";
		if(id != null) q = q + " AND id = " + id;
		if(code != null) q = q + " AND code = '" + code + "' "; 
		
		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			linTerminal[] terminals = new linTerminal[al.size()];
			int c = 0;
			for (DBLin lin : al) {
				terminals[c] = new linTerminal();
				terminals[c].id = lin.getVal("id");
				terminals[c].num = lin.getVal("num");
				terminals[c].code = lin.getVal("code");
				c++;
			}

			return terminals;
		}
		return null;
	}
	
	//###############################################################################
	//# Valid TERMINAL
	//###############################################################################
	private boolean validTerminal(linTerminal terminal){
		if(terminal == null){
			this.resType = "E";
			this.resMsg = "Informe os dados do Terminal";
			return false;
		}
		if(terminal.num == null || terminal.num.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o número do Terminal";
			return false;
		}
		
		if(terminal.code == null || terminal.code.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o codigo do Terminal";
			return false;
		}
		
		String q = "SELECT * FROM terminal WHERE num = '" + terminal.num.trim() + "' ";
		ArrayList<DBLin> al = this.readDb(q);
		if (al != null && al.size() > 0) {
			this.resType = "E";
			this.resMsg = "Número já cadastrado";
			return false;
		}

		q = "SELECT * FROM terminal WHERE num = '" + terminal.num.trim() + "' AND code = '"+terminal.code.trim()+"' ";
		al = this.readDb(q);
		if (al != null && al.size() > 0) {
			this.resType = "E";
			this.resMsg = "Código já cadastrado";
			return false;
		}

		
		return true;
	}
	
	//###############################################################################
	//# New TERMINAL
	//###############################################################################
	
	public linTerminal newTerminal(linTerminal terminal){
		linTerminal ret = null;

		if(!this.validTerminal(terminal))
			return null;
		
		String q = "INSERT INTO terminal (num, code) VALUES ('"+terminal.num.trim()+"','"+terminal.code.trim()+"') ";
		if (this.updateDB(q)) {
			q = "SELECT * FROM terminal WHERE num = '" + terminal.num.trim() + "' AND code = '"+terminal.code.trim()+"' ";
			ArrayList<DBLin> al = this.readDb(q);
			if (al != null && al.size() > 0) {
				ret = new linTerminal();
				ret.id = al.get(0).getVal("id");
				ret.num = al.get(0).getVal("num");
				ret.code = al.get(0).getVal("code");
				return ret;
			}
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível criar terminal";
		}
		return null;
	}
	

	//###############################################################################
	//# Change TERMINAL
	//###############################################################################
	
	public linTerminal changeTerminal(linTerminal terminal){
		if(!this.validTerminal(terminal))
			return null;
		if(terminal.id == null || terminal.id.length() <= 0){
			this.resType = "E";
			this.resMsg = "Informe o ID do terminal";
			return null;			
		}
				
		String q = "UPDATE terminal SET num = '"+terminal.num.trim()+"', code = '"+terminal.code.trim()+"' WHERE id = "+ terminal.id;
		if (this.updateDB(q)) {
			this.resType = "S";
			this.resMsg = "Terminal alterado";
			return terminal;
		}else{
			this.resType = "E";
			this.resMsg = "Não foi possível alterar terminal";
		}
		return null;
	}
	
	//###############################################################################
	//# DEL TERMNAL
	//###############################################################################
	
	public boolean delTerminal(String id){
		String q = "DELETE FROM terminal WHERE id = " + id;
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
