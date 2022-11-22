package app_Algorithm;

/* 
 * 이 클래스는 주어진 잔반을 이용해서 만들 수 있는 최적의 식단을 제공하는 알고리즘을 구현합니다.
 * Input : ArrayList<IngredientData> 의 배열을 통째로 받아옴.
 * Output : 최적의 식단을 ArrayList<RecipeData> 의 배열로 통째로 내보냄.
 * 위 IngredientData, RecipeData 타입은 kotlin단에 있는 data타입을 그대로 따라갑니다.
 */

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import data_structure.*;

public class Leftover_Food {
	int MAX_FOODS; // 최대 음식 개수
	int MAX_ING_PER_FOOD = 10; // 음식당 최대 재료 개수
	long today = (new Date().getTime())/(1000*60*60*24); // 오늘 날짜 (일 수)
	
	ArrayList<IngredientData> leftover = new ArrayList<>(); // 입력받기용 (IngredientData.kt와 연동 가능)
	ArrayList<RecipeData> foodList = new ArrayList<>(); // 전체 음식의 종류들을 넣은 값 (RecipeData.kt와 연동 가능) <<
	ArrayList<IngredientDataTransform> modLeft = new ArrayList<>(); // 계산할 때 사용할 arrayList (leftover 변수 변형) <<
	ArrayList<RecipeData> resultFood = new ArrayList<>(); // 최종적으로 return할 food 종류를 담은 array (우선순위 순서대로)
	long[][] foodWeight; // foodList 위에 덮어쓰는 weight 계산용 clone
	
	Leftover_Food(ArrayList<IngredientData> dat1, ArrayList<RecipeData> dat2) {
		leftover = dat1;
		foodList = dat2;
		System.out.println("데이터 입력 완료");
	}
	
	public ArrayList<RecipeData> get_Leftover_Food() {
		// 먼저 input 값을 아래 for문을 이용해서 분석, modLeft 함수에 추가
		
		MAX_FOODS = foodList.size();
		foodWeight = new long[MAX_FOODS][MAX_ING_PER_FOOD + 3];
		System.out.println("확인된 음식 종류 : " + String.valueOf(MAX_FOODS));
		// 추가로 들어가는 끝 3개의 index는 음식에 들어가는 총 재료의 개수, 현재 재료에 들어간 값, 가중치의 평균
		
		// leftover 입력을 modLeft의 계산하기 쉬운 형태로 변환.
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < leftover.size(); i++) {
			IngredientDataTransform tmpIngData = new IngredientDataTransform();
			tmpIngData.name = leftover.get(i).name;
			// 숫자가 아닌 모든 단위값을 제거하여 plain integer값만 받음
			tmpIngData.amount = Integer.parseInt(leftover.get(i).amount.replaceAll("[^0-9]", ""));
			try {
				tmpIngData.dateString = dateForm.parse(leftover.get(i).dateString).getTime();
				tmpIngData.dateString /= (1000*60*60*24); // 날짜 값으로 변환
			} catch (ParseException e) {
				System.out.println("Parse Failed");
				break;
			}
			
			System.out.println("재료 데이터 추가 : " + tmpIngData.name + ", " + tmpIngData.amount + ", " + tmpIngData.dateString);
			modLeft.add(tmpIngData);
		}
		
		Collections.sort(modLeft, new Ascending());
		for (int i = 0; i < modLeft.size(); i++) {
			System.out.println("정돈된 재료 데이터 : " + modLeft.get(i).name + ", " + modLeft.get(i).amount + ", " + modLeft.get(i).dateString);
		}
		// 여기까지 왔으면 받은 정보를 modLeft 배열에 정돈된 형태로 들어가게 됨.
		
		// 여기는 foodList 입력을 기반으로 가중치를 계산하는 셀의 초기값을 지정
		for (int i = 0; i < MAX_FOODS; i++) {
			foodWeight[i][MAX_ING_PER_FOOD + 2] = -1; // 가중치 평균 초기값 (추후 정렬 시 사용)
			foodWeight[i][MAX_ING_PER_FOOD + 1] = foodList.get(i).ingredient.size(); // 각 음식 당 ingredient의 개수
			foodWeight[i][MAX_ING_PER_FOOD] = 0; // 현재 계산된 ingredient의 개수 (계산시 사용)
		}
		
		this.Calculate_Recipe();
		return resultFood;
	}
	
	private void Calculate_Recipe() {
		// 대략적인 방법은 그리디 알고리즘을 이용하여 유통기한이 낮은 순서대로 음식을 만듬.
		int calTime = 0; // 시간이 오래 걸릴때를 대비한 변수. 이 변수가 1억이 넘어가면 프로그램이 중지된다.
		
		for (int i = 0; i < modLeft.size(); i++) { // 제료 array index
			if (calTime > 99999999) {
				System.out.println("Calculate limit Exceed");
				break;
			}
			
			String curIngName = modLeft.get(i).name; // 찾는대상 재료의 이름
			int curIngAmount = modLeft.get(i).amount; // 찾는대상 재료의 양
			long curIngDate = modLeft.get(i).dateString - today; // 오늘로부터 남은 유통기한
			if (curIngDate<0) curIngDate = 0; // 가중치 64000
			if (curIngDate>30) curIngDate = 31; // 가중치 729
			
			System.out.println("=============================================");
			System.out.println("현재 대상 재료 정보 | 이름 : " + curIngName + ", 남은 날짜 : " + curIngDate);
			System.out.println("=============================================");
			for (int j = 0; j < MAX_FOODS; j++) { // 음식 array index
				// 이미 평균 weight가 계산된 음식이면 다음 음식으로 바로 이동
				if (foodWeight[j][MAX_ING_PER_FOOD + 2] != -1) {calTime += 2; continue;}
				
				// 해당 음식에 선택된 재료가 있는 지 탐색
				System.out.println("현재 음식의 이름과 재료 종류 수 : " + foodList.get(j).name + ", " + foodList.get(j).ingredient.size());
				for (int k = 0; k < foodList.get(j).ingredient.size(); k++) {
					String foodIng = foodList.get(j).ingredient.get(k).name; // 탐색 대상 음식 재료
					int foodAmount = Integer.parseInt(foodList.get(j).ingredient.get(k).amount.replaceAll("[^0-9]", "")); // 탐색 대상 재료 필요양
					if (!curIngName.equals(foodIng)) {calTime += 11; continue;} // 재료명이 다르면 다음거 탐색
					
					// 여기는 재료명이 같은 것을 찾았을 경우만 이 분기로 옴
					if (curIngAmount < foodAmount) {
						foodWeight[j][MAX_ING_PER_FOOD + 2] = -99;
						calTime += 13;
						break;
						} // 양이 부족하면 음식제작이 불가능하다고 판단, 가중치를 음수로 지정하고 다음 음식을 탐색
					
					long weight = (long)Math.pow(40-curIngDate,3); // 남은 유통기한 일수에 따른 가중치 추가
					if (curIngAmount == foodAmount) { // 음식을 완벽하게 사용했을 경우
						weight *= 4;
					}
					else { // 음식을 일부만 사용했을 경우
						long tmp = weight; // 사용량에 따른 보너스
						tmp *= foodAmount;
						tmp /= curIngAmount;
						weight += tmp;
					}
					
					long tmp = weight; // 음식 종류 개수에 따른 보너스
					tmp *= foodList.get(j).ingredient.size();
					tmp /= 10;
					weight += tmp;
					
					// 여기에 선호도 추가?
					
					// 해당 재료에 대한 가중치 넣기
					calTime += 20;
					foodWeight[j][k] = weight; // 가중치 대입
					foodWeight[j][MAX_ING_PER_FOOD] += 1; // 계산된 재료 개수 +1
					System.out.println("가중치 정보 추가됨 | 이름 : " + foodList.get(j).name + " | 가중치 : " + weight + " | 경과 시간 : " + calTime);
					if (foodWeight[j][MAX_ING_PER_FOOD] == foodWeight[j][MAX_ING_PER_FOOD + 1]) {
						// 만약 해당 food 내의 모든 재료가 완성이 되었다면 평균 가중치 계산
						// standard 방식을 채용
						long weightSum = 0;
						for (int m = 0; m < foodList.get(j).ingredient.size(); m++) {
							weightSum += foodWeight[j][m];
							calTime += 2;
						}
						foodWeight[j][MAX_ING_PER_FOOD + 2] = weightSum / foodList.get(j).ingredient.size();
						calTime += 3;
					}
					break;
				} // k loop문의 끝
			} // j loop문의 끝
		} // i loop문의 끝
		
		System.out.println("\n\n================| 계산 완료 |================");
		
		// 여기까지 왔으면 모든 재료에 대한 데이터 (또는 유통기한이 임박한 재료들만의 음식 리스트 완료)
		// 5순위까지의 음식 데이터를 넣어둠.
		long[][] foodRank = new long[3][2]; // 각각 음식 index번호, weight값
		for (int i=0;i<3;i++) {foodRank[i][0] = -1; foodRank[i][1] = -1;}
		for (int i = 0; i < MAX_FOODS; i++) {
			long calWeight = foodWeight[i][MAX_ING_PER_FOOD + 2];
			System.out.println("index " + i + " | 음식명 : " + foodList.get(i).name + " | 평균 가중치 : " + calWeight);
			if (calWeight<100) continue; // 만들 수 없거나 스킵된 음식 제작법 거르기
			
			// top 3에 들면 3위 갈아치우기
			if (foodRank[2][1] < calWeight) {
				foodRank[2][0] = (long)i;
				foodRank[2][1] = calWeight;
			}
			// 이후 상위 랭킹보다 갈아치운 값이 더 크면 계속 스왑해줌
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
		
		// 여기까지 왔으면 음식 리스트에 대한 순위까지 정산 완료
		for (int i = 0; i < 3; i++) {
			// foodRank에 있는 index 순서대로 resultFood에 입력 (총 5순위까지)
			if (foodRank[i][0] < 0) {
				//out of index
				break;
			}
			resultFood.add(foodList.get((int)foodRank[i][0]));
			System.out.println("Rank " + String.valueOf(i+1) + " : " + foodList.get((int)foodRank[i][0]).name + ", " + String.valueOf(foodRank[i][1]));
			// rank n : (이름), (평균가중치)
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
