package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;

import java.io.IOException;

public interface Serializer {
    void saveProject(String filePath, ClassyNode node) throws IOException;

    Object openProject(String filePath) throws IOException;
}
