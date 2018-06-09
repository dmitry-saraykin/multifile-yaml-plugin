package org.yaml.maven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by dpujari on 11/11/16.
 */
public class YamlMerger {

    File sourceFilePath;
    File destFilePath;

    public YamlMerger() {
    }

    public YamlMerger(final File sourceFile, File destinationFile) {
        this.sourceFilePath = sourceFile;
        this.destFilePath = destinationFile;
    }

    /**
     * Merge the yaml files starting with sourceFilePath
     *
     * @return
     */
    public void merge() throws IOException {
        FileWriter fw = new FileWriter(destFilePath);
        resolve(sourceFilePath.toString(), FileUtils.readFileToString(sourceFilePath), fw, "");
        fw.close();

    }

    /**
     * start reading the source file line by line
     * if line starts with $ref.
     * get the parentDir and file name
     * call resolve()
     * else
     * write to dest file.
     *
     * @param parentDir
     * @param sourceFilePath
     */
    private void resolve(String currentPath, String content, final FileWriter fw, String indentation) throws IOException {
        for (String line : content.split("\n")) {
            //String indent="";
            RefPosition path = RefUtil.getPath(line);
            if (path != null) {
                String indent = indentation + StringUtils.leftPad("", path.getIndentation());

                String remoteRefValue = path.getPath();
                String parent = new File(currentPath).getParent();
                File file = new File(parent + remoteRefValue);
                if (file.exists()) {
                    resolve(remoteRefValue, FileUtils.readFileToString(file), fw, indent);
                } else {
                    try (InputStream inputStream = YamlMerger.class.getResourceAsStream(remoteRefValue)) {
                        if (inputStream == null) {
                            throw new RuntimeException("Not found file " + remoteRefValue + " referenced from " + currentPath);
                        }
                        resolve(remoteRefValue, IOUtils.toString(inputStream), fw, indent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                try {
                    fw.write(String.format("%s%s%n", indentation, line));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
