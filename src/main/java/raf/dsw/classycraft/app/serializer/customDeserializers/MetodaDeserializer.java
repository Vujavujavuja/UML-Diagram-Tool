package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Metod;

import java.io.IOException;

public class MetodaDeserializer extends JsonDeserializer<Metod> {

    @Override
    public Metod deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        Visibility visibility = Visibility.valueOf(rootNode.get("visibility").asText());
        boolean isStatic = rootNode.get("isStatic").asBoolean();
        String returnType = rootNode.get("returnType").asText();

        Metod metod = new Metod(name, visibility, isStatic, returnType, null);
        metod.setName(name);
        metod.setVisibility(visibility);
        metod.setStatic(isStatic);
        metod.setReturnType(returnType);

        return metod;
    }
}
