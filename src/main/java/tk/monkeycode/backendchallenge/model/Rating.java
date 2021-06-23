package tk.monkeycode.backendchallenge.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Rating {

	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
	
	private Integer rating;
	
	private Rating(Integer rating) {
		this.rating = rating;
	}
	
	@JsonValue
	public Integer getRating() {
		return rating;
	}
	
}
