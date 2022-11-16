import java.util.ArrayList;
public class Continent {
    private String name;//대륙 이름
    private ArrayList<Country> myCountry;//이 대륙에 포함된 국가
    private int numCountry;//이 대륙에 포함된 국가의 수
    private int numAirport;// 이 대륙에 포함된 공항의 수
    public Continent(String n){
        this.name = n;
        this.numCountry = 0;
        this.numAirport = 0;
        this.myCountry = new ArrayList<>();
    }
    public void setName(String n){
        this.name = n;
    }
    public void setMyCountry(Country c){
        this.myCountry.add(c);
        c.setMyContinent(this);
        this.numCountry++;
    }
    public void addAirport() {
        this.numAirport++;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Country> getAllCountries(){
        return this.myCountry;
    }
    public Country getOneCountry(int position){
        return this.myCountry.get(position);
    }
    public int getCountryPosition(String s) {
        for(int i = 0 ; i < myCountry.size(); i++) {
            if(this.myCountry.get(i).getKorName().equals(s) == true) {
                return i;
            }
        }
        return -1;
    }
    public int getNumCountry(){ return this.numCountry; }

    public Country getmyCountry(Country c) {
        if(this.myCountry.contains(c)){
            return c;
        }
        return null;
    }
}
