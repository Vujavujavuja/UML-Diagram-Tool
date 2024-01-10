package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Metod;

import java.io.IOException;

public class InterfejsDeserializer extends JsonDeserializer<Interfejs> {
    @Override
    public Interfejs deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        int position_1st = rootNode.get("position_1st").asInt();
        int position_2nd = rootNode.get("position_2nd").asInt();
        int size_1st = rootNode.get("size_1st").asInt();
        int size_2nd = rootNode.get("size_2nd").asInt();
        Pair<Integer, Integer> position = new Pair<>(position_1st, position_2nd);
        Pair<Integer, Integer> size = new Pair<>(size_1st, size_2nd);

        Interfejs interfejs = new Interfejs(null, name);
        interfejs.setName(name);
        interfejs.setPosition(position);
        interfejs.setSize(size);

        JsonNode methodsNode = rootNode.get("methods");
        if(methodsNode == null){
            return interfejs;
        }
        if (methodsNode.isArray()) {
            for (JsonNode methodNode : methodsNode) {
                JsonDeserializer<?> metodDeserializer = deserializationContext.findRootValueDeserializer(
                        deserializationContext.getTypeFactory().constructType(Metod.class));
                Metod metod = (Metod) metodDeserializer.deserialize(methodNode.traverse(jsonParser.getCodec()), deserializationContext);
                interfejs.addClassContent(metod);
            }
        }
        return interfejs;
    }
}
