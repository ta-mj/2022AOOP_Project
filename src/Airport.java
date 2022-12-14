public class Airport {
    private String eng_name;//영문 공항명
    private String kor_name;//한글 공항명
    private String city_name;//지역명
    private String air_code1;//공항 코드1
    private String air_code2;//공항 코드2
    private Country myCountry;//공항이 소속된 나라
    public Airport(String en, String kn, String ac1, String ac2 , String cn){
        this.eng_name = en;
        this.kor_name = kn;
        this.city_name = cn;
        this.air_code1 = ac1;
        this.air_code2 = ac2;
    }
    public void setEngName(String s){ this.eng_name = s; }
    public void setKorName(String s){ this.kor_name = s; }
    public void setCityName(String s){ this.city_name = s; }
    public void setAirCode1(String s){ this.air_code1 = s; }
    public void setAirCode2(String s){ this.air_code2 = s; }
    public void setMyCountry(Country c) { this.myCountry = c; }
    public String getEngName(){ return this.eng_name; }
    public String getKorName(){ return this.kor_name; }
    public String getCityName(){ return this.city_name; }
    public String getAirCode1(){ return this.air_code1; }
    public String getAirCode2(){ return this.air_code2; }

    public Country getMyCountry(){ return this.myCountry; }
    public Boolean isInternational() { return this.kor_name.contains("국제"); }
}
