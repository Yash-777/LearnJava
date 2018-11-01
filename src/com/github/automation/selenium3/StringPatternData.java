package com.github.automation.selenium3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.wrappers.RegualrExpression_PatternMatcher;

public class StringPatternData {
	public static void main(String[] args) {
		// Saved Online at - https://regex101.com/r/BWlswu/1/
		String s = "[ A ] this is] a song [D] [Am] i am [ Adim ] Am [f] [[f] [f]] [Dmin] [ how] Parametarised[Sheet2], [1], [[2]]";
		
		String quotedText = Pattern.quote( "([\\[{1}])+([\\w+])+([\\]{1}])" );
							//("(\\[)+([\\w+]*)+(\\])");
		Pattern p = Pattern.compile( quotedText );
		Matcher matcher = p.matcher( s );
		
		boolean match = false, find = false;
		if ( matcher.matches() ) match = true;
		
		find = RegualrExpression_PatternMatcher.findFullGruopMatcher(matcher);
		System.out.format("\t Match[%s] Find[%s]\n", match, find);
	}
	/* https://www.regextester.com/97589
	 * [ A ] this is a song [D] [Am] i am [ Adim ] Am [f] [Dmin]
	 * Test:
	 * \[ [^\[\]]+ \] « With space [ A ]
	 * \[[^\[\]]+\]+  « with out space [D]
	 * 
	 * Single character « \[[A-Z0-9]+\] « \[[^]]*\]
	 * Multiple values     «  \[\w+\] « \\[.*?\\] « \[[^\[\]]+\]
	 * 	 * Yash « (\[)+(\w+)+(\]) « (\[)+([\w+]*)+(\])
	 * 
	 * A group may be excluded by adding ?: in the beginning.
	 * 
	 * Not followed by character - https://stackoverflow.com/a/31201710/5081877
	 * ([\[{1}](?!\[))+([\w+]*)+([\]{1}]) Input:[[2]] OutPut: [2]
	 * 
	 * Exact 3 of a - a{3}
	 * https://regex101.com/r/OFojVx/1/
	 * ([\\]+([']))
	 */
}
