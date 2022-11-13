
import sun.applet.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MainFrame extends JFrame { //메인 프레임
    public static String conName;

    public MainFrame(String title) throws IOException {
        super(title);//타이틀
        conName = title;
        ProjectMain.setSelectedContinent(ProjectMain.getContinent(title));
        setDefaultCloseOperation(EXIT_ON_CLOSE); //끄기버튼 활성화
        setSize(1000, 650);          //크기
        setLayout(null);
        setLocationRelativeTo(null);//창이 가운데 나오게
        ConPanel c = new ConPanel(conName);
        c.setSize(500, 550);
        c.setLocation(0, 35);
        add(c);
        LookUpPanel l = new LookUpPanel(ProjectMain.getSelectedContinent());
        l.setSize(450, 630);
        l.setLocation(510, 0);
        add(l);
        JButton btnBack = new JButton();
        btnBack.setText("뒤로가기");
        btnBack.setLocation(10, 5);
        btnBack.setSize(90, 26);
        btnBack.addActionListener(e -> {
            new ShowContinent();
            setVisible(false);
        });
        add(btnBack);
        setVisible(true);
    }



}

class ConPanel extends JPanel {
    public static String conName;
    public ConPanel(String cont) throws IOException { //대륙 사진 판넬
        conName = cont;
        showConPanel();
    }
    public void showConPanel(){
        setVisible(false);
        Image img = new ImageIcon(MainFrame.class.getResource("./image/" + conName + ".png")).getImage();//배경이미지
        JLabel lb = new JLabel(new ImageIcon(img)); //라벨에 추가
        add(lb);                                    //라벨 추가
        setSize(100, 200);
        setLocation(200, 200);
        setVisible(true);
    }

}
class LookUpPanel extends JPanel { //검색, 정렬, 조회 통합 판넬
    public LookUpPanel(Continent c) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //세로 박스정렬
        add(new SearchPanel());
        add(new BottomPanel(c));
    }
}

class SearchPanel extends JPanel { //검색 판넬
    static String[] cons = {"유럽", "중동", "아시아", "아프리카", "대양주", "북미", "남미"};

    public SearchPanel() {
        HashMap<String, Continent> hm = ProjectMain.allContinent;
        JTextField textField = new JTextField(20);
        JButton btnSearch = new JButton("search");
        textField.addActionListener(e -> {
            boolean findKey = false;
            String str = textField.getText();
            textField.setText("");
            //System.out.println(hm.get(str).getName());
            if (str != null) {
                if (Arrays.asList(cons).contains(str)) {
                    if (str.equals(hm.get(str).getName())) { //대륙 이름을 입력했냐?
                        System.out.println(str + " 대륙 이름임");
                        findKey = true;
                        //new ConList(); //ConList에서 str이 선택된다는 가정
                    }
                } else { // 나라 이름이냐
                    for (String element : cons) { // 대륙을 돌면서 나라를 찾음
                        if (findKey) break;
                        for (Country c : hm.get(element).getAllCountries()) {
                            if (findKey) break;
                            if (str.equals(c.getKorName())) {
                                System.out.println(str + " 나라 이름임");
                                findKey = true;
                                // 나라 선택된다는 가정
                            } else {
                                if (hm.get(element).getmyCountry(c) != null) {
                                    for (Airport a : hm.get(element).getmyCountry(c).getAllAirport()) { // 공항 코드 등 정보 입력했을 때도 if문 나눠서 검색가능
                                        if (findKey) break;
                                        if (str.equals(a.getKorName())) {
                                            System.out.println(str + " 공항 이름임");
                                            findKey = true;
                                            //공항 선택된다는 가정
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!findKey) {
                    System.out.println("정보를 찾지 못했습니다");
                }
            }
        });
        btnSearch.addActionListener(e -> {
            String str = textField.getText();
            System.out.println(str);
        });
        add(textField);
        add(btnSearch);
    }
}

class BottomPanel extends JPanel { //검색 하단 판넬
    public BottomPanel(Continent c) {
        setVisible(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); //가로박스정렬
        add(new JScrollPane(new ConList(c)));                  //대륙, 국가 리스트 스크롤 가능하게 추가
        String[] countrys = new String[c.getAllCountries().size()];
        for (int i = 0; i < c.getAllCountries().size(); i++) {
            countrys[i] = c.getOneCountry(i).getKorName();
        }
        HashSet<String> hashSet = new HashSet<>(Arrays.asList(countrys)); // 중복 제거용 set 선언

        String[] resultArr = hashSet.toArray(new String[0]);
        Arrays.sort(resultArr); //나라 정렬
        add(new JScrollPane(new CountryList(resultArr)));
        add(new InfoPanel());

        SwingUtilities.updateComponentTreeUI(this);
        setVisible(true);
    }
}

class CountryList extends JList { //나라 리스트
    String[] myCountrys; //나중에 대륙 선택에 따라 받아올 국가명 문자열 배열

    public CountryList(String[] countrys) {
        super(countrys);
        this.myCountrys = countrys;
    }
}

class ConList extends JList implements ListSelectionListener { //대륙 리스트
    public static String[] cons = {"유럽", "중동", "아시아", "아프리카", "대양주", "북미", "남미"};
    String currentconName;
    Continent currentCont;
    private int index;
    public ConList(Continent c) {
        super(cons);
        currentCont = c;
        currentconName = c.getName();
        addListSelectionListener(this);
        this.setLayout(new BorderLayout());


//        addListSelectionListener(e -> {
//            if (e.getValueIsAdjusting()) { //마우스 눌렀을 때
//                currentCont = this.getSelectedValue().toString();
//                MainFrame.conName = currentCont;
//            }
//        });
    }
// BottomPanel : 검색 하단 패널 일단 여기 나라 리스트 들어가있음, ConPanel : 대륙 이미지, ConList : 대륙 리스트
    @Override
    public void valueChanged(ListSelectionEvent e) { // 대륙 리스트 클릭시 대륙 이미지 변화 및 나라 리스트 변화, 각 Panel들을 다시 불러오면 될듯한데 왜 안되징
        if (!e.getValueIsAdjusting()) {    //이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
            System.out.println("selected :" + this.getSelectedValue());
            currentconName = this.getSelectedValue().toString();
            ProjectMain.setSelectedContinent(ProjectMain.getContinent(currentconName));
            currentCont = ProjectMain.getSelectedContinent();
            this.index = Arrays.asList(cons).indexOf(currentconName);
            setSelectedIndex(this.index);
            //imageUpdate(new ImageIcon(MainFrame.class.getResource("./image/" + currentconName + ".png")).getImage(),0,0,35,500,550);
            new BottomPanel(currentCont);
            try {
                new ConPanel(currentconName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}

class AirportList extends JList {
    String[] myAirport;

    public AirportList(String[] airports) {
        super(airports);
        this.myAirport = airports;
    }
}

class InfoPanel extends Panel {
    Airport myAirport; //생성자 함수에 Airport 정보 받는 걸로 후에 수정

    public InfoPanel() {
        JRadioButton allRBtn = new JRadioButton("전체"); //JRadioButton 생성
        JRadioButton interRBtn = new JRadioButton("국제");
        JRadioButton domRBtn = new JRadioButton("국내");
        ButtonGroup rGroup = new ButtonGroup();
        rGroup.add(allRBtn);
        rGroup.add(interRBtn);
        rGroup.add(domRBtn);
        add(allRBtn);
        add(interRBtn);
        add(domRBtn);

    }

    //그룹에 그룹화시킬 버튼들을 추가
}