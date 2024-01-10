package raf.dsw.classycraft.app.gui.swing.state;

import lombok.Getter;
import raf.dsw.classycraft.app.gui.swing.state.stateImpl.*;

@Getter
public class StateManager {
    private State currentState;
    private AddClassState addClassState;
    private AddEnumState addEnumState;
    private AddInterfaceState addInterfaceState;
    private SelectState selectState;
    private AddGeneralizationState generalizationState;
    private AddAgregationState agregationState;
    private AddCompositionState compositionState;
    private AddDependancyState dependancyState;
    private ZoomInState zoomInState;
    private ZoomOutState zoomOutState;
    private ContentState contentState;
    private MoveElementState moveElementState;
    private ContentState addContentState;
    private ZoomToFitState zoomToFitState;
    private DeleteState deleteState;
    private CopyState copyState;
    public StateManager(){
        initialiseStates();
    }

    private void initialiseStates()
    {
        this.addClassState = new AddClassState();
        this.addEnumState = new AddEnumState();
        this.addInterfaceState = new AddInterfaceState();
        this.selectState = new SelectState();
        this.generalizationState = new AddGeneralizationState();
        this.agregationState = new AddAgregationState();
        this.compositionState = new AddCompositionState();
        this.dependancyState = new AddDependancyState();
        this.currentState = selectState;
        this.zoomInState = new ZoomInState();
        this.zoomOutState = new ZoomOutState();
        this.addContentState = new ContentState();
        this.zoomToFitState = new ZoomToFitState();
        this.contentState = new ContentState();
        this.moveElementState = new MoveElementState();
        this.deleteState = new DeleteState();
        this.copyState = new CopyState();
    }

    public void setAddClassState() {
        this.currentState = addClassState;
    }
    public void setAddEnumState() {
        this.currentState = addEnumState;
    }
    public void setAddInterfaceState() {
        this.currentState = addInterfaceState;
    }
    public void setSelectState() {
        this.currentState = selectState;
    }
    public void setGeneralizationState() {
        this.currentState = generalizationState;
    }
    public void setAgregationState() {
        this.currentState = agregationState;
    }
    public void setCompositionState() {
        this.currentState = compositionState;
    }
    public void setZoomInState() {this.currentState = zoomInState;}
    public void setZoomOutState() {this.currentState = zoomOutState;}
    public void setAddContentState() {this.currentState = addContentState;}
    public void setZoomToFitState() {this.currentState = zoomToFitState;}
    public void setAddDependancyState() {
        this.currentState = new AddDependancyState();
    }
    public void setMoveElementState() {this.currentState = moveElementState;}
    public void setDeleteState() {this.currentState = deleteState;}
    public void setCopyState() {this.currentState = copyState;}
}
