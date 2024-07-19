package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	public static List<String> getImagesLink(String textAreaStr) {
		List<String> links = new ArrayList<>();
		Pattern pattern = Pattern.compile("https?://\\S+");
		Matcher matcher = pattern.matcher(textAreaStr);

		while (matcher.find()) {
			links.add(matcher.group());
		}

		return links;
	}

}
