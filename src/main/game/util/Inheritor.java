package main.game.util;

import main.util.InputReader;

public abstract class Inheritor implements InheritorInterface
{
    private String inheriting;
    private String defined;

    public String getInheriting()
    {
        return inheriting;
    }

    public void setInheriting(String inheriting)
    {
        this.inheriting = inheriting;
    }

    public String getDefined()
    {
        return defined;
    }

    public void setDefined(String defined)
    {
        this.defined = defined;
    }
}
