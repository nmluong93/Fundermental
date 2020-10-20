package concurrency.udemy.immutable;

import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) {
		List<String> ingredients = new ArrayList<String>() {
			{
				add("Apple");
				add("Water melon");
			}
		};
		final Product product = new Product("123", "Pro1", ingredients);
		ingredients.clear();
		
		System.out.println(product.getIngredients());
		
		product.getIngredients().clear();
		
		System.out.println(product.getIngredients());
	}
}
