import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class users {
    HashMap<String,user> myusers = new HashMap<>();
    public boolean makeUser(String id,String pw,String name,String email,String ph) {
    	if(myusers.containsKey(id)) {
    		System.out.println("존재하는 아이디입니다.");
    		return false;
    	}
    	user newuser =  new user(pw,name,email,ph);
        myusers.put(id, newuser);
        return true;
    }
    public user Login() {
    	Scanner sc = new Scanner(System.in);
    	String id;
    	String pw;
    	while(1 == 1) {
    		System.out.println("아이디를 입력하세요");  
    		id = sc.next();
    		System.out.println("비밀번호를 입력하세요");
        	pw = sc.next();
           	if(myusers.containsKey(id)) {
           		if(myusers.get(id).getPW().equals(pw) == false) {
               		System.out.println(myusers.get(id).getPW());
               		System.out.println(pw);
           			System.out.println("비밀번호가 틀립니다.");
           			continue;
           		}
           		else {
           			System.out.println("로그인 성공!");
           			System.out.println("로그인 정보:");
           			myusers.get(id).printInfo();
           			return myusers.get(id);
           		}
        	}
           	else {
           		System.out.println("아이디가 존재하지 않습니다.");
           		continue;
           	}
    	}
    }
    //앱 개발시 필요 없음
    public void printUser() {     
    	myusers.forEach((key, value) -> {	
    		System.out.println("id:" + key);
    		myusers.get(key).printInfo();
    		System.out.println();
    	});	

    }

}

