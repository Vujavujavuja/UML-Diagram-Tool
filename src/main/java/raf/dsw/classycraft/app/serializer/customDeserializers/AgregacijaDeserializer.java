package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Agregacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;

import java.io.IOException;

public class AgregacijaDeserializer extends JsonDeserializer<Agregacija> {

    @Override
    public Agregacija deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
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

        Agregacija zavisnost = new Agregacija(null, name);
        zavisnost.setFromElementName(fromElementName);
        zavisnost.setToElementName(toElementName);
        zavisnost.setFromMultiplicity(fromMultiplicity);
        zavisnost.setToMultiplicity(toMultiplicity);
        zavisnost.setStart(start);
        zavisnost.setEnd(end);

        return zavisnost;
    }
}
