package concurrency.udemy.immutable;

import java.util.ArrayList;
import java.util.List;

public final class Product {

	private final String code;
	private final String name;
	private final List<String> ingredients;

	public Product(String code, String name, List<String> ingredients) {
		this.code = code;
		this.name = name;
		this.ingredients = new ArrayList<String>(ingredients);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public List<String> getIngredients() {
		return new ArrayList<>(ingredients);
	}

}
