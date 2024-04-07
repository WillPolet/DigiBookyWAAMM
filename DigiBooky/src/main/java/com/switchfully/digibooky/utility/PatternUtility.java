package com.switchfully.digibooky.utility;

import java.util.regex.Pattern;

public class PatternUtility {

    public static Pattern getPattern(String regex) {
        return Pattern.compile("^" + regex.replace("*", ".*") + "$");
    }
}
