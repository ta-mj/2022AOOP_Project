
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MainFrame extends JFrame { //메인 프레임
    private static String currentconName;
    private static Continent currentCont;
    public static String[] cons = {"유럽", "중동", "아시아", "아프리카", "대양주", "북미", "중남미"};

    public static HashMap<String, Image> conImage;
    private ConPanel continentImagePanel;

    private static ConList continentList;
    private static CountryList countryList;
    private static AirportList airportList;

    private static Image img;
    private static JLabel lb;
    private static DefaultListModel<String> model = new DefaultListModel<>();

    public MainFrame(String title) throws IOException {
        super("공항 통항 검색");//타이틀
        currentconName = title;
        setImage();
        ProjectMain.setSelectedContinent(ProjectMain.getContinent(title));
        setDefaultCloseOperation(EXIT_ON_CLOSE); //끄기버튼 활성화
        setSize(1050, 650);          //크기
        setLayout(null);
        setLocationRelativeTo(null);//창이 가운데 나오게
        setResizable(false);
        //List 설정
        continentList = new ConList(ProjectMain.getSelectedContinent());
        countryList = new CountryList(ProjectMain.getSelectedContinent());
        airportList = new AirportList();
        //Panel 설정
        continentImagePanel = new ConPanel(currentconName);
        continentImagePanel.setSize(500, 550);
        continentImagePanel.setLocation(0, 35);
        add(continentImagePanel);
        LookUpPanel l = new LookUpPanel(ProjectMain.getSelectedContinent());
        l.setSize(550, 630);
        l.setLocation(510, 0);
        add(l);
        //버튼 설정
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

    public void setImage() {
        conImage = new HashMap<>();
        for (int i = 0; i < cons.length; i++) {
            System.out.println(cons[i]);
            conImage.put(cons[i], new ImageIcon(MainFrame.class.getResource("./image/" + cons[i] + ".png")).getImage());
        }
    }


    class ConPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.drawImage(conImage.get(currentconName), 0, 0, null);
        }

        public ConPanel(String cont) throws IOException { //대륙 사진 판넬
            currentconName = cont;
            showConPanel();
        }

        public void showConPanel() {
            setVisible(false);
            img = conImage.get(currentconName);
            lb = new JLabel(new ImageIcon(img)); //라벨에 추가
            add(lb);                                    //라벨 추가
            setSize(100, 200);
            setLocation(200, 200);
            setVisible(true);
        }

    }

    class LookUpPanel extends JPanel { //검색, 정렬, 조회 통합 판넬
        public LookUpPanel(Continent c) {
//            setLayout(null); //세로 박스정렬
            SearchPanel searchPanel = new SearchPanel();

            add(searchPanel);
            InfoPanel infoPanel = new InfoPanel();
            add(infoPanel);
            BottomPanel bottomPanel = new BottomPanel(c);
            add(bottomPanel);
            setVisible(true);
        }
    }

    class SearchPanel extends JPanel { //검색 판넬
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
                            continentList.setSelectedValue(str,true);
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
            JScrollPane contL = new JScrollPane(continentList);
            JScrollPane countryL = new JScrollPane(countryList);
            JScrollPane apL = new JScrollPane(airportList);
            Dimension contD = continentList.getPreferredSize();
            contD.width = 80;
            contD.height = 500;
            contL.setPreferredSize(contD);
            Dimension countryD = continentList.getPreferredSize();
            countryD.width = 150;
            countryD.height = 500;
            countryL.setPreferredSize(countryD);
            Dimension aD = continentList.getPreferredSize();
            aD.width = 250;
            aD.height = 500;
            apL.setPreferredSize(aD);
            add(contL);                  //대륙, 국가 리스트 스크롤 가능하게 추가
            countryList.setCountryList(c);
            add(countryL);
            add(apL);

            setVisible(true);
        }
    }

    class ConList extends JList implements ListSelectionListener { //대륙 리스트
        public ConList(Continent c) {
            super(cons);
            currentCont = c;
            currentconName = c.getName();
            addListSelectionListener(this);
            this.setLayout(new BorderLayout());
        }

        // BottomPanel : 검색 하단 패널 일단 여기 나라 리스트 들어가있음, ConPanel : 대륙 이미지, ConList : 대륙 리스트
        @Override
        public void valueChanged(ListSelectionEvent e) { // 대륙 리스트 클릭시 대륙 이미지 변화 및 나라 리스트 변화, 각 Panel들을 다시 불러오면 될듯한데 왜 안되징
            if (!e.getValueIsAdjusting()) {    //이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
                //나라 및 공항 리스트 초기화
                countryList.countryName.removeAllElements();
                countryList.setModel(countryList.countryName);
                airportList.airportName.removeAllElements();
                airportList.setModel(airportList.airportName);

                System.out.println("selected :" + this.getSelectedValue());
                currentconName = this.getSelectedValue().toString();
                System.out.println(currentconName);
                ProjectMain.setSelectedContinent(ProjectMain.getContinent(currentconName));
                currentCont = ProjectMain.getSelectedContinent();
                continentImagePanel.repaint();
                countryList.setCountryList(currentCont);
            }
        }
    }

    class CountryList extends JList implements ListSelectionListener { //나라 리스트
        DefaultListModel<String> countryName;

        public CountryList(Continent c) {
            super();
            countryName = new DefaultListModel<>();
            setCountryList(c);
            addListSelectionListener(this);
            this.setLayout(new BorderLayout());
        }

        @Override
        public void valueChanged(ListSelectionEvent e) { // 나라 리스트 선택 시 공항 리스트 불러오기
            if (!e.getValueIsAdjusting()) {    //이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
                System.out.println("selected :" + this.getSelectedValue());
                if (this.getSelectedValue() != null) {
                    String countryStr = this.getSelectedValue().toString();
                    System.out.println(countryStr);
                    for (int i = 0; i < ProjectMain.getSelectedContinent().getAllCountries().size(); i++) {
                        if (ProjectMain.getSelectedContinent().getOneCountry(i).getKorName().equals(countryStr)) {
                            ProjectMain.setSelectedCountry(ProjectMain.getSelectedContinent().getOneCountry(i));
                            System.out.println(ProjectMain.getSelectedCountry().getKorName());
                            break;
                        } else {

                        }
                    }
                    airportList.setAirportList(ProjectMain.getSelectedCountry());
                }

            }
        }

        public void setCountryList(Continent c) {
            countryName.removeAllElements();
            String[] country = new String[c.getAllCountries().size()];
            for (int i = 0; i < c.getAllCountries().size(); i++) {
                country[i] = c.getOneCountry(i).getKorName();
            }
            Arrays.sort(country); //나라 정렬
            for (int i = 0; i < country.length; i++) {
                countryName.addElement(country[i]);
            }
            setModel(countryName);
        }
    }

    class AirportList extends JList implements ListSelectionListener {
        DefaultListModel<String> airportName;

        public AirportList() {
            super();
            airportName = new DefaultListModel<>();
        }

        public void setAirportList(Country c) {
            airportName.removeAllElements();
            String[] airport = new String[c.getAllAirport().size()];
            for (int i = 0; i < c.getAllAirport().size(); i++) {
                airport[i] = c.getOneAirport(i).getKorName();
            }
            Arrays.sort(airport); //나라 정렬
            for (int i = 0; i < airport.length; i++) {
                airportName.addElement(airport[i]);
            }
            setModel(airportName);
        }

        @Override
        public void valueChanged(ListSelectionEvent e) { // 대륙 리스트 클릭시 대륙 이미지 변화 및 나라 리스트 변화, 각 Panel들을 다시 불러오면 될듯한데 왜 안되징
            if (!e.getValueIsAdjusting()) {    //이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
                System.out.println("selected :" + this.getSelectedValue());
                currentconName = this.getSelectedValue().toString();
                System.out.println(currentconName);
            }
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

}
