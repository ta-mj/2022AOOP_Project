import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
public class TeamProject {
	//프로젝트 정보
	private String subject;
	//구성원
	private ArrayList<user> myUser = new ArrayList<>();
	//업무
	private LinkedList<Task> myTask = new LinkedList<>();
	//일정
	//알림
	TeamProject(String s, user u){
		subject = s;
		myUser.add(u);
	}
	public void setSubject(String s) {
		subject = s;
	}
	public void addUser(user u) {
		myUser.add(u);
	}
	public void addTask(Task t) {
		myTask.add(t);
	}
	public void sortTaskByStartDate() {
		System.out.println("등록일을 기준으로 정렬합니다.");
	}
	public void sortTaskByTargetDate() {
		System.out.println("마감일을 기준으로 정렬합니다.");
	}
	public void printProject() {
		System.out.println("프로젝트명:" + subject);
		for(int i = 0 ; i < myUser.size() ; i++) {
			System.out.println("팀원정보");
			System.out.println(myUser.get(i).getName());
		}
	}
}
