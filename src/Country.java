import java.util.ArrayList;
import java.util.HashMap;
public class Country {
    private String eng_name;//영문 국가명
    private String kor_name;//한글 국가명
    private Continent myContinent;//내가 소속된 지역
    private ArrayList<Airport> myAirport;//나라에 포함된 공항
    private int numAirport;
    public Country(String en,String kn){
        this.eng_name = en;
        this.kor_name = kn;
        this.numAirport = 0;
        this.myAirport = new ArrayList<>();
    }
    public void setEngName(String en){
        this.eng_name = en;
    }
    public void setKorName(String kn){
        this.kor_name = kn;
    }
    public void setMyAirport(Airport a){
        this.myAirport.add(a);
        a.setMyCountry(this);
        this.numAirport++;
        this.myContinent.addAirport();
    }
    public void setMyContinent(Continent cn){
        this.myContinent = cn;
    }
    public String getEngName(){ return this.eng_name; }
    public String getKorName(){ return this.kor_name; }
    public ArrayList<Airport> getAllAirport(){ return this.myAirport; }
    public Airport getOneAirport(int position){ return this.myAirport.get(position);}
    public int getNumAirport(){ return this.numAirport; }
}
