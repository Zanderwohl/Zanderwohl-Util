package com.zanderwohl.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A wrapper class for text files.
 */
public class FileLoader {

    private String fullPath, path, name, extension, contents;
    private boolean isResource;
    IOException keepError = null;

    /**
     * Open a file based on its name. Cannot handle directories.
     * //TODO: handle directories.
     * @param fileName The name of the file, with its extension.
     * @throws FileNotFoundException If the file is not found.
     */
    public FileLoader(String fileName) {
        this(fileName, false);
    }

    /**
     * Open a file based on its name.
     * @param filePath // TODO ???
     * @param fromResources // If true, will get file from resources folder.
     */
    public FileLoader(String filePath, boolean fromResources){
        isResource = fromResources;
        fullPath = filePath;
        String[] nameAndExtension = filePath.split("\\.");
        path = nameAndExtension[0];
        String[] directories = path.split("[\\\\/]"); // Forward or back-slash.
        name = directories[directories.length - 1];
        extension = nameAndExtension[1];
        contents = null;
    }

    /**
     * The name of the file with its extension.
     * @return The full name of the file.
     */
    public String getFullName(){
        return fullPath;
    }

    /**
     * The name of the file, without the extension.
     * @return The name of the file.
     */
    public String getName(){
        return name;
    }

    /**
     * Get the file extension of this file.
     * @return The extension without a .
     */
    public String getExtension(){
        return extension;
    }

    /**
     * Get the full path, including file name and exension.
     * @return The path as given in the constructor.
     */
    public String getFullPath(){
        return fullPath;
    }

    /**
     * Returns the content of the file in a single string with line breaks.
     * @return A string containing the entire contents of the file.
     */
    public String getFile() throws IOException{
        if(keepError != null){
            throw keepError;
        }
        if(contents == null){
            if(isResource){
                contents = resourceFileToString();
            }
            else {
                contents = fileToString();
            }
        }
        return contents;
    }

    /**
     * Get a resource file as a string.
     * @return A string containing the entire contents of the file.
     */
    private String resourceFileToString() {
        String _contents = "";
        try(InputStream in = FileLoader.class.getResourceAsStream(fullPath);
            Scanner scan = new Scanner (in, StandardCharsets.UTF_8.name())){
            _contents = scan.useDelimiter("\\A").next();
        }
        catch (IOException e){
            keepError = e; // yuck yuck yuck yuck state. Enterprisey garbage.
        }
        return _contents;
    }

    /**
     * Returns the content of the file if available, an empty string if not.
     * @return A string containing the contents of the file or empty string.
     */
    public String getFileSafe(){
        try {
            return getFile();
        } catch (FileNotFoundException e){
            return "";
        } catch (IOException e){
            return "";
        }
    }

    /**
     * Converts the file specified to a single string, with line breaks.
     * @return A string containing the entire contents of the file.
     * @throws FileNotFoundException If the file specified is not found.
     */
    private String fileToString() throws FileNotFoundException {
        String fileContents = "";
        Scanner file = new Scanner(new File(getFullName()));
        while (file.hasNext()) {
            fileContents += file.nextLine() + "\n";
        }
        file.close();
        return fileContents;
    }

    public List<String> fileToList() throws IOException {
        List<String> list = new ArrayList<>();
        String fileStuff = getFile();
        String[] lines = fileStuff.split("\n");
        for(String line: lines){
            list.add(line);
        }
        return list;
    }


}
