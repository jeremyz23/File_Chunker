import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ChunkTextFile {

	private static final String inputFilename = "inputFile.txt";

	public static void main(String[] args) {

		BufferedReader reader = null;

		BufferedWriter fileWriter = null;


		// Create an ArrayList object to hold the lines of input file

		List<String> lines = new ArrayList<String>();

		try {
			// Creating BufferedReader object to read the input file

			reader = new BufferedReader(new FileReader("src" + "//" + inputFilename));

			// Reading all the lines of input file one by one and adding them into ArrayList
			String currentLine = reader.readLine();

			while (currentLine != null) {
				lines.add(currentLine);

				currentLine = reader.readLine();

			}
			// End of file read.

			final AtomicInteger counter = new AtomicInteger(0);
			final int size = 10000;

			Collection<List<String>> partitioned = lines.stream()
					.collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size)).values();
			
			//Printing partitions. Each partition will be written to a file.
			//Testing confirms the partitioning works correctly.
			partitioned.forEach(System.out::println);

			//Iterate through the Collections and create a file for List<String> object.
			//Testing confirms the file is created and properly named.
			Integer count = 0;
			for (List<String> chunks : partitioned) {
				// Prepare new incremented file name.
				String outputFile = "batched_items_file_";
				String txt = ".txt";
				 count++;

				String filename = outputFile + count + txt;

				// Write file to directory.
				fileWriter = new BufferedWriter(new FileWriter("src" + "//" + outputFile));
				fileWriter = new BufferedWriter(new FileWriter(filename));
				
				//Iterate through the List of Strings and write each String to the file.
				//Writing is not successful. Only 1 file is created and it is empty.
				for (String chunk : chunks) {
					// Prepare list of strings to be written to new file.
					// Write each item number to file.
				    fileWriter.write(chunk + System.lineSeparator());
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Closing the resources
			System.out.println("Finished");

			try {
				if (reader != null) {
					reader.close();
				}

				if (fileWriter != null) {
					fileWriter.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}