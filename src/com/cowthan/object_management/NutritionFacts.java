package com.cowthan.object_management;

public class NutritionFacts {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	
	public NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}
	
	public static class Builder{
		
		//必填的参数，无默认值
		private final int servingSize;
		private final int servings;
		
		//选填的参数，有默认值
		private int calories = 0;
		private int fat      = 0;
		private int carbohydrate = 0;
		private int sodium = 0;
		
		public Builder(int servingSize, int servings){
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public Builder calories(int val){ calories = val; return this; }
		public Builder fat(int val){ fat = val; return this; }
		public Builder carbohydrate(int val){ carbohydrate = val; return this;}
		public Builder sodium(int val){ sodium = val; return this; }
	
		public NutritionFacts build(){
			return new NutritionFacts(this);
		}
		
	}
	
	public static void main(String[] args) {
		NutritionFacts cocacola = new NutritionFacts.Builder(240,  8)
				.calories(100)
				.sodium(35)
				.carbohydrate(27)
				.build();
	}
}
