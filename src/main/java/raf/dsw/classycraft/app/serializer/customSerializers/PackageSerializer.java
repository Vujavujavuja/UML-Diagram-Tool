package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;

import java.io.IOException;

public class PackageSerializer extends JsonSerializer<Package> {
    @Override
    public void serialize(Package aPackage, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typePD", "package");
        jsonGenerator.writeStringField("name", aPackage.getName());
        jsonGenerator.writeArrayFieldStart("children");
        for (ClassyNode child : aPackage.getChildren()) {
            if (child instanceof Package) {
                jsonGenerator.writeObject(child);
            }
            else if(child instanceof Diagram){
                jsonGenerator.writeObject(child);
            }
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
