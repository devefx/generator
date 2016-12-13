package org.devefx.generator.named;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.devefx.generator.NamedMapping;

public class UnderscoreToCamelCaseNamedMapping implements NamedMapping {

	private Pattern pattern;
	
	public UnderscoreToCamelCaseNamedMapping() {
		this.pattern = Pattern.compile("_(.)");
	}
	
	@Override
	public String mapping(String inputName) {
		Matcher matcher = pattern.matcher(inputName);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buffer, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
}
