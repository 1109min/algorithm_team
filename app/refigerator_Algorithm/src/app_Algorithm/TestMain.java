package app_Algorithm;

import java.util.*;

import data_structure.*;
import app_Algorithm.IngredientDataTransform;

public class TestMain {

	public static void main(String[] args) {
		
		// ===================| ������ ���� |===================
		ArrayList<RecipeData> recipes = new ArrayList<RecipeData>();
		ArrayList<NeedData> nd = new ArrayList<NeedData>();
		
		nd.add(new NeedData("��ġ","300g",0));
		nd.add(new NeedData("�������","100g",0));
		nd.add(new NeedData("����","30g",0));
		nd.add(new NeedData("����","20g",0));
		recipes.add(new RecipeData("��ġ�",new ArrayList<NeedData>(nd),0,0,0));
		nd.clear();
		
        nd.add(new NeedData("����","300g",0));
        nd.add(new NeedData("����","300g",0));
        nd.add(new NeedData("�ұ�","5g",0));
        nd.add(new NeedData("����","20g",0));
        nd.add(new NeedData("û�����","40g",0));
        nd.add(new NeedData("����","5g",0));
        nd.add(new NeedData("��ȣ��","100g",0));
        nd.add(new NeedData("����","50g",0));
        recipes.add(new RecipeData("�����",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�����","50g",0));
        nd.add(new NeedData("��","30g",0));
        nd.add(new NeedData("���","3",0));
        nd.add(new NeedData("�ұ�","5",0));
        recipes.add(new RecipeData("��������",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�","100g",0));
        nd.add(new NeedData("��","100g",0));
        nd.add(new NeedData("����","100g",0));
        nd.add(new NeedData("�ұ�","5g",0));
        nd.add(new NeedData("����","5g",0));
        nd.add(new NeedData("����","20g",0));
        nd.add(new NeedData("û�����","20g",0));
        recipes.add(new RecipeData("���",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("��¡��","600g",0));
        nd.add(new NeedData("�����","100g",0));
        nd.add(new NeedData("���","200g",0));
        nd.add(new NeedData("����","150g",0));
        nd.add(new NeedData("��","100g",0));
        nd.add(new NeedData("����","20g",0));
        nd.add(new NeedData("û�����","20g",0));
        nd.add(new NeedData("����","10g",0));
        recipes.add(new RecipeData("��¡���",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�߰�����","50g",0));
        nd.add(new NeedData("����","100g",0));
        nd.add(new NeedData("�ұ�","5g",0));
        nd.add(new NeedData("�ް�","3",0));
        nd.add(new NeedData("��","20g",0));
        recipes.add(new RecipeData("�߰�������",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�̿�","20g",0));
        nd.add(new NeedData("�Ұ��","100g",0));
        nd.add(new NeedData("�ұ�","5g",0));
        nd.add(new NeedData("����","20g",0));
        recipes.add(new RecipeData("�̿���",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�κ�","100g",0));
        nd.add(new NeedData("��ġ","100g",0));
        nd.add(new NeedData("�������","250g",0));
        nd.add(new NeedData("����","100g",0));
        nd.add(new NeedData("���","20g",0));
        nd.add(new NeedData("����","20g",0));
        nd.add(new NeedData("û�����","20g",0));
        nd.add(new NeedData("����","10g",0));
        recipes.add(new RecipeData("�κα�ġ",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("����","100g",0));
        nd.add(new NeedData("���","4",0));
        nd.add(new NeedData("�ұ�","5g",0));
        recipes.add(new RecipeData("���߰������",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�������","600g",0));
        nd.add(new NeedData("����","50g",0));
        nd.add(new NeedData("����","100g",0));
        nd.add(new NeedData("����","100g",0));
        recipes.add(new RecipeData("����",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�������","200g",0));
        nd.add(new NeedData("����","100g",0));
        nd.add(new NeedData("��ġ","50g",0));
        nd.add(new NeedData("����","50g",0));
        nd.add(new NeedData("����","50g",0));
        nd.add(new NeedData("����","20g",0));
        nd.add(new NeedData("�ҽ���","50g",0));
        recipes.add(new RecipeData("�δ��",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        nd.add(new NeedData("�������","180g",0));
        nd.add(new NeedData("����","210g",0));
        nd.add(new NeedData("����","50g",0));
        nd.add(new NeedData("����","50g",0));
        recipes.add(new RecipeData("���������ֺ���",new ArrayList<NeedData>(nd),0,0,0));
        nd.clear();
        
        
        // ===================| ��� ���� |===================
        
        ArrayList<IngredientData> ingDat = new ArrayList<IngredientData>();
        ingDat.add(new IngredientData("��ġ","1000g","2022-11-31",0,0));	
        ingDat.add(new IngredientData("�������","250g","2022-11-25",0,0));	
        ingDat.add(new IngredientData("����","120g","2022-12-05",0,0));
        ingDat.add(new IngredientData("���","8","2022-11-28",0,0));	
        ingDat.add(new IngredientData("���","150g","2022-11-29",0,0));
        ingDat.add(new IngredientData("�","250g","2022-12-22",0,0));
        ingDat.add(new IngredientData("����","10g","2023-01-30",0,0));
        ingDat.add(new IngredientData("û�����","20g","2023-02-11",0,0));
        ingDat.add(new IngredientData("�Ұ��","200g","2022-11-30",0,0));
        ingDat.add(new IngredientData("����","150g","2022-12-10",0,0));
        ingDat.add(new IngredientData("����","210g","2022-12-29",0,0));
        ingDat.add(new IngredientData("�ұ�","10g","2022-12-19",0,0));
        ingDat.add(new IngredientData("��","45g","2022-11-23",0,0));
        ingDat.add(new IngredientData("����","80g","2022-12-02",0,0));
        ingDat.add(new IngredientData("�����","100g","2022-12-06",0,0));
        ingDat.add(new IngredientData("����","100g","2023-01-24",0,0));
        ingDat.add(new IngredientData("����","80g","2022-11-31",0,0));
        ingDat.add(new IngredientData("�̿�","80g","2022-12-11",0,0));
        
        ArrayList<RecipeData> resData = new ArrayList<RecipeData>();
        Leftover_Food result = new Leftover_Food(ingDat,recipes);
        resData = result.get_Leftover_Food();
        
        for (int i = 0; i < resData.size(); i++) {
        	System.out.println("���� " + String.valueOf(i+1) + " : " + resData.get(i).name);
        }
        
        
        
	}

}
