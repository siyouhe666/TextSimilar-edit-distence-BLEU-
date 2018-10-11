package hsy.similar;

public class EditDistence {
	
	private int replaceWeight = 1;
	private int insertWeight = 1;
	private int deleteWeight = 1;
	
	//setting the edit operation weights,default setting(insert:1, delete:1, replace:1)
	public EditDistence(int replace, int insert, int delete){
		this.replaceWeight = replace;
		this.insertWeight = insert;
		this.deleteWeight = delete;
	}
	
	//default construct method:use default weights(1,1,1)
	public EditDistence(){
		
	}
	
	private void outputArr(int[][] arr){
		for(int i = 0;i<arr.length;i++){
			for(int j = 0;j<arr[0].length;j++){
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	
	public int[][] editDistence(String text1, String text2, String language){
		int[][] edit = null;
		String[] strAr1 = pirProcess(text1, language);
		String[] strAr2 = pirProcess(text2, language);
		edit = new int[strAr1.length][strAr2.length];
		edit = initialMat(edit);
		for(int i = 1;i<strAr1.length;i++){
			String char1 = strAr1[i];
			for(int j = 1;j<strAr2.length;j++){
				String char2 = strAr2[j];
				int min = min3Val(edit[i][j-1], edit[i-1][j], edit[i-1][j-1], char1, char2);
				edit[i][j] = min;
			}
		}
		System.out.println("minimal edit distence："+edit[edit.length-1][edit[0].length-1]);
//		outputArr(edit);
		return edit;
	}
	
	/*
	 * 返回矩阵右下角最短距离
	 */
	public double minVal(String text1, String text2, String language){
		int[][] edit = editDistence(text1, text2, language);
		int min =  edit[edit.length-1][edit[0].length-1];
		return (1.0)/(double)(min+1);
	}
	
	
	/**
	 * 求当前位置edit[i][j]的最短编辑距离
	 * @param left 
	 * @param up
	 * @param lu
	 * @param char1
	 * @param char2
	 * @return
	 */
	private int min3Val(int left, int up, int lu, String char1, String char2){
		int min = Integer.MAX_VALUE;
		int insert = left+this.insertWeight;
		int delete = up+this.deleteWeight;
		int replace = -1;
		if(char1.equals(char2)){
			replace = lu+0;
		}else{
			replace = lu+this.replaceWeight;
		}
		min = Math.min(insert, delete);
		min = Math.min(min, replace);
		return min;
	}
	
	
	
	
	/*
	 * 处理为英文单词
	 */
	private String[] processEN(String text){
		String[] strArr = text.split(" ");
		return strArr;
	}
	
	/*
	 * 处理为中文字符
	 */
	private String[] processCN(String text){
		String[] strArr = text.split("");
		return strArr;
	}
	
	
	/*
	 * 拼接#
	 */
	private String[] pirProcess(String text, String language){
		text = "#"+text;
		String[] strArr = null;
		if(language.equals("EN")){
			strArr = processEN(text);
		}else if(language.equals("CN")){
			strArr = processCN(text);
		}else{
			System.out.println("EN OR CN ?");
			System.exit(0);
		}
		return strArr;
	}
	
	/*
	 * 初始化矩阵
	 */
	private int[][] initialMat(int[][] edit){
		int rowNum = edit.length;
		int colNum = edit[0].length;
		for(int i = 0;i<rowNum;i++){
			edit[i][0] = i;
		}
		for(int j = 1;j<colNum;j++){
			edit[0][j] = j;
		}
		return edit;
	}
	
	

}
