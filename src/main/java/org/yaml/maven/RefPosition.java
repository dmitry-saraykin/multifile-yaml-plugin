package org.yaml.maven;

public class RefPosition {
    private final String path;
    private final int indentation;

    public RefPosition(String path, int indentation) {
        this.path = path;
        this.indentation = indentation;
    }

    public int getIndentation() {
        return indentation;
    }
    
    public String getPath() {
        return path;
    }
}
