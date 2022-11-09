import java.util.ArrayList;
import java.util.HashMap;
public class Country {
    private String eng_name;//영문 국가명
    private String kor_name;//한글 국가명
    private Continent myContinent;//내가 소속된 지역
    private ArrayList<Airport> myAirport;//나라에 포함된 국가
    private int numAirport;
    public Country(String en,String kn){
        eng_name = en;
        kor_name = kn;
        numAirport = 0;
        myAirport = new ArrayList<>();
    }
    public void setEngName(String en){
        eng_name = en;
    }
    public void setKorName(String kn){
        kor_name = kn;
    }
    public void setMyAirport(Airport a){
        myAirport.add(a);
        a.setMyCountry(this);
        myContinent.addAirport();
    }
    public void setMyContinent(Continent cn){
        myContinent = cn;
    }
    public String getEngName(){ return eng_name; }
    public String getKorName(){ return kor_name; }
    public ArrayList<Airport> getAllAirport(){ return myAirport; }
    public Airport getOneAirport(int position){ return myAirport.get(position);}
}
