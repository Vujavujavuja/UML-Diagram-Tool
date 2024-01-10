package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.implementation.*;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.*;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.*;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiagramDeserializer extends JsonDeserializer<Diagram> {

    @Override
    public Diagram deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();

        Diagram diagram = new Diagram(name, null, false);
        diagram.setName(name);

        List<DiagramElement> elements = new ArrayList<>();
        JsonNode elementsNode = rootNode.get("diagramElements");
        if (elementsNode.isArray()) {
            for (JsonNode elementNode : elementsNode) {
                JsonParser elementParser = elementNode.traverse(jsonParser.getCodec());
                elementParser.nextToken(); // Initialize the parser

                DiagramElement element;
                String typeICC = elementNode.get("typeICC").asText();
                switch (typeICC) {
                    case "class":
                        element = ctxt.readValue(elementParser, Klasa.class);
                        break;
                    case "interface":
                        element = ctxt.readValue(elementParser, Interfejs.class);
                        break;
                    case "enum":
                        element = ctxt.readValue(elementParser, UmlEnum.class);
                        break;
                    case "agregacija":
                        element = ctxt.readValue(elementParser, Agregacija.class);
                        break;
                    case "generalizacija":
                        element = ctxt.readValue(elementParser, Generalizacija.class);
                        break;
                    case "zavisnost":
                        element = ctxt.readValue(elementParser, Zavisnost.class);
                        break;
                    case "kompozicija":
                        element = ctxt.readValue(elementParser, Kompozicija.class);
                        break;
                    default:
                        continue;
                }

                element.setParent(diagram);
                elements.add(element);
            }
        }
        diagram.setDiagramElements(elements);
        resolveConnections(diagram, elements);

        return diagram;
    }

    private void resolveConnections(Diagram diagram,List<DiagramElement> elements) {
        List<InterClass> interClasses = new ArrayList<>();
        List<Connection> connections = new ArrayList<>();
        for(DiagramElement element : elements){
            if(element instanceof InterClass){
                interClasses.add((InterClass) element);
            }
            else{
                connections.add((Connection) element);
            }
        }
        for(Connection connection: connections){
            for(InterClass interClass: interClasses){
                if(connection.getFromElementName().equals(interClass.getName())){
                    connection.setFromElement(interClass);
                }
                if(connection.getToElementName().equals(interClass.getName())){
                    connection.setToElement(interClass);
                }
            }
        }
    }
}
