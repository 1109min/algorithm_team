package app_Algorithm;

/* 
 * �� Ŭ������ �־��� �ܹ��� �̿��ؼ� ���� �� �ִ� ������ �Ĵ��� �����ϴ� �˰����� �����մϴ�.
 * Input : ArrayList<IngredientData> �� �迭�� ��°�� �޾ƿ�.
 * Output : ������ �Ĵ��� ArrayList<RecipeData> �� �迭�� ��°�� ������.
 * �� IngredientData, RecipeData Ÿ���� kotlin�ܿ� �ִ� dataŸ���� �״�� ���󰩴ϴ�.
 */

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import data_structure.*;

public class Leftover_Food {
	int MAX_FOODS; // �ִ� ���� ����
	int MAX_ING_PER_FOOD = 10; // ���Ĵ� �ִ� ��� ����
	long today = (new Date().getTime())/(1000*60*60*24); // ���� ��¥ (�� ��)
	
	ArrayList<IngredientData> leftover = new ArrayList<>(); // �Է¹ޱ�� (IngredientData.kt�� ���� ����)
	ArrayList<RecipeData> foodList = new ArrayList<>(); // ��ü ������ �������� ���� �� (RecipeData.kt�� ���� ����) <<
	ArrayList<IngredientDataTransform> modLeft = new ArrayList<>(); // ����� �� ����� arrayList (leftover ���� ����) <<
	ArrayList<RecipeData> resultFood = new ArrayList<>(); // ���������� return�� food ������ ���� array (�켱���� �������)
	long[][] foodWeight; // foodList ���� ����� weight ���� clone
	
	Leftover_Food(ArrayList<IngredientData> dat1, ArrayList<RecipeData> dat2) {
		leftover = dat1;
		foodList = dat2;
		System.out.println("������ �Է� �Ϸ�");
	}
	
	public ArrayList<RecipeData> get_Leftover_Food() {
		// ���� input ���� �Ʒ� for���� �̿��ؼ� �м�, modLeft �Լ��� �߰�
		
		MAX_FOODS = foodList.size();
		foodWeight = new long[MAX_FOODS][MAX_ING_PER_FOOD + 3];
		System.out.println("Ȯ�ε� ���� ���� : " + String.valueOf(MAX_FOODS));
		// �߰��� ���� �� 3���� index�� ���Ŀ� ���� �� ����� ����, ���� ��ῡ �� ��, ����ġ�� ���
		
		// leftover �Է��� modLeft�� ����ϱ� ���� ���·� ��ȯ.
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < leftover.size(); i++) {
			IngredientDataTransform tmpIngData = new IngredientDataTransform();
			tmpIngData.name = leftover.get(i).name;
			// ���ڰ� �ƴ� ��� �������� �����Ͽ� plain integer���� ����
			tmpIngData.amount = Integer.parseInt(leftover.get(i).amount.replaceAll("[^0-9]", ""));
			try {
				tmpIngData.dateString = dateForm.parse(leftover.get(i).dateString).getTime();
				tmpIngData.dateString /= (1000*60*60*24); // ��¥ ������ ��ȯ
			} catch (ParseException e) {
				System.out.println("Parse Failed");
				break;
			}
			
			System.out.println("��� ������ �߰� : " + tmpIngData.name + ", " + tmpIngData.amount + ", " + tmpIngData.dateString);
			modLeft.add(tmpIngData);
		}
		
		Collections.sort(modLeft, new Ascending());
		for (int i = 0; i < modLeft.size(); i++) {
			System.out.println("������ ��� ������ : " + modLeft.get(i).name + ", " + modLeft.get(i).amount + ", " + modLeft.get(i).dateString);
		}
		// ������� ������ ���� ������ modLeft �迭�� ������ ���·� ���� ��.
		
		// ����� foodList �Է��� ������� ����ġ�� ����ϴ� ���� �ʱⰪ�� ����
		for (int i = 0; i < MAX_FOODS; i++) {
			foodWeight[i][MAX_ING_PER_FOOD + 2] = -1; // ����ġ ��� �ʱⰪ (���� ���� �� ���)
			foodWeight[i][MAX_ING_PER_FOOD + 1] = foodList.get(i).ingredient.size(); // �� ���� �� ingredient�� ����
			foodWeight[i][MAX_ING_PER_FOOD] = 0; // ���� ���� ingredient�� ���� (���� ���)
		}
		
		this.Calculate_Recipe();
		return resultFood;
	}
	
	private void Calculate_Recipe() {
		// �뷫���� ����� �׸��� �˰����� �̿��Ͽ� ��������� ���� ������� ������ ����.
		int calTime = 0; // �ð��� ���� �ɸ����� ����� ����. �� ������ 1���� �Ѿ�� ���α׷��� �����ȴ�.
		
		for (int i = 0; i < modLeft.size(); i++) { // ���� array index
			if (calTime > 99999999) {
				System.out.println("Calculate limit Exceed");
				break;
			}
			
			String curIngName = modLeft.get(i).name; // ã�´�� ����� �̸�
			int curIngAmount = modLeft.get(i).amount; // ã�´�� ����� ��
			long curIngDate = modLeft.get(i).dateString - today; // ���÷κ��� ���� �������
			if (curIngDate<0) curIngDate = 0; // ����ġ 64000
			if (curIngDate>30) curIngDate = 31; // ����ġ 729
			
			System.out.println("=============================================");
			System.out.println("���� ��� ��� ���� | �̸� : " + curIngName + ", ���� ��¥ : " + curIngDate);
			System.out.println("=============================================");
			for (int j = 0; j < MAX_FOODS; j++) { // ���� array index
				// �̹� ��� weight�� ���� �����̸� ���� �������� �ٷ� �̵�
				if (foodWeight[j][MAX_ING_PER_FOOD + 2] != -1) {calTime += 2; continue;}
				
				// �ش� ���Ŀ� ���õ� ��ᰡ �ִ� �� Ž��
				System.out.println("���� ������ �̸��� ��� ���� �� : " + foodList.get(j).name + ", " + foodList.get(j).ingredient.size());
				for (int k = 0; k < foodList.get(j).ingredient.size(); k++) {
					String foodIng = foodList.get(j).ingredient.get(k).name; // Ž�� ��� ���� ���
					int foodAmount = Integer.parseInt(foodList.get(j).ingredient.get(k).amount.replaceAll("[^0-9]", "")); // Ž�� ��� ��� �ʿ��
					if (!curIngName.equals(foodIng)) {calTime += 11; continue;} // ������ �ٸ��� ������ Ž��
					
					// ����� ������ ���� ���� ã���� ��츸 �� �б�� ��
					if (curIngAmount < foodAmount) {
						foodWeight[j][MAX_ING_PER_FOOD + 2] = -99;
						calTime += 13;
						break;
						} // ���� �����ϸ� ���������� �Ұ����ϴٰ� �Ǵ�, ����ġ�� ������ �����ϰ� ���� ������ Ž��
					
					long weight = (long)Math.pow(40-curIngDate,3); // ���� ������� �ϼ��� ���� ����ġ �߰�
					if (curIngAmount == foodAmount) { // ������ �Ϻ��ϰ� ������� ���
						weight *= 4;
					}
					else { // ������ �Ϻθ� ������� ���
						long tmp = weight; // ��뷮�� ���� ���ʽ�
						tmp *= foodAmount;
						tmp /= curIngAmount;
						weight += tmp;
					}
					
					long tmp = weight; // ���� ���� ������ ���� ���ʽ�
					tmp *= foodList.get(j).ingredient.size();
					tmp /= 10;
					weight += tmp;
					
					// ���⿡ ��ȣ�� �߰�?
					
					// �ش� ��ῡ ���� ����ġ �ֱ�
					calTime += 20;
					foodWeight[j][k] = weight; // ����ġ ����
					foodWeight[j][MAX_ING_PER_FOOD] += 1; // ���� ��� ���� +1
					System.out.println("����ġ ���� �߰��� | �̸� : " + foodList.get(j).name + " | ����ġ : " + weight + " | ��� �ð� : " + calTime);
					if (foodWeight[j][MAX_ING_PER_FOOD] == foodWeight[j][MAX_ING_PER_FOOD + 1]) {
						// ���� �ش� food ���� ��� ��ᰡ �ϼ��� �Ǿ��ٸ� ��� ����ġ ���
						// standard ����� ä��
						long weightSum = 0;
						for (int m = 0; m < foodList.get(j).ingredient.size(); m++) {
							weightSum += foodWeight[j][m];
							calTime += 2;
						}
						foodWeight[j][MAX_ING_PER_FOOD + 2] = weightSum / foodList.get(j).ingredient.size();
						calTime += 3;
					}
					break;
				} // k loop���� ��
			} // j loop���� ��
		} // i loop���� ��
		
		System.out.println("\n\n================| ��� �Ϸ� |================");
		
		// ������� ������ ��� ��ῡ ���� ������ (�Ǵ� ��������� �ӹ��� ���鸸�� ���� ����Ʈ �Ϸ�)
		// 5���������� ���� �����͸� �־��.
		long[][] foodRank = new long[3][2]; // ���� ���� index��ȣ, weight��
		for (int i=0;i<3;i++) {foodRank[i][0] = -1; foodRank[i][1] = -1;}
		for (int i = 0; i < MAX_FOODS; i++) {
			long calWeight = foodWeight[i][MAX_ING_PER_FOOD + 2];
			System.out.println("index " + i + " | ���ĸ� : " + foodList.get(i).name + " | ��� ����ġ : " + calWeight);
			if (calWeight<100) continue; // ���� �� ���ų� ��ŵ�� ���� ���۹� �Ÿ���
			
			// top 3�� ��� 3�� ����ġ���
			if (foodRank[2][1] < calWeight) {
				foodRank[2][0] = (long)i;
				foodRank[2][1] = calWeight;
			}
			// ���� ���� ��ŷ���� ����ġ�� ���� �� ũ�� ��� ��������
			for (int j = 1; j >= 0; j--) {
				if (foodRank[j][1] < foodRank[j+1][1]) {
					long tmp1 = foodRank[j][0];
					long tmp2 = foodRank[j][1];
					foodRank[j][0] = foodRank[j+1][0];
					foodRank[j][1] = foodRank[j+1][1];
					foodRank[j+1][0] = tmp1;
					foodRank[j+1][1] = tmp2;
				}
				else break;
			}
		}
		
		// ������� ������ ���� ����Ʈ�� ���� �������� ���� �Ϸ�
		for (int i = 0; i < 3; i++) {
			// foodRank�� �ִ� index ������� resultFood�� �Է� (�� 5��������)
			if (foodRank[i][0] < 0) {
				//out of index
				break;
			}
			resultFood.add(foodList.get((int)foodRank[i][0]));
			System.out.println("Rank " + String.valueOf(i+1) + " : " + foodList.get((int)foodRank[i][0]).name + ", " + String.valueOf(foodRank[i][1]));
			// rank n : (�̸�), (��հ���ġ)
		}
	}
	
	class Ascending implements Comparator<IngredientDataTransform> {
		public int compare(IngredientDataTransform ing1, IngredientDataTransform ing2) {
			if (ing1.dateString > ing2.dateString) return 1;
			else if (ing1.dateString < ing2.dateString) return -1;
			return 0;
		}
	}
}
