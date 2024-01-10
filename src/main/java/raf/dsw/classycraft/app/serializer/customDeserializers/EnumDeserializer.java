package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnumDeserializer extends JsonDeserializer<UmlEnum> {

    @Override
    public UmlEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        int position_1st = rootNode.get("position_1st").asInt();
        int position_2nd = rootNode.get("position_2nd").asInt();
        int size_1st = rootNode.get("size_1st").asInt();
        int size_2nd = rootNode.get("size_2nd").asInt();
        Pair<Integer, Integer> position = new Pair<>(position_1st, position_2nd);
        Pair<Integer, Integer> size = new Pair<>(size_1st, size_2nd);

        UmlEnum umlEnum = new UmlEnum(null, name, null);
        umlEnum.setName(name);
        umlEnum.setPosition(position);
        umlEnum.setSize(size);

        JsonNode childrenNode = rootNode.get("children");
        if (childrenNode.isArray()) {
            List<String> constants = new ArrayList<>();
            for (JsonNode childNode : childrenNode) {
                constants.add(childNode.asText());
            }
            umlEnum.setConstants(constants);
        }

        return umlEnum;
    }
}
