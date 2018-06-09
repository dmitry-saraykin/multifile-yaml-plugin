package org.yaml.maven;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RefUtilTest {

    @Test(dataProvider = "refLines")
    public void parse(String line, String result, int indentation) throws Exception {
        RefPosition path = RefUtil.getPath(line);
        assertEquals(path.getPath(), result);
        assertEquals(path.getIndentation(), indentation);
    }
    
    @Test(dataProvider = "noRefLines")
    public void parseNull(String line) throws Exception {
        assertNull(RefUtil.getPath(line));
    }
    
    @DataProvider
    private Object[][] refLines() {
        return new Object[][] {
            {"$ref: \"/test.yaml\"", "/test.yaml", 0},
            {"$ref: '/test.yaml'", "/test.yaml", 0},
            {"  $ref: '/test.yaml'", "/test.yaml", 2},
            {"  $ref: /test.yaml", "/test.yaml", 2}
        };
    }
    
    @DataProvider
    private Object[][] noRefLines() {
        return new Object[][] {
            {"test"},
            {"test:  $ref: '/test.yaml'"},
            {"$ref: '/test'"}
        };
    }
}
