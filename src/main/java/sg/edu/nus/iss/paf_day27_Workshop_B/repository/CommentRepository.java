package sg.edu.nus.iss.paf_day27_Workshop_B.repository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class CommentRepository {
    
    @Autowired
    private MongoTemplate template;

    public void dropAndCreateNewCollection(String collectionName) {
        template.dropCollection(collectionName);
        template.createCollection(collectionName);
        System.out.printf("Collection '%s' dropped and re-created\n", collectionName);
    }


    public void insertComment(JsonObject jsonComment, String collectionName) {
        String id = jsonComment.getString("c_id");
        String user = jsonComment.getString("user");
        int rating = jsonComment.getInt("rating");
        String text = jsonComment.getString("c_text");
        int gid = jsonComment.getInt("gid");

        Document toInsert = new Document();
            toInsert.put("_id", id);
            toInsert.put("user", user);
            toInsert.put("rating", rating);
            toInsert.put("c_text", text);
            toInsert.put("gid", gid);

        template.insert(toInsert, collectionName);

    }


    public void createTextIndex(String collectionName) {
        TextIndexDefinition textIndexDefinition = new TextIndexDefinition.TextIndexDefinitionBuilder()
            .onField("c_text")
            .build();

        template.indexOps(collectionName).ensureIndex(textIndexDefinition);

        System.out.println("Text index created for c_text in collection " + collectionName);
    }
}
