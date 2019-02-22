import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChunkTextFile {

    private static final String inputFilename = "inputFile.txt";

    public static void main(String[] args) {
        final int size = 10000;
        try(BufferedReader reader=Files.newBufferedReader(Paths.get("src", inputFilename))) {
            String line = reader.readLine();
            for(int count = 0; line != null; count++) {
                try(BufferedWriter writer = Files.newBufferedWriter(
                        Paths.get("batched_items_file_" + count + ".txt"))) {
                    for(int i = 0; i < size && line != null; i++) {
                        writer.write(line);
                        writer.newLine();
                        line = reader.readLine();
                    }
                }
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
