package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackageDeserializer extends JsonDeserializer<Package> {

    @Override
    public Package deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();

        Package aPackage = new Package(null, name);

        List<ClassyNode> children = new ArrayList<>();
        JsonNode childrenNode = rootNode.get("children");
        if (childrenNode.isArray()) {
            for (JsonNode childNode : childrenNode) {
                ClassyNode child;
                String childType = childNode.get("typePD").asText();

                if ("package".equals(childType)) {
                    child = ctxt.readValue(childNode.traverse(jsonParser.getCodec()), Package.class);
                } else if ("diagram".equals(childType)) {
                    child = ctxt.readValue(childNode.traverse(jsonParser.getCodec()), Diagram.class);
                } else {
                    continue;
                }

                child.setParent(aPackage);
                children.add(child);
            }
        }
        aPackage.setChildren(children);

        return aPackage;
    }
}
