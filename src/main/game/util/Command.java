package main.game.util;

import main.game.Case;
import main.game.Object;

public class Command
{
    private Case action;
    private String type;
    private Object object;
    private Object auxiliary;

    public Command()
    {
        this.action = new Case();
        this.type = null;
        this.object = new Object();
        this.auxiliary = null;
    }

    public Command(Case action, String type, Object object, Object auxiliary)
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
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
}
