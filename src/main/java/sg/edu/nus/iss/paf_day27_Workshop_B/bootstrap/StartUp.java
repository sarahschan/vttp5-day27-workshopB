package sg.edu.nus.iss.paf_day27_Workshop_B.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import sg.edu.nus.iss.paf_day27_Workshop_B.repository.CommentRepository;

@Component
public class StartUp implements CommandLineRunner {

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public void run(String... args) throws Exception {
        
        // mvn spring-boot:run -Dspring-boot.run.arguments="--load=<path to comments.json>"
        if (args.length > 0) {
            System.out.println("Arguments: " + Arrays.toString(args));

            for (String arg : args) {
                if (arg.startsWith("--load=")) {
                    String filePath = arg.substring(7);
                    System.out.println("Loading file from: " + filePath);

                    String[] parts = filePath.split(File.separator);
                    int fileIndex = parts.length - 1;

                    String fileName = parts[fileIndex]; 
                    String[] fileNameParts = fileName.split("\\.");

                    if (fileNameParts[1].equalsIgnoreCase("json")) {

                        String collection = fileNameParts[0];
                        try {
                            fileLoader.populateDatabase(filePath, collection);
                            commentRepository.createTextIndex(collection);
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found at path: " + filePath);
                            System.exit(-1);
                        }

                    } else {
                        System.out.println("Invalid file type - must be json");
                    }

                }
            }

        }


    }
    
}
