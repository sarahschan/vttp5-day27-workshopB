package sg.edu.nus.iss.paf_day27_Workshop_B.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.paf_day27_Workshop_B.repository.CommentRepository;

@Component
public class FileLoader {

    @Autowired
    CommentRepository commentRepository;
    
    public void populateDatabase(String filePath, String collectionName) throws FileNotFoundException {

        // drop the collection in mongodb
        commentRepository.dropAndCreateNewCollection(collectionName);

        File file = new File(filePath);

        if (file == null || !file.exists() || !file.isFile()) {
            throw new FileNotFoundException();
        }

        JsonReader jReader = Json.createReader(new FileReader(file));
        JsonArray jsonArray = jReader.readArray();

        for (JsonValue jValue : jsonArray) {
            JsonObject jObject = jValue.asJsonObject();
            commentRepository.insertComment(jObject, collectionName);
        }

        System.out.println("All documents loaded to mongoDB");

    }

}
