package main.game;

import main.game.util.ConstructableObject;
import main.reader.util.BuildConstruct;
import main.reader.util.RawTextConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Castle extends ConstructableObject
{
    private String welcome = "";
    private ArrayList<Room> rooms = new ArrayList<Room>();

    @Deprecated
    public Castle()
    {

    }

    @Override
    public HashMap<String, BiConsumer<ConstructableObject, String>> fieldConsumers()
    {
        return new HashMap<String, BiConsumer<ConstructableObject, String>>()
        {{
            put("welcome", (c, s) -> ((Castle) c).setWelcome(s));
        }};
    }

    @Override
    public BiConsumer<ConstructableObject, ArrayList<ConstructableObject>> objectConsumer()
    {
        return (c, a) -> ((Castle) c).setRooms(a.stream().map(o -> ((Room) o)).collect(Collectors.toCollection(ArrayList::new)));
    }

    public Castle(BuildConstruct construct)
    {
        initialize(construct);
    }

    public ArrayList<Room> getRooms()
    {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms)
    {
        this.rooms = rooms;
    }

    public String getWelcome()
    {
        return welcome;
    }

    public void setWelcome(String welcome)
    {
        this.welcome = welcome;
    }
}
