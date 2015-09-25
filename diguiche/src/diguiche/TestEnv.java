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

import java.util.ArrayList;

import javax.servlet.ServletConfig;

public class TestEnv extends ConBase {

	public TestEnv(ServletConfig sconf) {
		super(sconf);
		// TODO Auto-generated constructor stub
	}

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

		//** Dont need if db-owner***
		// check sequence display
		//if (this.readDb(
		//		"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'display_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
		//	errseq = errseq + "display|";
		// check sequence keys
		//if (this.readDb(
		//		"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'keys_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
		//	errseq = errseq + "keys|";
		// check sequence mesa
		//if (this.readDb(
		//		"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'mesa_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
		//	errseq = errseq + "mesa|";
		// check sequence terminal
		//if (this.readDb(
		//		"SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'terminal_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
		//	errseq = errseq + "terminal|";

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

	public boolean createAll() {
		String q = "CREATE ROLE dg LOGIN" + "  ENCRYPTED PASSWORD 'md545427346a3dea51c7a10fe6e3584f267'"
				+ "  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;" + "CREATE DATABASE diguiche"
				+ "  WITH OWNER = dg" + "       ENCODING = 'UTF8'" + "       TABLESPACE = pg_default"
				+ "       LC_COLLATE = 'en_US.UTF-8'" + "       LC_CTYPE = 'en_US.UTF-8'"
				+ "       CONNECTION LIMIT = -1;" + "ALTER DEFAULT PRIVILEGES"
				+ "   GRANT INSERT, SELECT, UPDATE, DELETE, TRUNCATE, REFERENCES, TRIGGER ON TABLES" + "    TO public;"
				+ "ALTER DEFAULT PRIVILEGES"
				+ "    GRANT INSERT, SELECT, UPDATE, DELETE, TRUNCATE, REFERENCES, TRIGGER ON TABLES" + "    TO dg;";
		if (this.updateDB(q)) {
			this.forceSetDB(null,null, "diguiche", "dg", "dg");
			q = "CREATE TABLE display" + "(" + "  id serial NOT NULL," + "  num character varying(20),"
					+ "  code character varying(20)," + "  CONSTRAINT displayid PRIMARY KEY (id)" + ")" + "WITH ("
					+ "  OIDS=FALSE" + ");" + "ALTER TABLE display" + "  OWNER TO dg;"
					+ "GRANT ALL ON TABLE display TO public;" + "GRANT ALL ON TABLE display TO dg;" + ""
					+ "CREATE TABLE keys" + "(" + "  id serial NOT NULL," + "  mesa bigint NOT NULL,"
					+ "  tipo character varying(1)," + "  seq integer," + "  dtreg timestamp without time zone,"
					+ "  dtview timestamp without time zone," + "  viewer character varying(20),"
					+ "  CONSTRAINT senhaid PRIMARY KEY (id)" + ")" + "WITH (" + "OIDS=FALSE" + ");"
					+ "ALTER TABLE keys" + "  OWNER TO dg;" + "GRANT ALL ON TABLE keys TO public;"
					+ "GRANT ALL ON TABLE keys TO dg;" + "" + "CREATE TABLE keysdisplay" + "("
					+ "  idkey bigint NOT NULL," + "  iddisplay bigint NOT NULL,"
					+ "  dtview timestamp without time zone," + "CONSTRAINT kdispview PRIMARY KEY (idkey, iddisplay)"
					+ ")" + "WITH (" + " OIDS=FALSE" + ");" + "ALTER TABLE keysdisplay" + "  OWNER TO dg;"
					+ "GRANT ALL ON TABLE keysdisplay TO public;" + "GRANT ALL ON TABLE keysdisplay TO dg;"
					+ "CREATE TABLE mesa" + "(" + "  id serial NOT NULL," + "  num character varying(20),"
					+ "  code character varying(20)," + "  CONSTRAINT mesaid PRIMARY KEY (id)" + ")" + "WITH ("
					+ "  OIDS=FALSE" + ");" + "ALTER TABLE mesa" + "  OWNER TO dg;"
					+ "GRANT ALL ON TABLE mesa TO public;" + "GRANT ALL ON TABLE mesa TO dg;" + ""
					+ "CREATE TABLE terminal" + "(" + "  id serial NOT NULL," + "  num character varying(20),"
					+ "  code character varying(20)," + "  CONSTRAINT terminalid PRIMARY KEY (id)" + ")" + "WITH ("
					+ "  OIDS=FALSE" + ");" + "ALTER TABLE terminal" + "  OWNER TO dg;"
					+ "GRANT ALL ON TABLE terminal TO public;" + "GRANT ALL ON TABLE terminal TO dg;";
			if (this.updateDB(q)) {
				q = "CREATE SEQUENCE display_id_seq" + "  INCREMENT 1" + "  MINVALUE 1"
						+ "  MAXVALUE 9223372036854775807" + "  START 2" + "CACHE 1;" + "ALTER TABLE display_id_seq"
						+ "OWNER TO dg;" + "GRANT ALL ON SEQUENCE display_id_seq TO dg;"
						+ "GRANT ALL ON SEQUENCE display_id_seq TO public;" + "CREATE SEQUENCE keys_id_seq"
						+ "INCREMENT 1" + "MINVALUE 1" + "MAXVALUE 9223372036854775807" + "START 41" + "CACHE 1;"
						+ "ALTER TABLE keys_id_seq" + "OWNER TO dg;" + "GRANT ALL ON SEQUENCE keys_id_seq TO dg;"
						+ "GRANT ALL ON SEQUENCE keys_id_seq TO public;" + "CREATE SEQUENCE mesa_id_seq" + "INCREMENT 1"
						+ "MINVALUE 1" + "MAXVALUE 9223372036854775807" + "START 2" + "CACHE 1;"
						+ "ALTER TABLE mesa_id_seq" + "OWNER TO dg;" + "GRANT ALL ON SEQUENCE mesa_id_seq TO dg;"
						+ "GRANT ALL ON SEQUENCE mesa_id_seq TO public;" + "CREATE SEQUENCE terminal_id_seq"
						+ "  INCREMENT 1" + "  MINVALUE 1" + "MAXVALUE 9223372036854775807" + "START 5" + "CACHE 1;"
						+ "ALTER TABLE terminal_id_seq" + "OWNER TO dg;"
						+ "GRANT ALL ON SEQUENCE terminal_id_seq TO dg;"
						+ "GRANT ALL ON SEQUENCE terminal_id_seq TO public;";
					
				if (this.updateDB(q)) {
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

}
