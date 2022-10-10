import java.util.Scanner;
public class app{
	public static void main(String arg[]) {
		Scanner sc = new Scanner(System.in);
		users Myuser = new users();
		Myuser.makeUser("kiatae0722","ptwmju2199@","박태우","kiatae0722@naver.com","01020449240");
		Myuser.makeUser("tedchang","tedchang1234","김창식","tedchang@naver.com","01063729793");
		Myuser.makeUser("kanye","0218","김하랑","kanye@naver.com","01067096942");
		Myuser.makeUser("ump","protein","엄태성","ump@naver.com","01012345678");
		user taewoo = Myuser.Login();
		TeamProject newProject = taewoo.makeProject();
		newProject.printProject();
	}
}