package main.game;

import main.game.util.ConstructableObject;
import main.reader.InputReader;
import main.reader.util.BuildConstruct;
import main.reader.util.RawTextConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Object extends ConstructableObject
{
    private String name = "Some Unnamed Object";
    private boolean existsInWorld = true;
    private boolean visible = true;

    private HashMap<String, String> data = new HashMap<String, String>();
    private ArrayList<Action> actions = new ArrayList<>();

    @Override
    public HashMap<String, BiConsumer<ConstructableObject, String>> fieldConsumers()
    {
        return new HashMap<String, BiConsumer<ConstructableObject, String>>()
        {{
            put("name", (o, s) -> {((Object) o).setName(s);InputReader.objectNames.put(s, ((Object) o));});
            put("visible", (o, s) -> ((Object) o).setVisible(Boolean.valueOf(s)));
            put("default", (o, s) -> {String[] args = s.split(",");((Object) o).getData().put(args[0], args[1]);});
        }};
    }

    @Override
    public BiConsumer<ConstructableObject, ArrayList<ConstructableObject>> objectConsumer()
    {
        return (ob, a) ->
        {
            ((Object) ob).setActions(a.stream().map(o -> ((Action) o)).collect(Collectors.toCollection(ArrayList::new)));
            ((Object) ob).getActions().forEach(ia -> ia.getSynonyms().forEach(sy -> {String key = sy + "<-" + ((Object) ob).getName(); InputReader.oaActions.put(key, ia);InputReader.oaObjects.put(key, ((Object) ob));}));
        };
    }

    public Object(BuildConstruct construct)
    {
        initialize(construct);
    }

    @Deprecated
    public Object()
    {

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
        if (data == null)
        {
            data = new HashMap<>();
        }
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
