package data;

public enum Categories {
	NATUROPATHIE("Naturopathie"),
	ROMAN("Roman"),
	MAGAZINE("Magazine"),
	CUISINE("Cuisine"),
	POESIE("Poésie"),
	JARDINAGE("Jardinage"),
	THEATRE("Théatre"),
	LANGUE_ETRANGERE("Langue étrangère"),
	AUTRE("Autre");
	
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
