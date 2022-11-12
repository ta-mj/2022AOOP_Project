import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class ProjectMain extends JFrame {
    static JButton[] btnContinent = new JButton[7];
    static JButton btnGraph = new JButton();

    // 유럽 중동 아프리카 아시아 오세아니아 북아메리카 남아메리카 버튼 좌표 위치
    static String[] continentName = new String[]{"유럽","중동","아프리카","아시아","오세아니아","북아메리카","남아메리카"};
    static int[][] location = new int[][]{{234, 222}, {269, 335}, {137, 421}, {449, 257}, {557, 586}, {983, 238}, {1142, 520}};
    private Image background = new ImageIcon(ProjectMain.class.getResource("./image/background.png")).getImage();//배경이미지

    /*생성자*/
    public ProjectMain() {
        homeframe();
    }

    public void homeframe() {
        setTitle("1");//타이틀
        setSize(1461, 799);//프레임의 크기
        setResizable(false);//창의 크기를 변경하지 못하게
        setLocationRelativeTo(null);//창이 가운데 나오게
        setLayout(null);//레이아웃을 내맘대로 설정가능하게 해줌.
        for (int i = 0; i < btnContinent.length; i++) {
            btnContinent[i] = new JButton();
            btnContinent[i].setLocation(location[i][0],location[i][1]);
            btnContinent[i].setBorderPainted(false);
            btnContinent[i].setSize(120, 60);
            btnContinent[i].setBackground(Color.white);
            btnContinent[i].setText(continentName[i]);
            add(btnContinent[i]);
        }
        btnGraph.setLocation(1340,690);
        btnGraph.setContentAreaFilled(false);
        btnGraph.setSize(100,60);
        btnGraph.setText("그래프");
        add(btnGraph);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame이 정상적으로 종료되게
        setVisible(true);//창이 보이게
    }
    public void paint(Graphics g) {//그리는 함수
        g.drawImage(background, 0, 0, null);//background를 그려줌
        paintComponents(g);

    }

    private static HashMap<String, Continent> allContinent = new HashMap<>();

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
        new ProjectMain();
    }
}


