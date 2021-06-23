package tk.monkeycode.backendchallenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tk.monkeycode.backendchallenge.util.StringToOrderEnumConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	 @Override
	    public void addFormatters(FormatterRegistry registry) {
	        registry.addConverter(new StringToOrderEnumConverter());
	    }
	 
}
