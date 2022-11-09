import java.util.ArrayList;
public class Continent {
    private String name;//대륙 이름
    private ArrayList<Country> myCountry;//이 대륙에 포함된 국가
    private int numCountry;//이 대륙에 포함된 국가의 수
    private int numAirport;// 이 대륙에 포함된 공항의 수
    public Continent(String n){
        name = n;
        numCountry = 0;
        numAirport = 0;
        myCountry = new ArrayList<>();
    }
    public void setName(String n){
        this.name = n;
    }
    public void setMyCountry(Country c){
        myCountry.add(c);
        c.setMyContinent(this);
        numCountry++;
    }
    public void addAirport() {
        numAirport++;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Country> getAllCountries(){
        return myCountry;
    }
    public Country getOneCountry(int position){
        return myCountry.get(position);
    }
    public int getCountryPosition(String s) {
        for(int i = 0 ; i < myCountry.size(); i++) {
            if(myCountry.get(i).getEngName().equals(s) == true) {
                return i;
            }
        }
        return -1;
    }
}
