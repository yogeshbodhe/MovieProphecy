package mp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class WriteToDB {

	public static void writeData(ArrayList<String> textData) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;port=1433;user=ybodhe;password=team6;database=MPDB");
		//System.out.println("test");
		Statement stmt = conn.createStatement();
		String sql = new String();
		
		for(int i=0;i<textData.size();i++){
			textData.set(i, textData.get(i).replaceAll("\'", ""));
		}
		
		String datetime = textData.get(1);
		String arr[] = datetime.split(" ");
		String year = arr[5];
		String month = arr[1];
		String mon = new String();
		
		switch(month){
		case "Jan":mon="01";break;
		case "Feb":mon="02";break;
		case "Mar":mon="03";break;
		case "Apr":mon="04";break;
		case "May":mon="05";break;
		case "Jun":mon="06";break;
		case "Jul":mon="07";break;
		case "Aug":mon="08";break;
		case "Sep":mon="09";break;
		case "Oct":mon="10";break;
		case "Nov":mon="11";break;
		case "Dec":mon="12";break;
		}
		
		String date = arr[2];
		String time = arr[3];
		
		String finalDatetime = year + "-" + mon + "-" + date + "T" + time;
                //String finalDatetime = "2012-12-12";
		
		if(textData.size()==6){
			//textData.get(3).replaceAll("'", " ");
			sql = "INSERT INTO MPDB.dbo.MP_Table2 VALUES('"+ textData.get(0) +"', '"+ finalDatetime +"', '"+ textData.get(2) +"', '"+ textData.get(3) +"', '"+ textData.get(4) +"','"+ textData.get(5) +"');";
		}
		else{
			//textData.get(2).replaceAll("'", " ");
			sql = "INSERT INTO MPDB.dbo.MP_Table2 VALUES('"+ textData.get(0) +"', '"+ finalDatetime +"', 'null', '"+ textData.get(2) +"', '"+ textData.get(3) +"','"+ textData.get(4) +"');";
		}
			stmt.executeUpdate(sql);
		conn.close();
	}	

}
