import java.util.Random;

public class GroupChoice {

	public static void main(String[] args) {
		
		String[] name = {"최승연", "노규민", "최대한", "신동호", "이찬호", "김선현", "김현정", "조예지", "박병선", "전예솜", "최유정", "정혜인",
				"임명훈", "최가슬", "민경우", "이화영", "변영우", "이우택", "최재형", "박찬주", "김다예", "오지은", "봉재성", "임종민" ,"백성운",
				"박지현", "신상준", "안홍용", "김원식", "이민형", "심운보", "이진원", "이주희"};
		Random random = new Random();
		
		for(int i = 0; i < 1000000; i++) {
			int r = random.nextInt(name.length - 1) + 1;
			String temp = name[0];
			name[0] = name[r];
			name[r] = temp;
		}
		
		for(int i = 0; i < 8; i++) {
			System.out.print(i + 1 + "조 : ");
			for(int j = i * 4; j < (i + 1) * 4; j++) {
				if(j % 4 != 0) {
					System.out.print(", ");
				}
				System.out.print(name[j]);
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			}
			System.out.println();
		}
		System.out.println(name[name.length - 1] + " : " + (random.nextInt(8) + 1) + "조");
		
	}
	
}








