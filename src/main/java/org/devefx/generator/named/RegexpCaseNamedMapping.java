package org.devefx.generator.named;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.devefx.generator.NamedMapping;

public class RegexpCaseNamedMapping implements NamedMapping {

	private Pattern pattern;
	
	private String replacement;
	
	public RegexpCaseNamedMapping(String regex, String replacement) {
		this.pattern = Pattern.compile(regex);
		this.replacement = replacement;
	}
	
	@Override
	public String mapping(String inputName) {
		Matcher matcher = pattern.matcher(inputName);
		if (matcher.find()) {
			return process(matcher.replaceAll(replacement));
		}
		return inputName;
	}

	private String process(String input) {
		Pattern pattern = Pattern.compile("_(.)");
		Matcher matcher = pattern.matcher(input);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buffer, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
}
