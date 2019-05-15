package com.brainacad;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;


import java.nio.file.Files;
import java.nio.file.Paths;


public class MyJsonValidator {

    public static ProcessingReport validateJson(String json, String jsonSchemaPath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
       String jsonSchema = new String(Files.readAllBytes(Paths.get (jsonSchemaPath))); // путь к файлу: jsonSchemaPath
        JsonNode schemaNode = mapper.readTree(jsonSchema);

        JsonNode currJson =mapper.readTree(json);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(schemaNode);
        return schema.validate(currJson);
    }
}
