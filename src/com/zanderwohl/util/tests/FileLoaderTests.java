package com.zanderwohl.util.tests;

import com.zanderwohl.util.FileLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class FileLoaderTests {

    private FileLoader file;
    private FileLoader resource;

    private static String expectedPath;
    private static String expectedName;
    private static String expectedFullName;
    private static String expectedExtension;
    private static String expectedFullPath;
    private static String expectedContents;
    private static String[] expectedContentsArray;

    @BeforeEach
    public void init() throws NoSuchFieldException, IllegalAccessException {
        expectedExtension = "ext";
        expectedName = "myfile";
        expectedPath = "/something/folder/other/";
        expectedFullPath = expectedPath + expectedName + "." + expectedExtension;
        file = new FileLoader(expectedFullPath);
        expectedFullName = expectedName + "." + expectedExtension;
        resource = new FileLoader(expectedFullName, true);

        String line1 = "This is the first line of the content";
        String line2 = "This is the second line of the content";
        String line3 = "This is the third line of the content";
        expectedContents = line1 + "\n" + line2 + "\n" + line3 + "\n";
        expectedContentsArray = new String[]{ line1, line2, line3 };
    }

    @Test
    public void testFileHasCorrectName(){
        assertEquals(expectedName, file.getName());
    }

    @Test
    public void testResourceHasCorrectName(){
        assertEquals(expectedName, resource.getName());
    }

    @Test
    public void testFileHasCorrectfullPath(){
        assertEquals(expectedFullPath, file.getFullPath());
    }

    @Test
    public void testResourceHasCorrectFullPath(){
        assertEquals(expectedFullName, resource.getFullPath());
    }

    @Test
    public void testFileHasCorrectExtension(){
        assertEquals(expectedExtension, file.getExtension());
    }

    @Test
    public void testResourceHasCorrectExtension(){
        assertEquals(expectedExtension, resource.getExtension());
    }

}
