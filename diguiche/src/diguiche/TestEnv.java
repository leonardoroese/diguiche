/*
Copyright (c) 2015 Leonardo Germano Roese (leonardoroese@hotmail.com)

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

package diguiche;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;

public class TestEnv extends ConBase {
	private ServletConfig sconf = null;

	public TestEnv(ServletConfig sconf) {
		super(sconf);
		this.sconf = sconf;
		// TODO Auto-generated constructor stub
	}

	// ######################################################################################
	// Verify Tables
	// ######################################################################################
	public boolean testTables() {
		String err = "";
		String errseq = "";
		ArrayList<DBLin> al = null;
		this.resMsg = "";
		this.resType = "";

		// check display
		al = this.readDb("select count(*) AS res from pg_class where relname='display'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			err = err + "display|";
		// check keys
		al = this.readDb("select count(*) AS res from pg_class where relname='keys'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			err = err + "keys|";
		// check keysdisplay
		al = this.readDb("select count(*) AS res from pg_class where relname='keysdisplay'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			err = err + "keysdisplay|";
		// check mesa
		al = this.readDb("select count(*) AS res from pg_class where relname='mesa'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			err = err + "mesa|";
		// check terminal
		al = this.readDb("select count(*) AS res from pg_class where relname='terminal'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			err = err + "terminal|";

		// check sequence display
		al = this.readDb("select count(*) AS res from pg_class where relname='display_id_seq'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			errseq = errseq + "display|";
		// check sequence keys
		al = this.readDb("select count(*) AS res from pg_class where relname='keys_id_seq'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			errseq = errseq + "keys|";
		// check sequence mesa
		al = this.readDb("select count(*) AS res from pg_class where relname='mesa_id_seq'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			errseq = errseq + "mesa|";
		// check sequence terminal
		al = this.readDb("select count(*) AS res from pg_class where relname='terminal_id_seq'");
		if (al == null || al.get(0).getVal("res").equals("0"))
			errseq = errseq + "terminal|";

		String reserr = "";
		if (err.trim().length() > 0)
			reserr = reserr + "Problemas ao acessar tabelas: |" + err;
		if (errseq.trim().length() > 0)
			reserr = reserr + "Problemas ao acessar sequências: |" + errseq;

		if (reserr.trim().length() > 0) {
			this.resType = "E";
			this.resMsg = reserr;
			return false;
		} else {
			this.resType = "S";
			this.resMsg = "OK";
			return true;
		}
	}

	// ######################################################################################
	// Verify PERMISSIONS
	// ######################################################################################

	public boolean testPermissions() {
		this.resMsg = "";
		this.resType = "";
		String err = "";
		String errseq = "";

		// check display
		if (this.readDb(
				"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'display', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "display|";
		// check keys
		if (this.readDb(
				"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'keys', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "keys|";
		// check keysdisplay
		if (this.readDb(
				"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'keysdisplay', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "keysdisplay|";
		// check mesa
		if (this.readDb(
				"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'mesa', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "mesa|";
		// check terminal
		if (this.readDb(
				"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'terminal', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "terminal|";

		// ** Dont need if db-owner***
		// check sequence display
		// if (this.readDb(
		// "SELECT * FROM pg_tables WHERE has_table_privilege ('public',
		// 'display_id_seq', 'select') AND schemaname NOT IN ('pg_catalog',
		// 'information_schema')") == null)
		// errseq = errseq + "display|";
		// check sequence keys
		// if (this.readDb(
		// "SELECT * FROM pg_tables WHERE has_table_privilege ('public',
		// 'keys_id_seq', 'select') AND schemaname NOT IN ('pg_catalog',
		// 'information_schema')") == null)
		// errseq = errseq + "keys|";
		// check sequence mesa
		// if (this.readDb(
		// "SELECT * FROM pg_tables WHERE has_table_privilege ('public',
		// 'mesa_id_seq', 'select') AND schemaname NOT IN ('pg_catalog',
		// 'information_schema')") == null)
		// errseq = errseq + "mesa|";
		// check sequence terminal
		// if (this.readDb(
		// "SELECT * FROM pg_tables WHERE has_table_privilege ('public',
		// 'terminal_id_seq', 'select') AND schemaname NOT IN ('pg_catalog',
		// 'information_schema')") == null)
		// errseq = errseq + "terminal|";

		String reserr = "";
		if (err.trim().length() > 0)
			reserr = reserr + "Sem permissão para tabelas: |" + err;
		if (errseq.trim().length() > 0)
			reserr = reserr + "Sem permissão para sequências: |" + errseq;

		if (reserr.trim().length() > 0) {
			this.resType = "E";
			this.resMsg = reserr;
			return false;
		} else {
			this.resType = "S";
			this.resMsg = "OK";
			return true;
		}
	}

	// ######################################################################################
	// INITIAL CREATE
	// ######################################################################################

	public boolean createAll() {
		String roledb = "";
		String tabs = "";
		String seqs = "";
		this.resMsg = "";
		this.resType = "";
		
		// Role
		roledb = roledb + this.readSQLFile("roledg.sql");
		if(this.resType == "E")return false;
		// DB
		roledb = roledb + this.readSQLFile("dbcreate.sql");
		if(this.resType == "E")return false;
		// Table display
		tabs = tabs + this.readSQLFile("tbdisplay.sql");
		if(this.resType == "E")return false;
		// Table keys
		tabs = tabs + this.readSQLFile("tbkeys.sql");
		if(this.resType == "E")return false;
		// Table keysdisplay
		tabs = tabs + this.readSQLFile("tbkeysdisplay.sql");
		if(this.resType == "E")return false;
		// Table mesa
		tabs = tabs + this.readSQLFile("tbmesa.sql");
		if(this.resType == "E")return false;
		// Table terminal
		tabs = tabs + this.readSQLFile("tbterminal.sql");
		if(this.resType == "E")return false;
		// Seq display
		seqs = seqs + this.readSQLFile("seqdisplay.sql");
		if(this.resType == "E")return false;
		// Seq keys
		seqs = seqs + this.readSQLFile("seqkeys.sql");
		if(this.resType == "E")return false;
		// Seq mesa
		seqs = seqs + this.readSQLFile("seqmesa.sql");
		if(this.resType == "E")return false;
		// Seq terminal
		seqs = seqs + this.readSQLFile("seqterminal.sql");
		if(this.resType == "E")return false;
		
		if (this.updateDB(roledb)) {
			this.forceSetDB(null, null, "diguiche", "dg", "dg");
			if (this.updateDB(tabs)) {
				if (this.updateDB(seqs)) {
					return true;
				} else {
					this.resMsg = "ERRO: criação das sequências: " + this.resMsg;
				}

			} else {
				this.resMsg = "ERRO: criação das tabelas: " + this.resMsg;
			}
		} else {
			this.resMsg = "ERRO: criação do banco: " + this.resMsg;
		}
		return false;
	}

	
	// ######################################################################################
	// READ SQL FILES
	// ######################################################################################

	private String readSQLFile(String name) {
		String ret = "";
		try {
			// Seq Display SQL
			List<String> fl = Files.readAllLines(
					Paths.get(this.sconf.getServletContext().getRealPath("/configscripts/"+name) ),
					StandardCharsets.UTF_8);
			if (fl != null)
				for (String s : fl) {
					ret = ret + s;
				}
			return ret;
		} catch (Exception ex) {
		}
		this.resType= "E";
		this.resMsg = "ERRO: leitura do arquivo: " + name;
		return "";
	}

}
