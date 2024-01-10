package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Atribut;

import java.io.IOException;

public class AtributDeserializer extends JsonDeserializer<Atribut> {

    @Override
    public Atribut deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        Visibility visibility = Visibility.valueOf(rootNode.get("visibility").asText());
        boolean isStatic = rootNode.get("isStatic").asBoolean();
        String dataType = rootNode.get("dataType").asText();

        Atribut atribut = new Atribut(name, visibility, isStatic, dataType);
        atribut.setName(name);
        atribut.setVisibility(visibility);
        atribut.setStatic(isStatic);
        atribut.setDataType(dataType);

        return atribut;
    }
}
