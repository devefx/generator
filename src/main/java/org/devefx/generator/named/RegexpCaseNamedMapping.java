package org.devefx.generator.named;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.devefx.generator.NamedMapping;

public class RegexpCaseNamedMapping implements NamedMapping {

	private Pattern pattern;
	
	private String replacement;

	private boolean isCamelCase;

	public RegexpCaseNamedMapping(String regex, String replacement) {
		this(regex, replacement, true);
	}

	public RegexpCaseNamedMapping(String regex, String replacement, boolean isCamelCase) {
		this.pattern = Pattern.compile(regex);
		this.replacement = replacement;
		this.isCamelCase = isCamelCase;
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
			String name = matcher.group(1);
			if (isCamelCase) {
				name = name.toUpperCase();
			} else {
				name = "_" + name;
			}
			matcher.appendReplacement(buffer, name);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
}
