import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NewFrame extends JFrame {
    //상수 선언
    private static final boolean KOREAN = false;
    private static final boolean ENGLISH = true;
    private static final int ALLAIRPORT = 0;
    private static final int INTERAIRPORT = 1;
    private static final int DOMAIRPORT = 2;
    //스윙 관련 변수
    private JToolBar toolBar;

    //대륙 이미지 관련 변수
    public static String[] cons = {"유럽", "중동", "아시아", "아프리카", "대양주", "북미", "중남미"};
    public static HashMap<String, Image> conImage;

    //검색 관련 변수
    private JTextField searchField = new JTextField();
    private JButton searchButton = new JButton("검색");

    //한영 변환을 위한 Radio버튼
    private JRadioButton korean = new JRadioButton("한글");
    private JRadioButton english = new JRadioButton("영어");
    //한 영 상태
    private Boolean isKoreanOrEnglish = KOREAN;

    //국제/국내 filter을 위한 Radio버튼
    private JRadioButton allRBtn = new JRadioButton("전체"); //JRadioButton 생성
    private JRadioButton interRBtn = new JRadioButton("국제");
    private JRadioButton domRBtn = new JRadioButton("국내");
    //국제 국내 filter 상태
    private int airport_show_range = ALLAIRPORT;

    //대륙/나라/공항 전체 JList
    private static ConList allContinentList;
    private static CountryList allCountryList;
    private static AirportList allAirportList;
    //검색 데이터 들어갈 List
    private static ConList seletedContinentList;
    private static CountryList seletedCountryList;
    private static AirportList seletedAirportList;

    //데이터 관련 변수
    private static String currentconName;//현재 선택 된 대륙 이름
    private static Continent currentCont;//현재 선택된 대륙
    private static Country currentCountry;//현재 선택된 나라
    private static Airport currentAirport;//현재 선택된 공항
    public NewFrame(String title) throws IOException {
        super("공항 통합 검색");//타이틀
        currentconName = title;
        setImage();
        ProjectMain.setSelectedContinent(ProjectMain.getContinent(title));
        setDefaultCloseOperation(EXIT_ON_CLOSE); //끄기버튼 활성화
        setSize(1050, 650);          //크기
        setLayout(null);
        setLocationRelativeTo(null);//창이 가운데 나오게
        setResizable(false);
    }
    public void setImage() {
        conImage = new HashMap<>();
        for (int i = 0; i < cons.length; i++) {
            conImage.put(cons[i], new ImageIcon(MainFrame.class.getResource("./image/" + cons[i] + ".png")).getImage());
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
                if (allCountryList != null) {
                    allCountryList.countryName.removeAllElements();
                    allCountryList.setModel(allCountryList.countryName);
                    allAirportList.airportName.removeAllElements();
                    allAirportList.setModel(allAirportList.airportName);
                }
                currentconName = this.getSelectedValue().toString();
                ProjectMain.setSelectedContinent(ProjectMain.getContinent(currentconName));
                currentCont = ProjectMain.getSelectedContinent();
                //continentImagePanel.repaint();
                allCountryList.setCountryList(currentCont);
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
                    for (int i = 0; i < ProjectMain.getSelectedContinent().getAllCountries().size(); i++) {
                        if (ProjectMain.getSelectedContinent().getOneCountry(i).getKorName().equals(countryStr)) {
                            ProjectMain.setSelectedCountry(ProjectMain.getSelectedContinent().getOneCountry(i));
                            break;
                        } else {

                        }
                    }
                    allAirportList.setAirportList(ProjectMain.getSelectedCountry());
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
        DefaultListModel<String> airportName;
        private String clickAirportName;

        public AirportList() {
            super();
            airportName = new DefaultListModel<>();
            addMouseListener(mouseListener);
        }

        public void setAirportList(Country c) {
            airportName.removeAllElements();
            ArrayList<String> airport = new ArrayList<>();
            for (int i = 0; i < c.getAllAirport().size(); i++) {
                if (airport_show_range == ALLAIRPORT) {
                    airport.add(c.getOneAirport(i).getKorName());
                } else if (airport_show_range == INTERAIRPORT) {
                    if (c.getOneAirport(i).isInternational()) {
                        airport.add(c.getOneAirport(i).getKorName());
                    }
                } else if (airport_show_range == DOMAIRPORT) {
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
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        clickAirportName = theList.getModel().getElementAt(index);
                        ProjectMain.setSelectedAirport(ProjectMain.getAirport(clickAirportName));
                        currentAirport = ProjectMain.getSelectedAirport();
                        showAirportInfo();
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
}


