package hsy.similar;

public class DistenceMeasure {
	
	/**
	 * Measure the difference between text1 and text2
	 * @param text1 文本1
	 * @param text2 文本2
	 * @param language *EN OR CN* EN：split(" ") CN:split("")
	 */
	public double input(String text1, String text2, String language){
		BLEU bleu = new BLEU();
		EditDistence dis = new EditDistence();
		double b = bleu.bleuAlgorithm(text1, text2, 4, language);//BLEU value   4：N-gram's N
		double e = dis.minVal(text1, text2, language);//minimal edit distence (1/(med+1))
		double result = 0.5*b+0.5*e;//weight could be setting as your need.
		System.out.println(text1+" | "+text2);
		System.out.println("similar value:"+result);
		return result;
	}

}
