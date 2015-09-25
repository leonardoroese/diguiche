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

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

public abstract class ConBase {

	public String resType = null;
	public String resMsg = null;
	private ServletConfig scontext = null;

	private String dbHost = null;
	private String dbPort = null;
	private String dbName = null;
	private String dbUser = null;
	private String dbPass = null;

	public ConBase(ServletConfig sconf) {
		this.scontext = sconf;
		this.dbHost = sconf.getServletContext().getInitParameter("dbHost");
		this.dbPort = sconf.getServletContext().getInitParameter("dbPort");
		this.dbName = sconf.getServletContext().getInitParameter("dbName");
		this.dbUser = sconf.getServletContext().getInitParameter("dbUser");
		this.dbPass = sconf.getServletContext().getInitParameter("dbPass");

	}

	// ####################################################################
	// FORCE config db
	// ####################################################################
	public void forceSetDB(String host, String port, String name, String user, String pass){
		if(host != null)this.dbHost = host;
		if(port != null)this.dbPort = port;
		if(name != null)this.dbName = name;
		if(user != null)this.dbUser = user;
		if(pass != null)this.dbPass = pass;
	}
	
	
	// ####################################################################
	// TEST Connection
	// ####################################################################
	public boolean test() {
		try {
			Class.forName("org.postgresql.Driver");
			DriverManager.getConnection("jdbc:postgresql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName,
					this.dbUser, this.dbPass);
			return true;
		} catch (Exception e) {
			this.resType = "E";
			this.resMsg = e.getMessage();
			return false;
		}
	}

	// ####################################################################
	// INSERT DB (MYSQL)
	// ####################################################################
	public boolean updateDB(String query) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = (Connection) DriverManager.getConnection(
					"jdbc:postgresql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName, this.dbUser,
					this.dbPass);

			Statement stmt = (Statement) connection.createStatement();
			stmt.execute(query);
			connection.close();

			this.resType = "S";
			this.resMsg = "OperaÃ§Ã£o realizada";
			return true;

		} catch (Exception e) {
			this.resType = "E";
			this.resMsg = e.getMessage();
			return false;
		}
	}

	// ####################################################################
	// READ DB (MYSQL)
	// ####################################################################

	public ArrayList<DBLin> readDb(String query) {
		Connection connection = null;
		ResultSet rs = null;
		ArrayList<DBLin> outres = null;

		try {
			Class.forName("org.postgresql.Driver");
			connection = (Connection) DriverManager.getConnection(
					"jdbc:postgresql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName, this.dbUser,
					this.dbPass);
			Statement stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(query);
			if (rs != null) {
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
				while (rs.next()) {
					if (outres == null)
						outres = new ArrayList<DBLin>();
					DBLin lin = new DBLin();
					DBParVal[] par = new DBParVal[metaData.getColumnCount()];
					for (int i = 0; i < metaData.getColumnCount(); i++) {
						par[i] = new DBParVal();
						par[i].param = metaData.getColumnLabel(i + 1);
						switch (metaData.getColumnTypeName(i + 1).toUpperCase()) {
						case "VARCHAR":
							par[i].value = rs.getString(i + 1);
							break;
						case "DATE":
							if (rs.getDate(i + 1) != null)
								par[i].value = rs.getDate(i + 1).toString();
							break;
						case "DATETIME":
							par[i].value = "";
							try {
								java.sql.Timestamp timestamp = rs.getTimestamp(i + 1);
								if (timestamp != null)
									par[i].value = timestamp.toString();
							} catch (Exception ex2) {
								InputStream bin = rs.getBinaryStream(i + 1);
								if (bin != null)
									par[i].value = bin.toString();
							}

							break;
						case "INT":
							par[i].value = Integer.valueOf(rs.getInt(i + 1)).toString();
							break;
						case "BIGINT":
							par[i].value = Integer.valueOf(rs.getInt(i + 1)).toString();
							break;
						default:
							par[i].value = rs.getString(i + 1);
							break;
						}

					}
					lin.cols = par;
					outres.add(lin);
				}
			}
			connection.close();
			this.resType = "S";
			this.resMsg = "Operação realizada";
			return outres;
		} catch (Exception e) {
			this.resType = "E";
			this.resMsg = e.getMessage();
			return null;
		}
	}

	// ####################################################################
	// E-MAIL VALIDATION
	// ####################################################################
	public boolean validEmail(String email) {
		if (email == null || email.length() <= 0)
			return false;
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(EMAIL_REGEX);
	}

	// ####################################################################
	// FORMAT DATE SQL>>FORMAT
	// ####################################################################
	public String dateSQL2FORMAT(String dt) {
		String res = "";
		if (dt == null || dt.length() < 10)
			return null;
		res = dt.substring(8, 10) + "/" + dt.substring(5, 7) + "/" + dt.substring(0, 4);
		return res;
	}

	// ####################################################################
	// FORMAT DATE FORMAT>>SQL
	// ####################################################################
	public String dateFORMAT2SQL(String dt) {
		String res = "";
		if (dt == null || dt.length() < 10)
			return null;
		res = dt.substring(6, 10) + "-" + dt.substring(3, 5) + "-" + dt.substring(0, 2);
		return res;
	}

	// ####################################################################
	// CRIPTO MD5
	// ####################################################################
	public String toMD5(String val) {
		MessageDigest m;
		String outmd5 = null;
		if (val == null) {
			this.resType = "E";
			this.resMsg = "Ocorreu um problema com criptografia.";
			return null;
		}
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(val.getBytes(), 0, val.length());
			outmd5 = new BigInteger(1, m.digest()).toString(16);
			return outmd5;
		} catch (Exception e) {
			this.resType = "E";
			this.resMsg = "Ocorreu um problema com criptografia.";
			return null;
		}
	}

	// ####################################################################
	// SQL VALIDATE
	// ####################################################################
	public String sqlValidate(String sqlquery, boolean nohtml) {
		String outs = "";

		if (nohtml)
			outs = org.jsoup.Jsoup.parse(sqlquery).text();
		try {
			outs = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), sqlquery);
		} catch (Exception ex) {
			return null;
		}
		return outs;
	}
}
