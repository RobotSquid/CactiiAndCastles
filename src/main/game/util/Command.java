package main.game.util;

import main.game.Case;
import main.game.Object;

public class Command
{
    private Case action;
    private ActionType type;
    private Object object;
    private Object auxiliary;

    public Command() {}

    public Command(Case action, ActionType type, Object object, Object auxiliary)
    {
        this.action = action;
        this.type = type;
        this.object = object;
        this.auxiliary = auxiliary;
    }

    public Case getAction()
    {
        return action;
    }

    public void setAction(Case action)
    {
        this.action = action;
    }

    public ActionType getType()
    {
        return type;
    }

    public void setType(ActionType type)
    {
        this.type = type;
    }

    public Object getObject()
    {
        return object;
    }

    public void setObject(Object object)
    {
        this.object = object;
    }

    public Object getAuxiliary()
    {
        return auxiliary;
    }

    public void setAuxiliary(Object auxiliary)
    {
        this.auxiliary = auxiliary;
    }

    public enum ActionType
    {
        SHOW_OBJECTS,
        STATISTICS,
        USER_DEFINED
    }
}
