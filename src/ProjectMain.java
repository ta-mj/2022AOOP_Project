package Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ProjectMain {
	public static void main(String[] args) throws SQLException{
	      HashMap<String, Continent> allContinent = new HashMap<>();
	      Connection con = DBConnect.makeConnection();
	      String sql = "SELECT * FROM data";
	      PreparedStatement pstmt = con.prepareStatement(sql);
	      ResultSet rs = pstmt.executeQuery();
	      while(rs.next()) {
	      	if(allContinent.containsKey(rs.getString(5))){
	      		int position = allContinent.get(rs.getString(5)).getCountryPosition(rs.getString(6));
	      		Airport a = new Airport(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(8));
	      		if(position != -1) {
	      			allContinent.get(rs.getString(5)).getOneCountry(position).setMyAirport(a);
	      		}
	      		else {
	      			Country ct = new Country(rs.getString(6),rs.getString(7));
	      			allContinent.get(rs.getString(5)).setMyCountry(ct);
	      			ct.setMyAirport(a);
	      		}
	      	}
	      	else {
	      		Continent cn = new Continent(rs.getString(5));
	      		Country ct = new Country(rs.getString(6),rs.getString(7));
	      		Airport a = new Airport(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(8));
	      		cn.setMyCountry(ct);
	      		ct.setMyAirport(a);
	      		allContinent.put(rs.getString(5), cn);
	      		
	      	}
	      }
	      Continent c = allContinent.get("유럽");
	      for(int i = 0 ; i < c.getAllCountries().size(); i++) {
	    	  System.out.println(c.getOneCountry(i).getKorName());
	      }
	}
}
