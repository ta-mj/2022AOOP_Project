
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MainFrame extends JFrame { //메인 프레임
    private static String currentconName;
    private static Continent currentCont;
    private static Country currentCountry;
    private static Airport currentAirport;
    //국제 / 국내 공항 radio 버튼 변수
    JRadioButton allRBtn = new JRadioButton("전체"); //JRadioButton 생성
    JRadioButton interRBtn = new JRadioButton("국제");
    JRadioButton domRBtn = new JRadioButton("국내");


    //국제 / 국내 공항 radio 버튼 관련 상수
    private final int all = 0;
    private final int international = 1;
    private final int domestic = 2;
    //국제 / 국내 공항 상태
    private int inter_all_dom_status = all;
    public static String[] cons = {"유럽", "중동", "아시아", "아프리카", "대양주", "북미", "중남미"};

    public static HashMap<String, Image> conImage;
    private ConPanel continentImagePanel;

    private static ConList continentList;
    private static CountryList countryList;
    private static AirportList airportList;

    private static Image img;
    private static JLabel lb;

    public MainFrame(String title) throws IOException {
        super("공항 통합 검색");//타이틀
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
            airportList.airportName.removeAllElements();
        });
        add(btnBack);

        // 다 만들어지면 대륙 클릭시 초기 MainFrame에서 처음 대륙리스트 클릭되게 만듦
        continentList.setSelectedValue(currentconName, true);
        setVisible(true);
    }

    public void setImage() {
        conImage = new HashMap<>();
        for (int i = 0; i < cons.length; i++) {
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
            SearchPanel searchPanel = new SearchPanel();
            searchPanel.setLocation(510, 5);
            searchPanel.setSize(50, 50);
            add(searchPanel);
            InfoPanel infoPanel = new InfoPanel();
            add(infoPanel);
            BottomPanel bottomPanel = new BottomPanel(c);
            add(bottomPanel);
            setVisible(true);
        }
    }

    class SearchPanel extends JPanel { //검색 판넬
        private HashMap<String, Continent> hm = ProjectMain.allContinent;
        private JTextField textField = new JTextField(20);

        private DefaultListModel<String> model = new DefaultListModel<>();
        private JButton btnSearch = new JButton("search");

        public SearchPanel() {
            textField.addActionListener(e -> { //텍스트 필드 엔터쳤을때 액션함수
                totalSearch(textField.getText());
//                searchInfo();
            });
            btnSearch.addActionListener(e -> { //버튼 클릭 액션함수
                totalSearch(textField.getText());
                //searchInfo();
            });
            add(textField);
            add(btnSearch);
        }

        public void totalSearch(String s) {
            final boolean[] findKey = {false};
            final boolean[] equalKey = {false};
            DefaultListModel<String> continentResult = new DefaultListModel<>();
            DefaultListModel<String> countryResult = new DefaultListModel<>();
            DefaultListModel<String> airportResult = new DefaultListModel<>();


            ProjectMain.allContinent.forEach((key, value) -> {
                if (s.equals("")) return;
                Continent c = value;
                String[] countryArr;
                if (c.getName().equals(s)) {
                    findKey[0] = true;
                    countryArr = new String[c.getAllCountries().size()];
                    continentList.setSelectedValue(c.getName(), true);

                    for (int i = 0; i < c.getAllCountries().size(); i++) {
                        countryArr[i] = c.getOneCountry(i).getKorName();
                    }
                    Arrays.sort(countryArr);
                    for (String str : countryArr) {
                        countryResult.addElement(str);
                    }
                    continentResult.addElement(c.getName());
                }
                for (int i = 0; i < c.getNumCountry(); i++) {
                    Country country = c.getOneCountry(i);
                    if (country.getKorName().contains(s) || country.getEngName().contains(s)) {
                        findKey[0] = true;
                        System.out.println(country.getKorName());
                        if (country.getKorName().equals(s) || country.getEngName().equals(s)) {
                            equalKey[0] = true;
                            countryArr = new String[country.getMyContinent().getNumCountry()];
                            for (int j = 0; j < country.getMyContinent().getNumCountry(); j++) {
                                countryArr[j] = country.getMyContinent().getOneCountry(j).getKorName();
                            }
                            Arrays.sort(countryArr);
                            for (int j = 0; j < countryArr.length; j++) {
                                countryResult.addElement(countryArr[j]);
                            }
                            continentList.setSelectedValue(country.getMyContinent().getName(), true);
                            countryList.setSelectedValue(country.getKorName(), true);
                            return;
                        }

                        countryResult.addElement(country.getKorName());
                    }
                    for (int j = 0; j < country.getNumAirport(); j++) {
                        Airport airport = country.getOneAirport(j);
                        if (airport.getKorName().contains(s) || airport.getEngName().contains(s) ||
                                airport.getCityName().equals(s) || airport.getAirCode1().equals(s) ||
                                airport.getAirCode2().equals(s)) {
                            findKey[0] = true;
                            if (airport.getKorName().equals(s)) {
                                equalKey[0] = true;
                                countryArr = new String[airport.getMyCountry().getMyContinent().getNumCountry()];

                                for (int k = 0; k < countryArr.length; k++) {
                                    countryArr[k] = c.getOneCountry(k).getKorName();
                                }
                                Arrays.sort(countryArr);
                                for (int k = 0; k < countryArr.length; k++) {
                                    countryResult.addElement(countryArr[k]);
                                }
                                airportResult.addElement(airport.getKorName());
                                continentList.setSelectedValue(airport.getMyCountry().getMyContinent().getName(), true);
                                countryList.setSelectedValue(airport.getMyCountry().getKorName(), true);
                                airportList.setSelectedValue(airport.getKorName(), true);
                                return;
                            }
                            System.out.println(airport.getKorName());
                            airportResult.addElement(airport.getKorName());
//                            countryResult.addElement(airport.getMyCountry().getKorName());
                            continentList.setSelectedValue(airport.getMyCountry().getMyContinent().getName(), true);
                        }
                    }
                }
            });
            if (equalKey[0]) return;
            if (!findKey[0] || s.equals((""))) {
                if (s.equals("")) JOptionPane.showMessageDialog(null, "검색어를 입력하세요!", "오류", JOptionPane.WARNING_MESSAGE);
                else JOptionPane.showMessageDialog(null, s + "의 정보를 찾지 못했습니다.", "오류", JOptionPane.WARNING_MESSAGE);
            } else {
                countryList.countryName.removeAllElements();
                countryList.setModel(countryResult);
                airportList.airportName.removeAllElements();
                airportList.setModel(airportResult);
            }


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

            SwingUtilities.updateComponentTreeUI(this);
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
                if (countryList != null) {
                    countryList.countryName.removeAllElements();
                    countryList.setModel(countryList.countryName);
                    airportList.airportName.removeAllElements();
                    airportList.setModel(airportList.airportName);
                }
                currentconName = this.getSelectedValue().toString();
                ProjectMain.setSelectedContinent(ProjectMain.getContinent(currentconName));
                currentCont = ProjectMain.getSelectedContinent();
                continentImagePanel.repaint();
                countryList.setCountryList(currentCont);
                airportList.airportName.removeAllElements();
                ProjectMain.setSelectedCountry(null);
                ProjectMain.setSelectedAirport(null);
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
                if (this.getSelectedValue() != null) {
                    String countryStr = this.getSelectedValue().toString();

                    ProjectMain.setSelectedContinent(ProjectMain.allCountry.get(countryStr).getMyContinent());
                    currentCont = ProjectMain.getSelectedContinent();
                    continentList.setSelectedValue(ProjectMain.getSelectedContinent().getName(), true);
                    countryList.setSelectedValue(countryStr, true);
                    for (int i = 0; i < ProjectMain.getSelectedContinent().getAllCountries().size(); i++) {
                        if (ProjectMain.getSelectedContinent().getOneCountry(i).getKorName().equals(countryStr)) {
                            ProjectMain.setSelectedCountry(ProjectMain.getSelectedContinent().getOneCountry(i));
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

    class AirportList extends JList {
        DefaultListModel<String> countryModel = new DefaultListModel<>();

        DefaultListModel<String> airportName;
        private String clickAirportName;
        private String[] countryArr;

        public AirportList() {
            super();
            airportName = new DefaultListModel<>();
            addMouseListener(mouseListener);
        }

        public void setAirportList(Country c) {
            airportName.removeAllElements();
            ArrayList<String> airport = new ArrayList<>();
            for (int i = 0; i < c.getAllAirport().size(); i++) {
                if (inter_all_dom_status == all) {
                    airport.add(c.getOneAirport(i).getKorName());
                } else if (inter_all_dom_status == international) {
                    if (c.getOneAirport(i).isInternational()) {
                        airport.add(c.getOneAirport(i).getKorName());
                    }
                } else if (inter_all_dom_status == domestic) {
                    if (!c.getOneAirport(i).isInternational()) {
                        airport.add(c.getOneAirport(i).getKorName());
                    }
                }
            }
            String[] airport_str = new String[airport.size()];
            airport_str = airport.toArray(airport_str);
            Arrays.sort(airport_str); //나라 정렬
            for (int i = 0; i < airport_str.length; i++) {
                airportName.addElement(airport_str[i]);
            }
            setModel(airportName);
//            final Boolean[] isClicked = {true};

        }

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList<String> theList = (JList) mouseEvent.getSource();
                int index = theList.locationToIndex(mouseEvent.getPoint());

                if (airportList.getMaxSelectionIndex() != -1) {
                    clickAirportName = theList.getModel().getElementAt(index);
                    ProjectMain.setSelectedAirport(ProjectMain.getAirport(clickAirportName));
                    currentAirport = ProjectMain.getSelectedAirport();
                    //현재 선택된 국가가 소속된 대륙과 나라 아이템이 클릭 된 것처럼 설정
                    ProjectMain.setSelectedCountry(currentAirport.getMyCountry());
                    ProjectMain.setSelectedContinent(ProjectMain.getSelectedCountry().getMyContinent());
                    currentCont = ProjectMain.getSelectedContinent();
                    currentCountry = ProjectMain.getSelectedCountry();
                    countryArr = new String[currentCont.getNumCountry()];
                    for(int i=0;i<currentCont.getNumCountry();i++){
                        countryArr[i] = currentCont.getOneCountry(i).getKorName();
                    }
                    Arrays.sort(countryArr);
                    for(int i=0;i<countryArr.length;i++){
                        countryModel.addElement(countryArr[i]);
                    }
                    countryList.setModel(countryModel);
                    continentList.setSelectedValue(currentCont.getName(), true);
                    countryList.setSelectedValue(currentCountry.getKorName(), true);
                    airportList.setSelectedValue(currentAirport.getKorName(), true);

                    //더블 클릭 시 공항 정보 출력
                    if (mouseEvent.getClickCount() == 2) {
                        if (index >= 0) {
                            showAirportInfo();
                        }
                    }
                }
            }
        };

        private void showAirportInfo() { // 공항정보 출력 함수
            String str = "현재 공항 정보\n\n"
                    + "공항 지역 : " + currentAirport.getCityName() + "\n영문 공항명 : " + currentAirport.getEngName() + "\n한글 공항명 : " + currentAirport.getKorName()
                    + "\n공항 코드1(IATA) : " + currentAirport.getAirCode1()
                    + "\n공항 코드2(ICAO) : " + currentAirport.getAirCode2();
            JOptionPane.showMessageDialog(null, str, clickAirportName + " 정보", JOptionPane.PLAIN_MESSAGE);

        }
    }

    class InfoPanel extends Panel {
        Airport myAirport; //생성자 함수에 Airport 정보 받는 걸로 후에 수정

        public InfoPanel() {
            ButtonGroup rGroup = new ButtonGroup();
            allRBtn.addItemListener(new RadioButtonListener());
            interRBtn.addItemListener(new RadioButtonListener());
            domRBtn.addItemListener(new RadioButtonListener());

            allRBtn.setSelected(true); //전체 버튼 default check
            rGroup.add(allRBtn);
            rGroup.add(interRBtn);
            rGroup.add(domRBtn);

            add(allRBtn);
            add(interRBtn);
            add(domRBtn);

        }

        class RadioButtonListener implements ItemListener {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    return;
                }
                if (allRBtn.isSelected()) {
                    inter_all_dom_status = all;
                    airportList.airportName.removeAllElements();
                } else if (interRBtn.isSelected()) {
                    inter_all_dom_status = international;
                } else if (domRBtn.isSelected()) {
                    inter_all_dom_status = domestic;
                }
                if (ProjectMain.getSelectedCountry() != null)
                    airportList.setAirportList(ProjectMain.getSelectedCountry());
            }
        }
        //그룹에 그룹화시킬 버튼들을 추가
    }


}
