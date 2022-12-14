import org.apache.maven.model.Dependency;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.*;

public class GenerateAppPropertiesTask {

    private List<Map<String, String>> dbMap;
    public GenerateAppPropertiesTask(List<Map<String, String>> dbMap) {
        this.dbMap = dbMap;
    }

    public void execute(Recipe recipe) {
        try {
            String propertiesFolder = recipe.getApp().getArtifactId() + "/src/main/resources/" ;

            Optional<Map<String,String>> db =
                    dbMap.stream().filter(i -> i.get("key").equals(recipe.getApp().getDb())).findFirst();

            String jdbcUrl = "";

            if(db.isPresent()) {
                jdbcUrl = db.get().get("jdbcUrl");
            }
            else{
                System.out.println("!!! No matching DB found");
            }

            //generate base
            Properties properties = new Properties();
            properties.put("spring.datasource.url", jdbcUrl);
            properties.put("spring.datasource.username", "${DB_USERNAME}");
            properties.put("spring.datasource.password", "${DB_PASSWORD}");

            properties.store(new FileOutputStream(propertiesFolder + "/application.properties"), "Generated by Homi 0.1.1");

            if(Objects.nonNull(recipe.getApp().getEnvironments())) {
                properties = new Properties();
                for(String env : recipe.getApp().getEnvironments()) {
                    properties.store(new FileOutputStream(propertiesFolder + "/application-" + env + ".properties"), "Generated by Homi 0.1.1");
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load templates or write output.");
        }
    }
}
