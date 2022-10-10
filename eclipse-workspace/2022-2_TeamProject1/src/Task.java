import java.time.*;
public class Task{
	//업무 이름
	String workname;
	//담당자
	user manager;
	//등록일
	LocalDate startDate;
	//마감일
	LocalDate targetDate;
	//완료 여부
	boolean is_complete;
	//제출 파일 --> 향후
	Task(String n, user m, LocalDate t){
		workname = n;
		manager = m;
		startDate = LocalDate.now();
		targetDate = t;
		is_complete = false;
	}

}
