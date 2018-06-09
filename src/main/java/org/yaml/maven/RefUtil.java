package org.yaml.maven;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dpujari on 11/12/16.
 */
public class RefUtil {
    private static final Pattern PATTERN = Pattern.compile("\\s*(\\$ref:\\s*['\"]{0,1})([\\w/\\._-]+\\.yaml)['\"]{0,1}");

    public static RefPosition getPath(String refLine ) {
        Matcher matcher = PATTERN.matcher(refLine);
        if (matcher.matches()) {
            String path = matcher.group(2);
            if (!path.startsWith("#")) {
                return new RefPosition(path, matcher.start(1));
            }
        }
        return null;
    }
}
