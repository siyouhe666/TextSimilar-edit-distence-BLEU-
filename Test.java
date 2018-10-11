package hsy.similar;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String language = "CN";
		String text1 = "哎哟 这个 算法 好难";
		String text2 = "哦 ， 这个 算法 太难 了。";
		DistenceMeasure dm = new DistenceMeasure();
		dm.input(text1, text2, language);
	}

}
