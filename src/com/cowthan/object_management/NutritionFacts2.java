package com.cowthan.object_management;

public class NutritionFacts2 {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	
	public NutritionFacts2(MyBuilder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}
	
	public static class MyBuilder implements Builder<NutritionFacts2>{
		
		//必填的参数，无默认值
		private final int servingSize;
		private final int servings;
		
		//选填的参数，有默认值
		private int calories = 0;
		private int fat      = 0;
		private int carbohydrate = 0;
		private int sodium = 0;
		
		public MyBuilder(int servingSize, int servings){
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public MyBuilder calories(int val){ calories = val; return this; }
		public MyBuilder fat(int val){ fat = val; return this; }
		public MyBuilder carbohydrate(int val){ carbohydrate = val; return this;}
		public MyBuilder sodium(int val){ sodium = val; return this; }
	
		public NutritionFacts2 build(){
			return new NutritionFacts2(this);
		}
		
	}
	
	public static void main(String[] args) {
		Builder<NutritionFacts2> builder = new NutritionFacts2.MyBuilder(240,  8)
				.calories(100)
				.sodium(35)
				.carbohydrate(27);
		
		NutritionFacts2 cocacola = builder.build();
	}
}
