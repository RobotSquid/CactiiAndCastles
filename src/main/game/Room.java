package main.game;

import main.game.util.ConstructableObject;
import main.reader.util.BuildConstruct;
import main.reader.util.RawTextConstruct;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Room extends ConstructableObject
{
    private String name = "Some Unnamed Room";
    private ArrayList<Object> objects = new ArrayList<Object>();
    //private HashMap doors = new HashMap<Room, Connection>();

    @Override
    public HashMap<String, BiConsumer<ConstructableObject, String>> fieldConsumers()
    {
        return new HashMap<String, BiConsumer<ConstructableObject, String>>()
        {{
            put("name", (r, s) -> ((Room) r).setName(s));
        }};
    }

    @Override
    public BiConsumer<ConstructableObject, ArrayList<ConstructableObject>> objectConsumer()
    {
        return (r, a) -> ((Room) r).setObjects(a.stream().map(o -> ((Object) o)).collect(Collectors.toCollection(ArrayList::new)));
    }

    public Room(BuildConstruct construct)
    {
        super(construct);
    }

    public Room()
    {
        this(null);
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
