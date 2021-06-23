package tk.monkeycode.backendchallenge.util;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.backendchallenge.model.Order;

@Slf4j
public class StringToOrderEnumConverter implements Converter<String, Order> {

	@Override
	public Order convert(String source) {
		try {
			return Order.valueOf(source.toUpperCase());
		} catch(IllegalArgumentException e) {
			log.error("{}", e.getMessage());
			return null;
		}
	}


}
