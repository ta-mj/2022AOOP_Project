
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame { //메인 프레임
    public MainFrame(String title) throws IOException {
        super(title);                            //타이틀
        setDefaultCloseOperation(EXIT_ON_CLOSE); //끄기버튼 활성화
        setSize(1000, 600);          //크기
        setLayout(new GridLayout());
        add(new ConPanel());
        add(new LookUpPanel());
        setVisible(true);
    }
    class ConPanel extends JPanel {
        public ConPanel() throws IOException { //대륙 사진 판넬
            //File file = new File("C:/Users/우성/Desktop/고급객체/AOP/2022AOOP_Project/public/test.png"); //사진 경로
            //BufferedImage img = ImageIO.read(file); //가상에 이미지 로드
            //JLabel lb = new JLabel(new ImageIcon(img)); //라벨에 추가
            //add(lb);                                    //라벨 추가
            this.setSize(100, 200);
            this.setLocation(200,200);
            //this.setVisible(true);
        }
    }

//    public static void main(String[] args) throws IOException {
//        MainFrame frame = new MainFrame("그림판");
//        frame.setVisible(true);
//    }
}

class LookUpPanel extends JPanel{ //검색, 정렬, 조회 통합 판넬
    public LookUpPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //세로 박스정렬
        add(new SearchPanel());
        add(new BottomPanel());
    }
}
class SearchPanel extends JPanel{ //검색 판넬
    public SearchPanel(){
        add(new JTextField(20));
        add(new JButton("search"));
    }
}

class BottomPanel extends JPanel{ //검색 하단 판넬
    public BottomPanel(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); //가로박스정렬
        add(new JScrollPane(new ConList() ) );                  //대륙, 국가 리스트 스크롤 가능하게 추가
        String[] countrys = {"한국", "일본", "중국"};
        add(new JScrollPane(new CountryList(countrys) ) );
        add(new InfoPanel());
    }
}
class CountryList extends JList{ //나라 리스트
    String[] myCountrys; //나중에 대륙 선택에 따라 받아올 국가명 문자열 배열
    public CountryList(String[] countrys){
        super(countrys);
        this.myCountrys = countrys;
    }
}
class ConList extends JList{ //대륙 리스트
    public static String[] cons = {"유럽", "중동", "아시아", "아프리카", "오세아니아", "북아메리카", "남아메리카"};
    public ConList(){
        super(cons);
    }
}

class InfoPanel extends Panel{
    Airport myAirport; //생성자 함수에 Airport 정보 받는 걸로 후에 수정
    public InfoPanel(){
        JRadioButton  allRBtn = new JRadioButton("전체"); //JRadioButton 생성
        JRadioButton  interRBtn = new JRadioButton("국제");
        JRadioButton  domRBtn = new JRadioButton("국내");
        ButtonGroup  rGroup = new ButtonGroup();
        rGroup.add(allRBtn);
        rGroup.add(interRBtn);
        rGroup.add(domRBtn);
        add(allRBtn);
        add(interRBtn);
        add(domRBtn);

    }

    //그룹에 그룹화시킬 버튼들을 추가
}