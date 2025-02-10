package studentskills.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public final class FileProcessor {
    private BufferedReader reader;
    public FileProcessor(String inputFilePath) throws InvalidPathException, SecurityException, FileNotFoundException, IOException{
        if (!Files.exists(Paths.get(inputFilePath))) {
            throw new FileNotFoundException("Invalid input file path or file in incorrect location");
        }
        reader = new BufferedReader(new FileReader(new File(inputFilePath)));
    }

    public String poll() throws IOException{
        return reader.readLine();
    }

    public void close() throws IOException{
        reader.close();
    }
}
