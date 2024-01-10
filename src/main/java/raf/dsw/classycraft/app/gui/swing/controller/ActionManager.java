package raf.dsw.classycraft.app.gui.swing.controller;

import lombok.Getter;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddDiagramAction;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddPackageAction;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddProjectAction;
import raf.dsw.classycraft.app.gui.swing.controller.diagramActions.*;

@Getter
public class ActionManager
{
    private AboutUsAction aboutUsAction;
    private EditAction editAction;
    private ExitAction exitAction;
    private AddDiagramAction addDiagramAction;
    private AddPackageAction addPackageAction;
    private AddProjectAction addProjectAction;
    private DeleteAction deleteAction;
    private AddClassAction addClassAction;
    private AddInterfaceAction addInterfaceAction;
    private AddEnumAction addEnumAction;
    private AddRelationshipAction addRelationshipAction;
    private MoveElementAction moveElementAction;
    private SelectElementAction selectElementAction;
    private DeleteElementAction deleteElementAction;
    private ZoomInAction zoomInAction;
    private ZoomOutAction zoomOutAction;
    private AddContentAction addContentAction;
    private ZoomToFitAction zoomToFitAction;
    private CopyAction copyAction;
    private SaveAction saveAction;
    private SaveAsAction saveAsAction;
    private OpenAction openAction;
    private PatternAction patternAction;
    private ImageAction imageAction;
    private CodeConvertAction codeConvertAction;
    private RedoAction redoAction;
    private UndoAction undoAction;

    public ActionManager()
    {
        initialiseAction();
    }

    private void initialiseAction()
    {
        this.aboutUsAction = new AboutUsAction();
        this.editAction = new EditAction();
        this.exitAction = new ExitAction();
        this.addDiagramAction = new AddDiagramAction();
        this.addPackageAction = new AddPackageAction();
        this.addProjectAction = new AddProjectAction();
        this.deleteAction = new DeleteAction();
        this.addClassAction = new AddClassAction();
        this.addInterfaceAction = new AddInterfaceAction();
        this.addEnumAction = new AddEnumAction();
        this.addRelationshipAction = new AddRelationshipAction();
        this.moveElementAction = new MoveElementAction();
        this.selectElementAction = new SelectElementAction();
        this.deleteElementAction = new DeleteElementAction();
        this.zoomInAction = new ZoomInAction();
        this.zoomOutAction = new ZoomOutAction();
        this.addContentAction = new AddContentAction();
        this.zoomToFitAction = new ZoomToFitAction();
        this.copyAction = new CopyAction();
        this.saveAction = new SaveAction();
        this.saveAsAction = new SaveAsAction();
        this.openAction = new OpenAction();
        this.patternAction = new PatternAction();
        this.imageAction = new ImageAction();
        this.codeConvertAction = new CodeConvertAction();
        this.redoAction = new RedoAction();
        this.undoAction = new UndoAction();
        this.undoAction.setEnabled(false);
        this.redoAction.setEnabled(false);

    }
}
