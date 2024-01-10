package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Kompozicija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;

import java.io.IOException;

public class KompozicijaDeserializer extends JsonDeserializer<Kompozicija> {
    @Override
    public Kompozicija deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        String fromElementName = rootNode.get("fromElement").asText();
        String toElementName = rootNode.get("toElement").asText();
        String fromMultiplicity = rootNode.get("fromMultiplicity").asText();
        String toMultiplicity = rootNode.get("toMultiplicity").asText();
        int start_1st = rootNode.get("start_1st").asInt();
        int start_2nd = rootNode.get("start_2nd").asInt();
        int end_1st = rootNode.get("end_1st").asInt();
        int end_2nd = rootNode.get("end_2nd").asInt();
        Pair<Integer, Integer> start = new Pair<>(start_1st, start_2nd);
        Pair<Integer, Integer> end = new Pair<>(end_1st, end_2nd);

        Kompozicija zavisnost = new Kompozicija(null, name);
        zavisnost.setFromElementName(fromElementName);
        zavisnost.setToElementName(toElementName);
        zavisnost.setFromMultiplicity(fromMultiplicity);
        zavisnost.setToMultiplicity(toMultiplicity);
        zavisnost.setStart(start);
        zavisnost.setEnd(end);

        return zavisnost;
    }
}
