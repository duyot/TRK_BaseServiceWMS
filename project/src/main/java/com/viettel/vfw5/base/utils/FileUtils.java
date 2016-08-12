/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thangdd8@viettel.com.vn
 * @since Apr 12, 2010
 * @version 1.0
 */
public class FileUtils {

    /** .*/
    private static final int BUFFER_SIZE = 1024 * 8;
    /** .*/
    private static final int COPY_BUFFER_SIZE = 1024 * 1024;

    private FileUtils() {
    }

    /**
     * saveFile
     * @param f f
     * @param fileName fileName
     * @param desDir desDir
     * @throws IOException IOException
     */
    public static void saveFile(File f, String fileName, File desDir) throws IOException {
        InputStream stream = new FileInputStream(f);
        OutputStream out = new FileOutputStream(desDir.getAbsolutePath() + File.separator + fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((bytesRead = stream.read(buffer, 0, BUFFER_SIZE)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        stream.close();
        out.close();
    }

    /**
     * Reads a text file.
     *
     * @param fileName the name of the text file
     * @return the lines of the text file
     * @throws IOException when file could not be read.
     */
    public static String[] readTextFile(String fileName)
            throws IOException {
        return readTextFile(new File(fileName));
    }

    /**
     * Reads a text file.
     *
     * @param file the text file
     * @return the lines of the text file
     * @throws IOException when file could not be read.
     */
    public static String[] readTextFile(File file)
            throws IOException {
        ArrayList lines = new ArrayList();
        BufferedReader in = new BufferedReader(new FileReader(file));

        String line;
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }
        in.close();
        return (String[]) lines.toArray(new String[lines.size()]);
    }

    /**
     * Reads a text file.
     *
     * @param file the text file
     * @param encoding the encoding of the textfile
     * @return the lines of the text file
     * @throws IOException when file could not be read.
     */
    public static String[] readTextFile(File file, String encoding)
            throws IOException {
        return readTextFile(new FileInputStream(file), encoding);
    }

    /**
     * Reads the text from the given input stream in the default encoding.
     *
     * @param in the input stream
     * @return the text contained in the stream
     * @throws IOException when stream could not be read.
     */
    public static String[] readTextFile(InputStream in)
            throws IOException {
        return readTextFile(in, null);
    }

    /**
     * Reads the text from the given input stream in the default encoding.
     *
     * @param in the input stream
     * @param encoding the encoding of the textfile
     * @return the text contained in the stream
     * @throws IOException when stream could not be read.
     */
    public static String[] readTextFile(InputStream in, String encoding)
            throws IOException {
        ArrayList lines = new ArrayList();
        BufferedReader bufferedIn;
        if (encoding != null) {
            bufferedIn = new BufferedReader(new InputStreamReader(in, encoding));
        } else {
            bufferedIn = new BufferedReader(new InputStreamReader(in));
        }
        String line;
        while ((line = bufferedIn.readLine()) != null) {
            lines.add(line);
        }
        bufferedIn.close();
        in.close();
        return (String[]) lines.toArray(new String[lines.size()]);
    }

    /**
     * Writes (and creates) a text file.
     * @param file the file to which the text should be written
     * @param lines the text lines of the file in a collection with String-values
     * @throws IOException when there is an input/output error during the saving
     */
    public static void writeTextFile(File file, Collection lines)
            throws IOException {
        writeTextFile(file, (String[]) lines.toArray(new String[lines.size()]));
    }

    /**
     * Writes (and creates) a text file.
     * @param file the file to which the text should be written
     * @param lines the text lines of the file
     * @throws IOException when there is an input/output error during the saving
     */
    public static void writeTextFile(File file, String[] lines)
            throws IOException {
        File parentDir = file.getParentFile();
        if ((parentDir != null) && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        PrintWriter out = new PrintWriter(new FileWriter(file));
        for (int i = 0; i < lines.length; i++) {
            out.println(lines[i]);
        }
        out.close();
    }

    /**
     * Copies the given files to the specified target directory.
     * @param files The files which should be copied, when an array element is null, it will be ignored.
     * @param targetDir The directory to which the given files should be copied to.
     * @throws IOException when there is an error while copying the file.
     */
    public static void copy(File[] files, File targetDir)
            throws IOException {
        copy(files, targetDir, false);
    }

    /**
     * Copies the given files to the specified target directory.
     *
     * @param files The files which should be copied, when an array element is null, it will be ignored.
     * @param targetDir The directory to which the given files should be copied to.
     * @param overwrite true when existing target files should be overwritten even when they are newer
     * @throws IOException when there is an error while copying the file.
     */
    public static void copy(File[] files, File targetDir, boolean overwrite)
            throws IOException {
        String targetPath = targetDir.getAbsolutePath() + File.separatorChar;
        byte[] buffer = new byte[COPY_BUFFER_SIZE];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file != null) {
                File targetFile = new File(targetPath + file.getName());
                if (!overwrite && targetFile.exists() && targetFile.lastModified() > file.lastModified()) {
                    continue;
                }
                copy(file, targetFile, buffer);
            }
        }
    }

    /**
     * Copies a file.
     *
     * @param source The file which should be copied
     * @param target The file or directory to which the source-file should be copied to.
     * @throws FileNotFoundException when the source file was not found
     * @throws IOException when there is an error while copying the file.
     */
    public static void copy(File source, File target)
            throws IOException {
        copy(source, target, new byte[1024 * 1024]);
    }

    /**
     * Copies a file.
     *
     * @param source The file which should be copied
     * @param target The file or directory to which the source-file should be copied to.
     * @param buffer A buffer used for the copying.
     * @throws FileNotFoundException when the source file was not found
     * @throws IOException when there is an error while copying the file.
     */
    private static void copy(File source, File target, byte[] buffer)
            throws IOException {
        InputStream in = new FileInputStream(source);
        // create parent directory of target-file if necessary:
        File parent = target.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (target.isDirectory()) {
            target = new File(target, source.getName());
        }
        OutputStream out = new FileOutputStream(target);
        int read;
        try {
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * Writes the properties which are defined in the given HashMap into a textfile.
     * The notation in the file will be [name]=[value]\n for each defined property.
     *
     * @param file the file which should be created or overwritten
     * @param properties the properties which should be written.
     * @throws IOException when there is an input/output error during the saving
     */
    public static void writePropertiesFile(File file, Map properties)
            throws IOException {
        writePropertiesFile(file, '=', properties);
    }

    /**
     * Writes the properties which are defined in the given HashMap into a textfile.
     * The notation in the file will be [name]=[value]\n for each defined property.
     *
     * @param file the file which should be created or overwritten
     * @param delimiter the character that separates a property-name from a property-value.
     * @param properties the properties which should be written.
     * @throws IOException when there is an input/output error during the saving
     */
    public static void writePropertiesFile(File file, char delimiter, Map properties)
            throws IOException {
        Object[] keys = properties.keySet().toArray();
        String[] lines = new String[keys.length];
        for (int i = 0; i < lines.length; i++) {
            Object key = keys[i];
            Object value = properties.get(key);
            lines[i] = key.toString() + "=" + value.toString();
        }
        writeTextFile(file, lines);
    }

    /**
     * Reads a properties file.
     * The notation of the file needs to be
     * "[name]=[value]\n"
     * for each defined property.
     *
     * @param file the file containing the properties
     * @return a hashmap containing all properties found in the file

     * @throws IOException when file could not be read.
     * @throws IllegalArgumentException when the line does not contain a property
     */
    public static HashMap readPropertiesFile(File file)
            throws IOException {
        return readPropertiesFile(file, '=');
    }

    /**
     * Reads a properties file.
     * The notation of the file needs to be
     * "[name]=[value]\n"
     * for each defined property.
     *
     * @param file the file containing the properties
     * @param delimiter the character that separates a property-name from a property-value.
     * @return a hashmap containing all properties found in the file

     * @throws IOException when file could not be read.
     * @throws IllegalArgumentException when the line does not contain a property
     */
    public static HashMap readPropertiesFile(File file, char delimiter)
            throws IOException {

        HashMap map = new HashMap();
        readPropertiesFile(file, delimiter, map);
        return map;
    }

    /**
     * Reads a properties file.
     * The notation of the file needs to be
     * "[name]=[value]\n" where '=' is the defined delimiter character.
     * for each defined property.
     *
     * @param file the file containing the properties
     * @param delimiter the character that separates a property-name from a property-value.
     * @param map the hash map to which the properties should be added

     * @throws IOException when file could not be read.
     * @throws IllegalArgumentException when the line does not contain a property
     */
    public static void readPropertiesFile(File file, char delimiter, Map map)
            throws IOException {
        readPropertiesFile(file, delimiter, '#', map, false);
        /*
        String[] lines = readTextFile( file );
        for (int i = 0; i < lines.length; i++) {
        String line = lines[i];
        if (line.length() > 0 && line.charAt(0) != '#') {
        int equalsPos = line.indexOf( delimiter );
        if (equalsPos == -1) {
        throw new IllegalArgumentException("The line [" + line
        + "] of the file [" + file.getAbsolutePath()
        + "] contains an invalid property definition: " +
        "missing separater-character (\"" + delimiter + "\")." );
        }
        String key = line.substring(0, equalsPos );
        String value = line.substring( equalsPos + 1);
        map.put( key, value );
        }
        }
         */
    }

    /**
     * Reads a properties file.
     * The notation of the file needs to be
     * "[name]=[value]\n" where '=' is the defined delimiter character.
     * for each defined property.
     *
     * @param file the file containing the properties
     * @param delimiter the character that separates a property-name from a property-value.
     * @param comment the character that introduces a comment, e.g. '#'
     * @param map the hash map to which the properties should be added
     * @param ignoreInvalidProperties when this flag is true, invalid property definition (those that do not contain
     * the delimiter char) are ignored
     * @throws IOException when file could not be read.
     * @throws IllegalArgumentException when the line does not contain a property
     */
    public static void readPropertiesFile(File file, char delimiter, char comment, Map map,
            boolean ignoreInvalidProperties)
            throws IOException {
        try {
            readProperties(new FileInputStream(file), delimiter, comment, map, ignoreInvalidProperties);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("File [" + file.getAbsolutePath() + "]:  " + e.getMessage());
        }
    }

    /**
     * Copies the contents of a directory to the specified target directory.
     *
     * @param directory the directory containing files
     * @param targetDirName the directory to which the files should be copied to
     * @param update is true when files should be only copied when the source files are
     * newer compared to the target files.
     * @throws IOException when a file could not be copied
     * @throws IllegalArgumentException when the directory is not a directory.
     */
    public static void copyDirectoryContents(File directory, String targetDirName, boolean update)
            throws IOException {
        copyDirectoryContents(directory, new File(targetDirName), update);
    }

    /**
     * Copies the contents of a directory to the specified target directory.
     *
     * @param directory the directory containing files
     * @param targetDir the directory to which the files should be copied to
     * @param update is true when files should be only copied when the source files
     * are newer compared to the target files.
     * @throws IOException when a file could not be copied
     * @throws IllegalArgumentException when the directory is not a directory.
     */
    public static void copyDirectoryContents(File directory, File targetDir, boolean update)
            throws IOException {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Cannot copy contents of the file ["
                    + directory.getAbsolutePath() + "]: specify a directory instead.");
        }
        String[] fileNames = directory.list();
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            File file = new File(directory.getAbsolutePath(), fileName);
            if (file.isDirectory()) {
                copyDirectoryContents(file, targetDir.getAbsolutePath() + File.separatorChar + fileName, update);
            } else {
                File targetFile = new File(targetDir, fileName);
                if (update) {
                    // update only when the source file is newer:
                    if ((!targetFile.exists())
                            || (file.lastModified() > targetFile.lastModified())) {
                        copy(file, targetFile);
                    }
                } else {
                    // copy the file in all cases:
                    copy(file, targetFile);
                }
            }
        }
    }

    private static String getPath(File file) {
        String path = file.getAbsolutePath();
        int buildIndex = path.indexOf("build");
        if (buildIndex != -1) {
            path = path.substring(buildIndex);
        }
        return path;
    }

    /**
     * Deletes a file or a directory.
     *
     * @param file the file or directory which should be deleted.
     * @return true when the file could be deleted
     */
    public static boolean delete(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delete(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return file.delete();
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @return a map containing all properties that could be read from the input stream
     * @throws IOException when reading from the input stream fails
     */
    public static Map readProperties(InputStream in) throws IOException {
        Map map = new HashMap();
        readProperties(in, '=', '#', map, false);
        return map;
    }

    /**
     * Reads properties from the given file.
     *
     * @param file the file containing properties separated with '='
     * @return a map containing all properties that could be read from the input stream
     * @throws IOException when reading from the file fails
     * @throws FileNotFoundException when the file does not exist
     */
    public static Map readProperties(File file) throws IOException {
        return readProperties(new FileInputStream(file));
    }

    /**
     * Reads properties from the given reader.
     *
     * @param reader the input reader
     * @return a map containing all properties that could be read from the reader
     * @throws IOException when reading fails
     */
    public static Map readProperties(Reader reader)
            throws IOException {
        Map map = new HashMap();
        readProperties(reader, '=', '#', map, false);
        return map;
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @param delimiter the character that separates a property-name from a property-value.
     * @param properties a map containing properties
     * @throws IOException when reading from the input stream fails
     */
    public static void readProperties(InputStream in, char delimiter, Map properties)
            throws IOException {
        readProperties(in, delimiter, '#', properties, false, null);
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @param delimiter the character that separates a property-name from a property-value.
     * @param properties a map containing properties
     * @param encoding the encoding of the file
     * @throws IOException when reading from the input stream fails
     */
    public static void readProperties(InputStream in, char delimiter, Map properties, String encoding)
            throws IOException {
        readProperties(in, delimiter, '#', properties, false, encoding);
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @param delimiter the character that separates a property-name from a property-value.
     * @param properties a map containing properties
     * @param encoding the encoding of the file
     * @param translateToAscii true when the FileUtil should translate the code into ASCII only code (using unicode).
     * @param translateToNative true when escape sequences like \t or \n should be converted to native characters
     * @throws IOException when reading from the input stream fails
     */
    public static void readProperties(InputStream in, char delimiter, Map properties, String encoding,
            boolean translateToAscii, boolean translateToNative)
            throws IOException {
        readProperties(in, delimiter, '#', properties, false, encoding, translateToAscii, translateToNative);
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @param delimiter the character that separates a property-name from a property-value.
     * @param comment the char denoting comments
     * @param properties a map containing properties
     * @throws IOException when reading from the input stream fails
     */
    public static void readProperties(InputStream in, char delimiter, char comment, Map properties)
            throws IOException {
        readProperties(in, delimiter, comment, properties, false);
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @param delimiter the character that separates a property-name from a property-value.
     * @param comment the char denoting comments
     * @param properties a map containing properties
     * @param ignoreInvalidProperties when this flag is true, invalid property definition
     * (those that do not contain the delimiter char) are ignored
     * @throws IOException when reading from the input stream fails
     * @throws IllegalArgumentException when an invalid property definition is encountered
     * and ignoreInvalidProperties is false
     */
    public static void readProperties(InputStream in, char delimiter, char comment, Map properties,
            boolean ignoreInvalidProperties)
            throws IOException {
        readProperties(in, delimiter, comment, properties, ignoreInvalidProperties, null);
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @param delimiter the character that separates a property-name from a property-value.
     * @param comment the char denoting comments
     * @param properties a map containing properties
     * @param ignoreInvalidProperties when this flag is true, invalid property definition
     * (those that do not contain the delimiter char) are ignored
     * @param encoding the encoding of the text file, when null the default charset is used
     * @throws IOException when reading from the input stream fails
     * @throws IllegalArgumentException when an invalid property definition is encountered and
     * ignoreInvalidProperties is false
     */
    public static void readProperties(InputStream in, char delimiter, char comment, Map properties,
            boolean ignoreInvalidProperties, String encoding)
            throws IOException {
        readProperties(in, delimiter, comment, properties, ignoreInvalidProperties, encoding, false, false);
    }

    /**
     * Reads properties from the given reader.
     *
     * @param reader the input reader
     * @param delimiter the character that separates a property-name from a property-value.
     * @param comment the char denoting comments
     * @param properties a map containing properties
     * @param ignoreInvalidProperties when this flag is true, invalid property definition
     * (those that do not contain the delimiter char) are ignored
     * @throws IOException when reading from the input stream fails
     * @throws IllegalArgumentException when an invalid property definition is encountered and
     * ignoreInvalidProperties is false
     */
    public static void readProperties(Reader reader, char delimiter, char comment, Map properties,
            boolean ignoreInvalidProperties)
            throws IOException {
        readProperties(reader, delimiter, comment, properties, ignoreInvalidProperties, false, false);
    }

    /**
     * Reads properties from the given input stream.
     *
     * @param in the input stream
     * @param delimiter the character that separates a property-name from a property-value.
     * @param comment the char denoting comments
     * @param properties a map containing properties
     * @param ignoreInvalidProperties when this flag is true, invalid property definition
     * (those that do not contain the delimiter char) are ignored
     * @param encoding the encoding of the text file, when null the default charset is used
     * @param translateToAscii true when the FileUtil should translate the code into ASCII only code (using unicode).
     * @param translateToNative true when escape sequences like \t or \n should be converted to native characters
     * @throws IOException when reading from the input stream fails
     * @throws IllegalArgumentException when an invalid property definition is encountered and
     * ignoreInvalidProperties is false
     */
    public static void readProperties(InputStream in, char delimiter, char comment, Map properties,
            boolean ignoreInvalidProperties, String encoding, boolean translateToAscii, boolean translateToNative)
            throws IOException {
        Reader reader;
        if (encoding == null) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, encoding);
        }
        readProperties(reader, delimiter, comment, properties, ignoreInvalidProperties, translateToAscii,
                translateToNative);
        in.close();
    }

    /**
     * Reads properties from the given reader.
     *
     * @param reader the input reader
     * @param delimiter the character that separates a property-name from a property-value.
     * @param comment the char denoting comments
     * @param properties a map containing properties
     * @param ignoreInvalidProperties when this flag is true, invalid property definition
     * (those that do not contain the delimiter char) are ignored
     * @param translateToAscii true when the FileUtil should translate the code into ASCII only code (using unicode).
     * @param translateToNative true when escape sequences like \t or \n should be converted to native characters
     * @throws IOException when reading from the input stream fails
     * @throws IllegalArgumentException when an invalid property definition is encountered and
     * ignoreInvalidProperties is false
     */
    public static void readProperties(Reader reader, char delimiter, char comment, Map properties,
            boolean ignoreInvalidProperties, boolean translateToAscii, boolean translateToNative)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        int index = 0;
        while ((line = bufferedReader.readLine()) != null) {
            index++;
            if (line.length() == 0 || line.charAt(0) == comment || line.trim().length() == 0) {
                continue;
            }
            if (translateToAscii) {
//                line = Native2Ascii.nativeToAscii(line);
            }
            if (translateToNative) {
//                line = Native2Ascii.asciiToNative(line);
            }
            int delimiterPos = line.indexOf(delimiter);
            if (delimiterPos == -1) {
                if (ignoreInvalidProperties || (index == 1)) { // ("\ufeff".equals(line)) ) {
                    // "\ufeff" is an optional header announcing Big-Endian byte ordering for UTF-8 files.
                    // since there are several BOM signatures, we will ignore all invalid
                    // propery definitions in the first line for now.
                    // For further information:
                    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4508058
                    // http://www.unicode.org/unicode/faq/utf_bom.html
                    continue;
                } else {
                    throw new IllegalArgumentException("The line [" + line + "] in row " + index
                            + " contains an invalid property definition: "
                            + "missing separator-character (\"" + delimiter + "\").");
                }
            }
            String key = line.substring(0, delimiterPos).trim();
            String value = line.substring(delimiterPos + 1);
            properties.put(key, value);
        }
    }

    /**
     * Writes the given textlines into the specified file.
     *
     * @param file the file to which the text should be written
     * @param lines the text lines of the file
     * @param encoding the encoding, e.g. "UTF8", null when the default encoding should be used
     * @throws IOException when there is an input/output error during the saving
     */
    public static void writeTextFile(File file, String[] lines, String encoding)
            throws IOException {

        File parentDir = file.getParentFile();
        if ((parentDir != null) && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        PrintWriter out;
        if (encoding != null) {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
        } else {
            out = new PrintWriter(new FileWriter(file));
        }

        for (int i = 0; i < lines.length; i++) {
            out.println(lines[i]);
        }
        out.close();
    }

    /**
     * Adds the given line to the specified textfile.
     * The file is created if necessary.
     *
     * @param file the text file
     * @param line the line
     * @throws IOException when adding fails.
     */
    public static void addLine(File file, String line)
            throws IOException {
        addLines(file, new String[]{line});
    }

    /**
     * Adds the given line to the specified textfile.
     * The file is created if necessary.
     *
     * @param file the text file
     * @param lines the lines that should be added
     * @throws IOException when adding fails.
     */
    public static void addLines(File file, String[] lines)
            throws IOException {
        if (file.exists()) {
            String[] oldLines = readTextFile(file);
            String[] newLines = new String[oldLines.length + lines.length];
            System.arraycopy(oldLines, 0, newLines, 0, oldLines.length);
            System.arraycopy(lines, 0, newLines, oldLines.length, lines.length);
            writeTextFile(file, newLines);
        } else {
            writeTextFile(file, lines);
        }
    }

    /**
     * Retrieves all files from the given directory
     *
     * @param dir the directory
     * @param extension the file extension, when the extension is null, all files are included
     * @param recursive true when subdirectories should also be read.
     * @return an String array with the file-names relative to the given directory that do have the given extension
     */
    public static String[] filterDirectory(File dir, String extension, boolean recursive) {
        if (dir == null || !dir.exists()) {
            return new String[0];
        }
        ArrayList fileNamesList = new ArrayList();
        filterDirectory("", dir, extension, recursive, fileNamesList);
        return (String[]) fileNamesList.toArray(new String[fileNamesList.size()]);
    }

    /**
     * Retrieves all files from the given directory
     *
     * @param path the start path taken from the base directory towards the current one
     * @param dir the directory
     * @param extension the file extension
     * @param recursive true when subdirectories should also be read.
     */
    private static void filterDirectory(String path, File dir, String extension,
            boolean recursive, List fileNamesList) {
        String[] names = dir.list();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            File file = new File(dir, name);
            if (file.isDirectory()) {
                if (recursive) {
                    filterDirectory(path + name + File.separatorChar, file, extension, recursive, fileNamesList);
                }
            } else if (extension == null || name.endsWith(extension)) {
                fileNamesList.add(path + name);
            }
        }
    }
}
