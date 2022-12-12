package app_Algorithm;

import java.util.*;

import data_structure.*;
import app_Algorithm.IngredientDataTransform;

public class TestMain {

	public static void main(String[] args) {
		
		// ===================| 레시피 정보 |===================
		ArrayList<RecipeData> recipes = new ArrayList<RecipeData>();
		ArrayList<NeedData> nd = new ArrayList<NeedData>();
		
		nd.add(new NeedData("김치","300g",0));
		nd.add(new NeedData("돼지고기","100g",0));
		nd.add(new NeedData("대파","30g",0));
		nd.add(new NeedData("마늘","20g",0));
		recipes.add(new RecipeData("김치찌개",new ArrayList<NeedData>(nd),0,0,0));
		nd.clear();
		
        nd.add(new NeedData("된장","300g",0));
        nd.add(new NeedData("양파","300g",0));
        nd.add(new NeedData("소금","5g",0));
        nd.add(new NeedData("마늘","20g",0));
        nd.add(new NeedData("청양고추","40g",0));
        nd.add(new NeedData("설탕","5g",0));
        nd.add(new NeedData("애호박","100g",0));
        nd.add(new NeedData("감자","50g",0));
        recipes.add(new RecipeData("된장찌개",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("명란젓","50g",0));
        nd.add(new NeedData("파","30g",0));
        nd.add(new NeedData("계란","3",0));
        nd.add(new NeedData("소금","5",0));
        recipes.add(new RecipeData("명란계란찜",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("어묵","100g",0));
        nd.add(new NeedData("무","100g",0));
        nd.add(new NeedData("양파","100g",0));
        nd.add(new NeedData("소금","5g",0));
        nd.add(new NeedData("후추","5g",0));
        nd.add(new NeedData("마늘","20g",0));
        nd.add(new NeedData("청양고추","20g",0));
        recipes.add(new RecipeData("어묵탕",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("오징어","600g",0));
        nd.add(new NeedData("양배추","100g",0));
        nd.add(new NeedData("당근","200g",0));
        nd.add(new NeedData("양파","150g",0));
        nd.add(new NeedData("파","100g",0));
        nd.add(new NeedData("마늘","20g",0));
        nd.add(new NeedData("청양고추","20g",0));
        nd.add(new NeedData("설탕","10g",0));
        recipes.add(new RecipeData("오징어볶음",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("닭가슴살","50g",0));
        nd.add(new NeedData("양파","100g",0));
        nd.add(new NeedData("소금","5g",0));
        nd.add(new NeedData("달걀","3",0));
        nd.add(new NeedData("파","20g",0));
        recipes.add(new RecipeData("닭가슴살전",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("미역","20g",0));
        nd.add(new NeedData("소고기","100g",0));
        nd.add(new NeedData("소금","5g",0));
        nd.add(new NeedData("마늘","20g",0));
        recipes.add(new RecipeData("미역국",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("두부","100g",0));
        nd.add(new NeedData("김치","100g",0));
        nd.add(new NeedData("돼지고기","250g",0));
        nd.add(new NeedData("양파","100g",0));
        nd.add(new NeedData("당근","20g",0));
        nd.add(new NeedData("대파","20g",0));
        nd.add(new NeedData("청양고추","20g",0));
        nd.add(new NeedData("마늘","10g",0));
        recipes.add(new RecipeData("두부김치",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("부추","100g",0));
        nd.add(new NeedData("계란","4",0));
        nd.add(new NeedData("소금","5g",0));
        recipes.add(new RecipeData("부추계란말이",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("돼지고기","600g",0));
        nd.add(new NeedData("마늘","50g",0));
        nd.add(new NeedData("된장","100g",0));
        nd.add(new NeedData("후추","100g",0));
        recipes.add(new RecipeData("수육",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("돼지고기","200g",0));
        nd.add(new NeedData("마늘","100g",0));
        nd.add(new NeedData("김치","50g",0));
        nd.add(new NeedData("양파","50g",0));
        nd.add(new NeedData("후추","50g",0));
        nd.add(new NeedData("대파","20g",0));
        nd.add(new NeedData("소시지","50g",0));
        recipes.add(new RecipeData("부대찌개",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("돼지고기","180g",0));
        nd.add(new NeedData("숙주","210g",0));
        nd.add(new NeedData("대파","50g",0));
        nd.add(new NeedData("마늘","50g",0));
        recipes.add(new RecipeData("돼지고기숙주볶음",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        
        // ===================| 재료 정보 |===================
        
        ArrayList<IngredientData> ingDat = new ArrayList<IngredientData>();
        ingDat.add(new IngredientData("김치","1000g","2022-11-31",0,0));	
        ingDat.add(new IngredientData("돼지고기","250g","2022-11-25",0,0));	
        ingDat.add(new IngredientData("마늘","120g","2022-12-05",0,0));
        ingDat.add(new IngredientData("계란","8","2022-11-28",0,0));	
        ingDat.add(new IngredientData("당근","150g","2022-11-29",0,0));
        ingDat.add(new IngredientData("어묵","250g","2022-12-22",0,0));
        ingDat.add(new IngredientData("설탕","10g","2023-01-30",0,0));
        ingDat.add(new IngredientData("청양고추","20g","2023-02-11",0,0));
        ingDat.add(new IngredientData("소고기","200g","2022-11-30",0,0));
        ingDat.add(new IngredientData("부추","150g","2022-12-10",0,0));
        ingDat.add(new IngredientData("숙주","210g","2022-12-29",0,0));
        ingDat.add(new IngredientData("소금","10g","2022-12-19",0,0));
        ingDat.add(new IngredientData("파","45g","2022-11-23",0,0));
        ingDat.add(new IngredientData("대파","80g","2022-12-02",0,0));
        ingDat.add(new IngredientData("양배추","100g","2022-12-06",0,0));
        ingDat.add(new IngredientData("후추","100g","2023-01-24",0,0));
        ingDat.add(new IngredientData("감자","80g","2022-11-31",0,0));
        ingDat.add(new IngredientData("미역","80g","2022-12-11",0,0));
        
        ArrayList<RecipeData> resData = new ArrayList<RecipeData>();
        Leftover_Food result = new Leftover_Food(ingDat,recipes);
        resData = result.get_Leftover_Food();
        
        for (int i = 0; i < resData.size(); i++) {
        	System.out.println("순위 " + String.valueOf(i+1) + " : " + resData.get(i).name);
        }
        
        
        
	}

}
