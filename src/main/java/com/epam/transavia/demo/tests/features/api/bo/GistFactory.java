package com.epam.transavia.demo.tests.features.api.bo;

import java.util.HashMap;
import java.util.Map;

public class GistFactory {

    private static final String NEW_FILE_NAME = "fileTest.txt";
    private static final String NEW_DESCRIPTION = "test gist description";
    private static final String NEW_CONTENT = "Test content";

    private static final String UPDATE_CONTENT = "Updated File Content";
    private static final String UPDATE_DESCRIPTION = "updated gist description";
    private static final String NEW_PATCH_FILE_NAME = "fileNew.txt";
    private static final String NEW_PATCH_FILE_CONTENT = "New Patch File Content";


    public static Gist createNewGist() {
        Gist gist = new Gist();
        gist.setDescription(NEW_DESCRIPTION);
        gist.setIsPublic(true);
        gist.setFiles(createFileWithContent(NEW_FILE_NAME, NEW_CONTENT));
        return gist;
    }

    private static Map<String, Map<String, String>> createFileWithContent(String fileName, String content) {
        Map<String, String> fileContent = new HashMap<String, String>() {{
            put("content", content);
        }};
        Map<String, Map<String, String>> files = new HashMap<>();
        files.put(fileName, fileContent);
        return files;
    }

    /**
     * patch JSON must be of the following format:
     * { "description": "the description for this gist",
     * "files": {"file1.txt": { "content": "updated file contents"  },
     * "old_name.txt": { "filename": "new_name.txt", "content": "modified contents" },
     * "new_file.txt": {"content": "a new file"},"delete_this_file.txt": null  } }
     */
    public static Gist createGistUpdateFileContentAndNewFile() {
        Gist gist = new Gist();
        gist.setDescription(UPDATE_DESCRIPTION);
        Map<String, Map<String, String>> files = createFileWithContent(NEW_FILE_NAME, UPDATE_CONTENT); //to update existing file

        Map<String, String> newFileContent = new HashMap<String, String>() {{
            put("content", NEW_PATCH_FILE_CONTENT);
        }};
        files.put(NEW_PATCH_FILE_NAME, newFileContent); //to add a new file

        gist.setFiles(files);
        return gist;
    }

}
