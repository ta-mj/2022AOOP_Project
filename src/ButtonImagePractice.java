import java.awt.*;
import javax.swing.*;

public class ButtonImagePractice extends JFrame {
    static JButton[] btnContinent;
    static JButton btnGraph;

    // 유럽 중동 아프리카 아시아 오세아니아 북아메리카 남아메리카 버튼 좌표 위치
    static String[] continentName;
    static int[][] location;
    private ImageIcon icon;
    private JScrollPane scrollPane;

    /*생성자*/
    public ButtonImagePractice() {
        setTitle("1");//타이틀
        setSize(1461, 799);//프레임의 크기
        setResizable(false);//창의 크기를 변경하지 못하게
        setLocationRelativeTo(null);//창이 가운데 나오게
        setLayout(null);//레이아웃을 내맘대로 설정가능하게 해줌.
        btnContinent = new JButton[7];
        btnGraph = new JButton();
        continentName = new String[]{"유럽","중동","아프리카","아시아","오세아니아","북아메리카","남아메리카"};
        location = new int[][]{{234, 222}, {269, 335}, {137, 421}, {449, 257}, {557, 586}, {983, 238}, {1142, 520}};
        icon = new ImageIcon(ProjectMain.class.getResource("./image/background.png"));
        //배경 Panel 생성후 컨텐츠페인으로 지정
        JPanel background = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        for(int i = 0 ; i < 7 ; i++){
            btnContinent[i] = new JButton();
            btnContinent[i].setLocation(location[i][0],location[i][1]);
            btnContinent[i].setBorderPainted(false);
            btnContinent[i].setSize(120, 60);
            btnContinent[i].setBackground(Color.white);
            btnContinent[i].setText(continentName[i]);
            background.add(btnContinent[i]);
        }
        add(background);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame이 정상적으로 종료되게
        setVisible(true);//창이 보이게
    }

    public static void main(String[] args){
        new ButtonImagePractice();
    }
}


