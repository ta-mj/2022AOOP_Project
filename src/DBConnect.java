package Project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.PreparableStatement;

public class DBConnect {
    public static Connection makeConnection(){
        String url = "jdbc:mysql://localhost/projectdata?characterEncoding=UTF-8&serverTimezone=UTC";

        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("데이터베이스 연결 중...");
            con = DriverManager.getConnection(url,"root","ptwmju2199@");
            System.out.println("데이터베이스 연결 성공");
        } catch(ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        } catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
        }
        return con;
    }
//    public static void main(String[] args) throws SQLException{
//        Connection con = makeConnection();
//        String sql = "SELECT * FROM data";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        ResultSet rs = pstmt.executeQuery();
//        int i = 0;
//        while(rs.next()) {
//        	System.out.println(rs.getString(2));
//        	i++;
//        }
//        System.out.println(i);
//    }
}