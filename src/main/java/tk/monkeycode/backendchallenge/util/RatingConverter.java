package tk.monkeycode.backendchallenge.util;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import tk.monkeycode.backendchallenge.model.Rating;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Rating attribute) {
		if (attribute  == null)
			return null;
		return attribute.getRating();
	}

	@Override
	public Rating convertToEntityAttribute(Integer dbData) {
		if (dbData == null)
			return null;
		return Stream.of(Rating.values()).filter(r -> r.getRating().equals(dbData)).findFirst().orElseThrow(IllegalArgumentException::new);
	}

}
