package raf.dsw.classycraft.app.serializer.customDeserializers;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KlasaDeserializer extends JsonDeserializer<Klasa> {

    @Override
    public Klasa deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        int position_1st = rootNode.get("position_1st").asInt();
        int position_2nd = rootNode.get("position_2nd").asInt();
        int size_1st = rootNode.get("size_1st").asInt();
        int size_2nd = rootNode.get("size_2nd").asInt();
        Pair<Integer, Integer> position = new Pair<>(position_1st, position_2nd);
        Pair<Integer, Integer> size = new Pair<>(size_1st, size_2nd);

        Klasa klasa = new Klasa(null, name);
        klasa.setName(name);
        klasa.setPosition(position);
        klasa.setSize(size);
        JsonNode classContentsNode = rootNode.get("ClassContent");
        if (classContentsNode != null && classContentsNode.isArray()) {
            List<ClassContent> classContents = new ArrayList<>();
            for (JsonNode contentNode : classContentsNode) {
                JsonParser contentParser = contentNode.traverse(jsonParser.getCodec());
                contentParser.nextToken();

                ClassContent content;
                if ("Metod".equals(contentNode.get("typeMA").asText())) {
                    content = deserializationContext.readValue(contentParser, Metod.class);
                } else if ("Atribut".equals(contentNode.get("typeMA").asText())) {
                    content = deserializationContext.readValue(contentParser, Atribut.class);
                } else {
                    continue;
                }
                classContents.add(content);
            }
            klasa.setClassContents(classContents);
        }

        return klasa;
    }
}
