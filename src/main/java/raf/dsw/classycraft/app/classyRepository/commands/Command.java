package raf.dsw.classycraft.app.classyRepository.commands;

public interface Command
{
    void undoCommand();
    void redoCommand();
}
