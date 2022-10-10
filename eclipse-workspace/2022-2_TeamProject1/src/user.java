import java.util.ArrayList;
import java.util.Scanner;
public class user {
	private String pw;
	private String name;
	private String email;
	private String phone;
	private ArrayList<TeamProject> myProject = new ArrayList<TeamProject>();
	//project 객체
	//user 생성자
	public user(String p , String n , String e, String ph){
		pw = p;
		name = n;
		email = e;
		phone = ph;
	}
	public String getPW() {
		return pw;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNum() {
		return phone;
	}
	public void printInfo() {
		System.out.println("이름:" + getName());
		System.out.println("이메일:" + getEmail());
		System.out.println("전화번호:" + getPhoneNum());
	}
	public TeamProject makeProject() {
		Scanner sc = new Scanner(System.in);
		System.out.println("프로젝트명을 입력하세요.");
		TeamProject newProject = new TeamProject(sc.nextLine(),this);
		return newProject;
	}

}
