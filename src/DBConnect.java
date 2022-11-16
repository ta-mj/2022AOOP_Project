import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {
    public static Connection makeConnection(){
        String url = "jdbc:mysql://127.0.0.1:3306/projectdata?characterEncoding=UTF-8&serverTimezone=UTC";
        //String url = "jdbc:mysql://127.0.0.1:3306";

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
}
