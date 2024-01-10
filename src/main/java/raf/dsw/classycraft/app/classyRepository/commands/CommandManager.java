package raf.dsw.classycraft.app.classyRepository.commands;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private int currentCommand;
    private List<Command> commands;

    public CommandManager() {
        currentCommand = 0;
        commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        MainFrame.getInstance().getPackageView().getDiagram().getProject(MainFrame.getInstance().getPackageView().getDiagram()).setChanged(true);
        while (currentCommand < commands.size()) {
            commands.remove(currentCommand);
        }
        commands.add(command);
        currentCommand++;
        this.updateUndoRedoState();
    }

    public void redoCommand() {
        if (currentCommand < commands.size()) {
            commands.get(currentCommand++).redoCommand();
        }
        this.updateUndoRedoState();
    }

    public void undoCommand() {
        if (currentCommand > 0) {
            commands.get(--currentCommand).undoCommand();
        }
        this.updateUndoRedoState();
    }

    private void updateUndoRedoState() {
        Diagram diagram = MainFrame.getInstance().getPackageView().getDiagram();
        diagram.updateUndoRedoStateForActiveDiagram();
    }
    public boolean canUndo() {
        return currentCommand > 0;
    }

    public boolean canRedo() {
        return currentCommand < commands.size();
    }
}
