package data_structure;

// 이 클래스는 IngredientData.kt와 연동되는 항목
public class IngredientData {
	public IngredientData(String string, String string2, String string3, int i, int j) {
		name = string;
		amount = string2;
		dateString = string3;
		pic = i;
		late = j;
	}
	public String name; // 음식명
	public String amount; // 음식의 양
	public String dateString; // 날짜값
	int pic;
	int late;
}