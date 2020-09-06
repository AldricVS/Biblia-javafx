package data;

public enum Categories {
	NATUROPATHIE("Naturopathie"),
	ROMAN("Roman"),
	MAGAZINE("Magazine");
	
	private String value;
	Categories(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static Categories fromValue(String value) {
		for (Categories category : Categories.values()) {
			if(category.getValue().equals(value)) {
				return category;
			}
		}
		throw new IllegalArgumentException(value + " is not a value of any Categories instances");
	}
}
