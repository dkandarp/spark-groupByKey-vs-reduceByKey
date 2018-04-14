import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Dave on 14/4/18.
 */
public class GenerateSampleInputFile {


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new RuntimeException("please pass number of words and filePath for generation");
        }

        long wordCount = Long.parseLong(args[0]);
        String filePath = args[1];

        File file = new File(filePath);
        if (file.exists()) {
            throw new RuntimeException("File already exists at ${filePath}");
        }

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            int newLine = (int) (Math.random() * 500);
            int count = 0;

            for (long i = 0; i < wordCount; i++) {


                writer.write(randomAlphaNumeric(ThreadLocalRandom.current().nextInt(1, 5 + 1)));
                writer.write(" ");

                ++count;

                if (count > newLine) {
                    writer.write(System.lineSeparator());
                    writer.flush();

                    count = 0;
                    newLine = (int) (Math.random() * 500);
                }
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
