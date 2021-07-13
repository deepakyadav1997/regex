package regex;

import org.junit.Test;
import regex.compiler.*;

import static org.junit.Assert.assertEquals;

public class RegexTests {

    @Test
    public void regexTestSimple(){
        assertEquals(true, Regex.match("a","a"));
        assertEquals(false, Regex.match("","a"));
    }

    @Test
    public void regexTestOneOrMore() {
        assertEquals(false, Regex.match("aaa","b+"));
        assertEquals(true, Regex.match("a","a+"));
        assertEquals(true, Regex.match("aaaaa","a+"));
        assertEquals(false, Regex.match("","a+"));
    }
    @Test
    public void regexTestZeroOrMore() {

        assertEquals(true, Regex.match("aaa","a?aa"));
        assertEquals(true, Regex.match("a","a?"));
        assertEquals(true, Regex.match("","a?"));
    }
    @Test
    public void regexTestLongWords(){
        assertEquals(true,
                Regex.match("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        "a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a*aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        assertEquals(false,
                Regex.match("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        "a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?a?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }
    @Test
    public void regexTestKleeneStar(){
        assertEquals(true, Regex.match("aaa","a*"));
        assertEquals(false, Regex.match("aba","a*"));
        assertEquals(true, Regex.match("aaaaaaaaaaaaaa","a*"));
        assertEquals(true, Regex.match("","a*"));
    }

    @Test
    public void regexTestOr(){
        assertEquals(true, Regex.match("c","(a|b)*c"));
        assertEquals(true, Regex.match("abaac","(a|b)*c"));

    }
    @Test
    public void regexTestOr2(){
        assertEquals(true, Regex.match("def","abc|def"));
        assertEquals(true, Regex.match("abc","abc|def"));
        assertEquals(false, Regex.match("fed","abc|def"));

    }
}
