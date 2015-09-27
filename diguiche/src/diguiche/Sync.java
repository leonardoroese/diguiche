package diguiche;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletConfig;

public class Sync extends ConBase{

	public Sync(ServletConfig sconf) {
		super(sconf);
		// TODO Auto-generated constructor stub
	}

	public void syncDevice(String id , String tipo, String ip){
		
		if(id == null || ip == null)
			return;
		
		String q = "SELECT * FROM sync WHERE tipo = '"+tipo+"' AND  devid = " + id;
		ArrayList<DBLin> al = null;
		
		al = this.readDb(q);
		if(al != null){
			q = "UPDATE sync SET dtsync = CURRENT_TIMESTAMP, ip = '"+ip+"' WHERE tipo = '"+tipo+"' AND devid = "+id;
			this.updateDB(q);
		}else{
			q = "INSERT INTO sync (tipo, devid, dtsync, ip) VALUES ('"+tipo+"',"+id+", CURRENT_TIMESTAMP, '"+ip+"')";
			this.updateDB(q);
		}
		return;
	}
	
	
	public String isOnline(String id , String tipo){
		if(id == null || id.trim().length() <= 0)
			return null;
		String q = "SELECT * FROM sync WHERE tipo = '" + tipo + "' AND devid = " + id;
		ArrayList<DBLin> al = null;
		q = "SELECT * FROM Sync WHERE tipo = '"+tipo+"' AND devid = " + id;
		al = this.readDb(q);
		if(al != null){
			Timestamp t = new Timestamp(new Date().getTime());
			Timestamp t2 = Timestamp.valueOf(al.get(0).getVal("dtsync"));
			if(((t.getTime() - t2.getTime()) / 1000) < 30)
				return al.get(0).getVal("dtsync");
		}
		return null;
	}
}
