package com.github.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Map;
import java.util.Scanner;

/**
 * A line is a sequence of characters ending with either a carriage-return character ('\u005Cr')
 * or a newline character ('\u005Cn'). In addition, a carriage-return character followed 
 * immediately by a newline character is treated as a single end-of-line token. 
 * 
 * @author yashwanth.m
 *
 */
public class File_WordCount {
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./lib/RequiredJarFiles.txt");
		
		// totalWordCount( file );
		repeadedWordCount(file);
	}
	/**
	 * The StreamTokenizer class takes an input stream and parses it into "tokens",
	 * allowing the tokens to be read one at a time.
	 * <ul>In addition, an instance has four flags. These flags indicate:
		<li>Whether line terminators are to be returned as tokens or treated as white space 
		that merely separates tokens.</li>
		<li>Whether C-style comments are to be recognized and skipped.</li>
		<li>Whether C++-style comments are to be recognized and skipped.</li>
		<li>Whether the characters of identifiers are converted to lowercase.</li>
		</ul>
	 * @param file
	 */
	public static Map<String, Integer> repeadedWordCount( File file ) {
		try { // https://stackoverflow.com/a/18608136/5081877
			FileReader reader = new FileReader( file );
			/*FileInputStream fis = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fis, Charset.forName("UTF-8"));*/
			BufferedReader in = new BufferedReader( reader );
			StreamTokenizer st = new StreamTokenizer(in);
			st.eolIsSignificant(false); // line terminators treated as white space.
			st.slashSlashComments( false ); // C++-style comments [//]
			st.slashStarComments(true); // C-style comments [/*  */]
			
			while(st.nextToken() != StreamTokenizer.TT_EOF) {
				if (st.ttype == StreamTokenizer.TT_NUMBER) {
					System.out.println("Numbers : "+(int)st.nval);
				} else if ( st.ttype == StreamTokenizer.TT_WORD ) {
					System.out.println("Strings : "+st.sval);
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("resource")
	public static int totalWordCount( File file ) {
		int count = 0;
		try {
			Scanner sc = new Scanner( file ).useDelimiter("d*[\r\n|\r|\n]");
		
			while (sc.hasNext()) {
				String next = sc.next();
				System.out.println( next );
				String[] s = next.split(" "); //("(\r\n|\r|\n)", -1);
				
				for (int i = 0; i < s.length; i++) {
					if (!s[i].isEmpty()){
						System.out.println(s[i]);
						count++;
					}
				}
			}
			sc.close();
			System.out.println("Word-Count : "+count);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return count;
	}
}