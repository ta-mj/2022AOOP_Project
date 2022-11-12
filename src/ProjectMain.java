import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class ProjectMain extends JFrame {
    private static HashMap<String, Continent> allContinent = new HashMap<>();
    private static Continent selectedContinent;
    private static Country selectedCountry;
    private static Airport selectedAirport;
    public static void setSelectedContinent(Continent c){ selectedContinent = c; }
    public static void setSelectedCountry(Country c){ selectedCountry = c; }
    public static void setSelectedAirport(Airport a){ selectedAirport = a; }
    public static Continent getContinent(String s){ return allContinent.get(s); }
    public static Continent getSelectedContinent(){ return selectedContinent; }
    public static Country getSelectedCountry(){ return selectedCountry; }
    public static Airport getSelectedAirport(){ return selectedAirport; }

    public static void getData() throws SQLException {
        Connection con = DBConnect.makeConnection();
        String sql = "SELECT * FROM data";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            if (allContinent.containsKey(rs.getString(5))) {
                int position = allContinent.get(rs.getString(5)).getCountryPosition(rs.getString(6));
                Airport a = new Airport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(8));
                if (position != -1) {
                    allContinent.get(rs.getString(5)).getOneCountry(position).setMyAirport(a);
                } else {
                    Country ct = new Country(rs.getString(6), rs.getString(7));
                    allContinent.get(rs.getString(5)).setMyCountry(ct);
                    ct.setMyAirport(a);
                }
            } else {
                Continent cn = new Continent(rs.getString(5));
                Country ct = new Country(rs.getString(6), rs.getString(7));
                Airport a = new Airport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(8));
                cn.setMyCountry(ct);
                ct.setMyAirport(a);
                allContinent.put(rs.getString(5), cn);

            }
        }
    }

    public static void main(String[] args) throws SQLException {
        getData();
        new ShowContinent();
    }
}


