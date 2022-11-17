import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphByCountry extends JDialog {
    String title;
    Continent myContinent;
    ArrayList<Country> myCountrys; //대륙별 국가 리스트
    Color barColor;
    static Color colorList[] = {Color.LIGHT_GRAY, Color.magenta, Color.orange, Color.PINK, Color.red, Color.YELLOW, Color.blue, Color.GREEN};
    Integer[] numOfAirport = new Integer[7]; //top7 국가별 공항 개수
    String[] topCountry = new String[7]; //top7 국가 한국 이름
    Integer topNumAp;
    int barStartX = 150; //첫번째 버튼이 시작하는 X좌표
    int barStartY = 800; //버튼이 그려지는 Y좌표
    int barWidth = 70;  //막대 너비
    int distance = 200; //막대 간격
    RoundedButton exitBtn;
    public GraphByCountry (JFrame frame,String title, Continent myContinent, Color barColor){
        super(frame, title);
        this.setLayout(null);
        setLocationRelativeTo(null);
        this.barColor = barColor;
        exitBtn = new RoundedButton("X");
        exitBtn.setSize(50,50);
        exitBtn.setLocation(1450, 0);
        exitBtn.setBackground(Color.RED);
        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getSource().equals(exitBtn)){
                    dispose();
                }
            }
        });
        add(exitBtn);
        this.setUndecorated(true);
        this.myContinent = myContinent;
        this.myCountrys = myContinent.getAllCountries();
        this.myCountrys.sort((Country a, Country b) -> b.getNumAirport()-a.getNumAirport()); //내림차순 정렬
        this.title = title+" 국가별 공항 개수";
        if(this.myCountrys.size() >= 7) this.title += " (TOP7)";
        for(int i = 0; i<7 && i<myCountrys.size(); i++){
            this.numOfAirport[i] = myCountrys.get(i).getNumAirport();
            this.topCountry[i] = myCountrys.get(i).getKorName();
        }

        RoundedButton[] conBtn = new RoundedButton[7];
        Font btnFont = new Font("맑은 고딕", Font.BOLD, 20 );
        for(int i = 0; i<7 &&i<myCountrys.size(); i++){ //버튼 생성 및 Panel에 추가
            conBtn[i] = new RoundedButton(this.topCountry[i]);
            conBtn[i].setLocation(barStartX+distance*i-50, barStartY+50);
            conBtn[i].setSize(180,50);
            conBtn[i].setBackground(Color.darkGray);
            conBtn[i].setForeground(Color.white);
            conBtn[i].setFont(btnFont);
            add(conBtn[i]);
        }

        setSize(1500, 900);
        setVisible(true);
    }
    public void getTopAirport(){

    }
    public void paint(Graphics g) {
        super.paint(g);
        Font titleFont = new Font("맑은 고딕", Font.BOLD, 50 );
        g.setFont(titleFont);
        g.drawString(title, 400, 110);
        int ratio = 5;
        if(super.getTitle().equals("북미")) ratio = 3;
        for(int i = 0 ; i<7 &&i<myCountrys.size() ; i++){
            g.setColor(barColor);
            g.fillRect(+barStartX + distance*i, barStartY -numOfAirport[i]*ratio,barWidth, numOfAirport[i]*ratio);
            g.setColor(Color.black);
            Font countFont = new Font("맑은 고딕", Font.ITALIC, 25 );
            g.setFont(countFont);
            g.drawString(numOfAirport[i].toString(),barStartX+10 + distance*i, barStartY -numOfAirport[i]*ratio );
        }
    }
}
