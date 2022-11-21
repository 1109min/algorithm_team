package data_structure;

import java.util.ArrayList;

//�� Ŭ������ RecipeData.kt�� �����Ǵ� �׸�
public class RecipeData {
	public RecipeData(String string, ArrayList<NeedData> nd, int i, int j, int k) {
		name = string;
		ingredient = nd;
		pic = i;
		click = j;
		state = k;
	}
	public String name;
	public ArrayList<NeedData> ingredient;
	int pic = 0;
	int click = 0;
	int state = 0;
}
