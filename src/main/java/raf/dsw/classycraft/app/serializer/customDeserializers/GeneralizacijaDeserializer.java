package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Generalizacija;

import java.io.IOException;

public class GeneralizacijaDeserializer extends JsonDeserializer<Generalizacija> {
    @Override
    public Generalizacija deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String name = node.get("name").asText();
        String fromElementName = node.get("fromElement").asText();
        String toElementName = node.get("toElement").asText();
        int start_1st = node.get("start_1st").asInt();
        int start_2nd = node.get("start_2nd").asInt();
        int end_1st = node.get("end_1st").asInt();
        int end_2nd = node.get("end_2nd").asInt();
        Pair<Integer, Integer> start = new Pair<>(start_1st, start_2nd);
        Pair<Integer, Integer> end = new Pair<>(end_1st, end_2nd);

        Generalizacija generalizacija = new Generalizacija(null, name);

        generalizacija.setFromElementName(fromElementName);
        generalizacija.setToElementName(toElementName);
        generalizacija.setStart(start);
        generalizacija.setEnd(end);

        return generalizacija;
    }
}
