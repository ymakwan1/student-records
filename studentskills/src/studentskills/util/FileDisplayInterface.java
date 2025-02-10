package studentskills.util;
import java.io.Writer;

public interface FileDisplayInterface {

    void printToFile(String printStr);
    Writer initFileWriter(String fileName);
}