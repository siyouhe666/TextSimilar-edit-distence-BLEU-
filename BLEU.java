package hsy.similar;
import java.util.ArrayList;
import java.util.List;


public class BLEU {
	
	
	/**
	 * BLEU算法主体
	 * @param tran 翻译文本
	 * @param answer 标准文本
	 * @param maxN 最大N-gram窗口
	 * @return BLEU值
	 */
	public double bleuAlgorithm(String tran, String answer, int maxN, String language){
		double wn = 1/(double)maxN;
		double BP = computePenalty(tran, answer);
		System.out.println("惩罚系数："+BP);
		double bleuVal = 0.;
		double expRes = 0.;
		for(int i = 1;i <= maxN;i++){
			List<String> tranGrams = null;
			List<String> answerGrams = null;
			if(language.equals("CN")){
				tranGrams = ngramCHExtrac(i, tran);
				answerGrams = ngramCHExtrac(i, answer);
			}else{
				tranGrams = ngramENExtrac(i, tran);
				answerGrams = ngramENExtrac(i, answer);
			}

			double minL = (double)minLen(tranGrams, answerGrams);
			if(minL==0){
				continue;
			}
			double shot = compareShot(tranGrams, answerGrams);
			double logPN = Math.log(shot/minL);
//			System.out.println(shot+"  "+minL);
			if(shot==0.){
				logPN = 0.;
			}
			expRes += (wn*logPN);
//			System.out.println(expRes+"**");
//			System.out.println(wn+"  "+logPN);
		}
		expRes = Math.exp(expRes);
		bleuVal = BP*expRes;
//		System.out.println("原文："+answer);
//		System.out.println("译文："+tran);
		System.out.println("BLEU："+bleuVal);
		System.out.println("-------------");
		return bleuVal;
	}
	
	
	/**
	 * 提取中文n-gram
	 * @param size n-gram窗口
	 * @param text 待提取文本
	 * @return n-gram List
	 */
	public List<String> ngramCHExtrac(int size, String text){
		List<String> nGram = new ArrayList<String>();
		String[] chars = text.split("");
		if(chars.length<size){
			return nGram;
		}
		for(int i = 0;i<chars.length-size+1;i++){
			String tempGram = text.substring(i, i+size);
			nGram.add(tempGram);
		}
//		System.out.println("nGrame NUM: "+nGram.size());
		return nGram;
	}
	
	/**
	 * 提取英文n-gram
	 * @param size n-gram窗口
	 * @param text 待提取文本
	 * @return n-gram List
	 */
	public List<String> ngramENExtrac(int size, String text){
		List<String> nGram = new ArrayList<String>();
		String[] words = text.split(" ");
		if(words.length<size){
			return nGram;
		}
		for(int i =0;i<words.length-size+1;i++){
			StringBuilder sb = new StringBuilder();
			for(int j=i;j<i+size;j++){
				sb.append(words[j]);
			}
//			System.out.println(sb.toString());
			nGram.add(sb.toString());
		}
		return nGram;
	}
	
	
	
	/**
	 * 统计命中率
	 * @param tranGrams
	 * @param answerGrams
	 * @return
	 */
	public double compareShot(List<String> tranGrams, List<String> answerGrams){
		double shot = 0.;
		for(String gram : tranGrams){
			if(answerGrams.contains(gram)){
//				System.out.println(gram);
				shot++;
			}
		}
//		System.out.println("shot:"+shot);
		return shot;
	}
	
	/**
	 * 返回最短n-gram长度
	 * @param tranGrams
	 * @param answerGrams
	 * @return
	 */
	public int minLen(List<String> tranGrams, List<String> answerGrams){
		return (tranGrams.size() < answerGrams.size()) ? (tranGrams.size()) : answerGrams.size();
	}
	
	/*
	 * 惩罚系数
	 */
	public double computePenalty(String tran, String answer){
		if(tran.length()>answer.length()){
			return 1.0;
		}else{
			double rc = (double)answer.length()/(double)tran.length();
			return Math.exp(1-rc);
		}
	}
	
	

}
