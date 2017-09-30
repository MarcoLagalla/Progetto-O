
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */
public class RegexMatcher extends BaseMatcher{
    private final String regex;

    public RegexMatcher(String regex){
        this.regex = regex;
    }

    @Override
    public boolean matches(Object o){
        return ((String)o).matches(regex);

    }

    @Override
    public void describeTo(Description description){
        description.appendText("matches regex=");
    }
    
    public static RegexMatcher matches(String regex){
        return new RegexMatcher(regex);
    }

}

