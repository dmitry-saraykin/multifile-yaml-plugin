package org.yaml.maven;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class YamlMergerTest {
    String projectDir = System.getProperty("user.dir");
    private File destYamlFile;

    @BeforeMethod
    public void before() throws Exception {
        destYamlFile = File.createTempFile("temp", ".yaml");
        destYamlFile.deleteOnExit();
    }

    @AfterMethod
    public void after() {
        destYamlFile.delete();
    }
    
    @Test
    public void testThatYamlFileIsGenerated() throws IOException, URISyntaxException {
        File sourceIndexFile = new File(YamlMergerTest.class.getResource("/swagger/index.yaml").toURI());
        
        new YamlMerger(sourceIndexFile, destYamlFile).merge();

        String result = FileUtils.readFileToString(destYamlFile);
        assertEquals(result, FileUtils.readFileToString(new File(YamlMergerTest.class.getResource("/expected.yaml").toURI())));
    }

}
