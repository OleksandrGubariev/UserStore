package com.gubarev.usersstore.web.templater;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


public class PageGenerator {
    private static final String HTML_DIR = "webapp/";

    private static PageGenerator pageGenerator;


    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename) {
        return getPage(filename, null);
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        String contentString;
        String fileContent = fileContentToString(filename);
        if (data == null) {
            contentString = fileContent;
        } else {

            int indexStartLoopContent = fileContent.indexOf("<#list");
            int indexEndLoopContent = fileContent.indexOf("</#list>");
            StringBuilder content = new StringBuilder();
            while (indexStartLoopContent != -1 && indexEndLoopContent != -1) {

                content.append(fileContent, 0, fileContent.indexOf("<#list"));

                String contentToReplaceWithProperty = fileContent.substring(indexStartLoopContent, fileContent.indexOf("</#list>"));
                String objectKey = contentToReplaceWithProperty.substring(contentToReplaceWithProperty.indexOf(" "), contentToReplaceWithProperty.indexOf("as"));

                List listObjects = (List) data.get(objectKey.trim());
                if (listObjects == null) {
                    throw new RuntimeException("Object with key: " + objectKey + " not found");
                }
                for (Object entry : listObjects) {
                    if (entry != null) {
                        String contentToReplace = contentToReplaceWithProperty.substring(contentToReplaceWithProperty.indexOf(">") + 1);
                        content.append(toReplaceContent(contentToReplace, entry)).toString();
                    }
                }
                fileContent = fileContent.substring(fileContent.indexOf("</#list>".trim()) + 8);
                indexStartLoopContent = fileContent.indexOf("<#list");
                indexEndLoopContent = fileContent.indexOf("</#list>");
            }
            content.append(fileContent);

            contentString = content.toString();
            int indexStartReplaceContent = contentString.indexOf("${");
            int indexEndReplaceContent = contentString.indexOf("}");
            while (indexStartReplaceContent != -1 && indexEndReplaceContent != -1) {
                String objectProperty = contentString.substring(indexStartReplaceContent + 2, indexEndReplaceContent);
                String[] property = objectProperty.split("\\.");
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    if (entry != null) {
                        if (entry.getKey().equalsIgnoreCase(property[0].trim())) {
                            contentString = toReplaceContent(content.toString(), entry.getValue());
                        }
                    }
                }
                indexStartReplaceContent = contentString.indexOf("${");
                indexEndReplaceContent = contentString.indexOf("}");
            }
        }
        try {
            stream.write(contentString);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }


    private String fileContentToString(String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = PageGenerator.class.getClassLoader().getResourceAsStream(HTML_DIR + filename)) {
            if(inputStream == null){
                throw new RuntimeException("File not found");
            }
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = fileReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    private String toReplaceContent(String contentToReplace, Object entry) {
        StringBuilder replacedContent = new StringBuilder();
        int startContentToReplace = contentToReplace.indexOf("${");
        int endContentToReplace = contentToReplace.indexOf("}");
        while (startContentToReplace != -1 && endContentToReplace != -1) {
            String stringToReplace = contentToReplace.substring(startContentToReplace + 2, endContentToReplace).trim();
            replacedContent.append(contentToReplace, 0, startContentToReplace);
            String[] wordsToReplace = stringToReplace.split("\\.");
            try {
                Method method = entry.getClass().getDeclaredMethod(wordsToReplace[1].trim());
                method.setAccessible(true);
                replacedContent.append(method.invoke(entry).toString());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            contentToReplace = contentToReplace.substring(endContentToReplace + 1);
            startContentToReplace = contentToReplace.indexOf("${");
            endContentToReplace = contentToReplace.indexOf("}");
        }
        replacedContent.append(contentToReplace);
        return replacedContent.toString();
    }
}