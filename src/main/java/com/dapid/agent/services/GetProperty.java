package com.dapid.agent.services;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class GetProperty {
    private Properties properties;

    public GetProperty(Properties properties) {
        this.properties = properties;
    }

    public GetProperty(FindResourceFile findResourceFile) throws Exception {
        this(new Properties());
        FileInputStream in = new FileInputStream(findResourceFile.file().getAbsolutePath());
        properties.load(in);
        in.close();
    }

    public String value(String property) {
        String result = this.properties.getProperty(property);
        String patternOfProperty = "(\\$\\{(.*?)\\})";
        Pattern pattern = Pattern.compile(patternOfProperty);
        Matcher matcher = pattern.matcher(result);

        String matched = "";
        String matchedValue = "";
        int groupCount = matcher.groupCount();
        while (matcher.find()) {
            for (int i=2; i <= groupCount; i++) {
                matched = matcher.group(i);
                matchedValue = value(matched);
                result = replaceGroup(patternOfProperty, result ,1 ,matchedValue);
            }
        }
        return result;
    }

    private String replaceGroup(String regex, String source, int groupToReplace, String replacement) {
        return replaceGroup(regex, source, groupToReplace, 1, replacement);
    }

    private String replaceGroup(String regex, String source, int groupToReplace, int groupOccurrence, String replacement) {
        Matcher m = Pattern.compile(regex).matcher(source);
        for (int i = 0; i < groupOccurrence; i++)
            if (!m.find()) return source; // pattern not met, may also throw an exception here
        return new StringBuilder(source).replace(m.start(groupToReplace), m.end(groupToReplace), replacement).toString();
    }
}
