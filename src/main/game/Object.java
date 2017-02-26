package main.game;

import main.game.util.Action;

import java.util.ArrayList;
import java.util.HashMap;

public class Object
{
    private String name;
    private boolean existsInWorld = true;
    private boolean visible = true;

    private HashMap data = new HashMap<String, String>();
    private ArrayList<Action> actions = new ArrayList<>();

    public Object()
    {
        name = "Some Unnamed Object";
    }

    public Object(String s, ArrayList<Action> h)
    {
        name = s;
        actions = h;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<Action> getActions()
    {
        return actions;
    }

    public void setActions(ArrayList<Action> actions)
    {
        this.actions = actions;
    }

    public HashMap<String, String> getData()
    {
        return data;
    }

    public void setData(HashMap data)
    {
        this.data = data;
    }

    public boolean isExistsInWorld()
    {
        return existsInWorld;
    }

    public void setExistsInWorld(boolean existsInWorld)
    {
        this.existsInWorld = existsInWorld;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
