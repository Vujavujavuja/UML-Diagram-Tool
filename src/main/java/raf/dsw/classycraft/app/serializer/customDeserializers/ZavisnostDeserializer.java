package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;

import java.io.IOException;

public class ZavisnostDeserializer extends JsonDeserializer<Zavisnost> {

    @Override
    public Zavisnost deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        String fromElementName = rootNode.get("fromElement").asText();
        String toElementName = rootNode.get("toElement").asText();
        int start_1st = rootNode.get("start_1st").asInt();
        int start_2nd = rootNode.get("start_2nd").asInt();
        int end_1st = rootNode.get("end_1st").asInt();
        int end_2nd = rootNode.get("end_2nd").asInt();
        Pair<Integer, Integer> start = new Pair<>(start_1st, start_2nd);
        Pair<Integer, Integer> end = new Pair<>(end_1st, end_2nd);

        Zavisnost zavisnost = new Zavisnost(null, name);
        zavisnost.setFromElementName(fromElementName);
        zavisnost.setToElementName(toElementName);
        zavisnost.setStart(start);
        zavisnost.setEnd(end);
        return zavisnost;
    }
}
