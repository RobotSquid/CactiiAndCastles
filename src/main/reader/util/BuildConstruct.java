package main.reader.util;

import main.game.util.ConstructableObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildConstruct
{
    private HashMap<String, String> values;
    private ArrayList<ConstructableObject> objects;

    public BuildConstruct(HashMap<String, String> values, ArrayList<ConstructableObject> objects)
    {
        this.values = values;
        this.objects = objects;
    }

    public HashMap<String, String> getValues()
    {
        return values;
    }

    public ArrayList<ConstructableObject> getObjects()
    {
        return objects;
    }
}
