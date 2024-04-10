package com.switchfully.digibooky.utility;

import java.util.regex.Pattern;

public class PatternUtility {

    public static Pattern getPattern(String searchText) {
        return Pattern.compile("^" + searchText.replace("*", ".*") + "$");
    }
}
