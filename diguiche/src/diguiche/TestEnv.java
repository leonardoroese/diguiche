package diguiche;

import javax.servlet.ServletConfig;

public class TestEnv extends ConBase {

	public TestEnv(ServletConfig sconf) {
		super(sconf);
		// TODO Auto-generated constructor stub
	}

	public boolean testTables() {
		String err = "";
		String errseq = "";
		this.resMsg = "";
		this.resType = "";

		// check display
		if (this.readDb("select count(*) from pg_class where relname='display'") == null)
			err = err + "display|";
		// check keys
		if (this.readDb("select count(*) from pg_class where relname='keys'") == null)
			err = err + "keys|";
		// check keysdisplay
		if (this.readDb("select count(*) from pg_class where relname='keysdisplay'") == null)
			err = err + "keysdisplay|";
		// check mesa
		if (this.readDb("select count(*) from pg_class where relname='mesa'") == null)
			err = err + "mesa|";
		// check terminal
		if (this.readDb("select count(*) from pg_class where relname='terminal'") == null)
			err = err + "terminal|";

		// check sequence display
		if (this.readDb("select count(*) from pg_class where relname='display_id_seq'") == null)
			errseq = errseq + "display|";
		// check sequence keys
		if (this.readDb("select count(*) from pg_class where relname='keys_id_seq'") == null)
			errseq = errseq + "keys|";
		// check sequence mesa
		if (this.readDb("select count(*) from pg_class where relname='mesa_id_seq'") == null)
			errseq = errseq + "mesa|";
		// check sequence terminal
		if (this.readDb("select count(*) from pg_class where relname='terminal_id_seq'") == null)
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
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'display', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "display|";
		// check keys
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'keys', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "keys|";
		// check keysdisplay
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'keysdisplay', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "keysdisplay|";
		// check mesa
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'mesa', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "mesa|";
		// check terminal
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'terminal', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			err = err + "terminal|";

		// check sequence display
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'display_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			errseq = errseq + "display|";
		// check sequence keys
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'kesy_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			errseq = errseq + "keys|";
		// check sequence mesa
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'mesa_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			errseq = errseq + "mesa|";
		// check sequence terminal
		if (this.readDb("SELECT * FROM pg_tables WHERE has_table_privilege ('public', 'terminal_id_seq', 'select') AND schemaname NOT IN ('pg_catalog', 'information_schema')") == null)
			errseq = errseq + "terminal|";

		
		
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
}
