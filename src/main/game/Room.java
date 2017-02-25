package main.game;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Room
{
    private String name;
    private ArrayList<Object> objects = new ArrayList<Object>();
    //private HashMap doors = new HashMap<Room, Connection>();

    public Room()
    {
        name = "Some Unnamed Room";
        objects = new ArrayList();
    }

    public Room(String s, ArrayList o/*, HashMap d*/)
    {
        name = s;
        objects = o;
        //doors = d;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<Object> getObjects()
    {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects)
    {
        this.objects = objects;
    }
}
