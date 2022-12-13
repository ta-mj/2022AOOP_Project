import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ShowContinent extends JFrame {
    static JButton[] btnContinent = new JButton[8];
    // 유럽 중동 아프리카 아시아 오세아니아 북아메리카 남아메리카 버튼 좌표 위치
    static int[][] location = new int[][]{{174, 222}, {239, 345}, {100, 421}, {400, 120}, {507, 586}, {850, 200}, {1100, 500}, {0, 0}};
    static int[][] btnSize = new int[][]{{200, 120}, {160, 100}, {180, 200}, {250, 450}, {200, 120}, {350, 300}, {180, 250}, {100, 60}};
    static String[] btnName = new String[]{"유럽", "중동", "아프리카", "아시아", "대양주", "북미", "중남미", "그래프"};

    private Image background = new ImageIcon(ProjectMain.class.getResource("./image/background.jpg")).getImage();//배경이미지

    /*생성자*/
    public ShowContinent() {
        homeframe();
    }

    public void homeframe() {
        JPanel bg = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(background, 0, 0, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        setTitle("1");//타이틀
        setSize(1461, 799);//프레임의 크기
        setResizable(false);//창의 크기를 변경하지 못하게
        setLocationRelativeTo(null);//창이 가운데 나오게
        setLayout(null);//레이아웃을 내맘대로 설정가능하게 해줌.
        bg.setLayout(null);

        for (int i = 0; i < btnContinent.length; i++) {
            if(i == btnContinent.length - 1){
                btnContinent[i] = new RoundedButton();
            }
            else{
                btnContinent[i] = new JButton();

            }
            btnContinent[i].setBounds(location[i][0], location[i][1], btnSize[i][0], btnSize[i][1]);
            if (i == btnContinent.length - 1) {
                ImageIcon img = new ImageIcon("./image/graph.png");
                btnContinent[i].setIcon(img);
                btnContinent[i].setLocation(5,5);
                btnContinent[i].setRolloverIcon(img);
                btnContinent[i].setFont(new Font("고딕", Font.BOLD, 18));;
                btnContinent[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnContinent[i].setBackground(null);
                btnContinent[i].setText(btnName[i]);
                btnContinent[i].addActionListener(e -> {
                    new GraphFrame();
                    setVisible(false);
                });
                bg.add(btnContinent[i]);

                break;
            }
            btnContinent[i].setBorderPainted(false);
            btnContinent[i].setContentAreaFilled(false);
            btnContinent[i].setFocusPainted(false);
            btnContinent[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            int finalI = i;
            btnContinent[i].addActionListener(e -> {
                try {
                    new MainFrame(btnName[finalI]);
                    setVisible(false);
                } catch (IOException ioException) {
                    System.out.println(ioException);
                }
            });
            bg.add(btnContinent[i]);

        }
        setContentPane(bg);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
