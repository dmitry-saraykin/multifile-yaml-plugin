String result = new File( basedir, "target/yaml/result.yaml").getText('UTF-8')
String expected = new File( basedir, "expected.yaml").getText('UTF-8')

assert result == expected