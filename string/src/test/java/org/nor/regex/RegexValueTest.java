package org.nor.regex;

import org.testng.annotations.Test;

public class RegexValueTest {
	
	@Test
	public void testgetPatternValue() {

		String value = RegexValue.getPatternValue("A BD 0XFGE9XZZZ", "([A-Z| ]+)([0-0][A-Z]+[0-9])", 2);
		System.out.println(value);
	}
	
}
