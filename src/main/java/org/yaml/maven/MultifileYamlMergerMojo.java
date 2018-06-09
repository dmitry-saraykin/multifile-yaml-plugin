package org.yaml.maven;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "merge-yaml", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class MultifileYamlMergerMojo extends AbstractMojo {
    /**
     * Location of the main yaml file
     */
    @Parameter(property = "sourceFile", required = true)
    private File sourceFile;
    /**
     * Target where all files will be merged to
     */
    @Parameter(property = "targetFile", required = true)
    private File targetFile;
    
    @Parameter(property = "project", required = true, readonly = true)
    protected MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            if (!sourceFile.exists()) {
                throw new MojoExecutionException("sourceFile not found: " + sourceFile);
            }
            if (!targetFile.exists()) {
                FileUtils.forceMkdir(targetFile.getParentFile());
                FileUtils.touch(targetFile);
            }
            new YamlMerger(sourceFile, targetFile).merge();
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
