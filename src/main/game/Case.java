package main.game;

import main.game.util.Condition;
import main.game.util.ConstructableObject;
import main.game.util.ReplaceString;
import main.reader.util.BuildConstruct;
import main.reader.util.RawTextConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Case extends ConstructableObject
{
    private int health = 0;
    private int happiness = 0;
    private ReplaceString string = new ReplaceString();
    private Condition condition = new Condition("default");
    private String action = "";

    @Override
    public HashMap<String, BiConsumer<ConstructableObject, String>> fieldConsumers()
    {
        return new HashMap<String, BiConsumer<ConstructableObject, String>>()
        {{
            put("health", (c, s) -> ((Case) c).setHealth(Integer.valueOf(s)));
            put("happiness", (c, s) -> ((Case) c).setHappiness(Integer.valueOf(s)));
            put("case", (c, s) -> ((Case) c).setCondition(new Condition(s)));
            put("string", (c, s) -> ((Case) c).setString(new ReplaceString(s)));
            put("command", (c, s) -> ((Case) c).setCommand(s));
        }};
    }

    @Override
    public BiConsumer<ConstructableObject, ArrayList<ConstructableObject>> objectConsumer()
    {
        return (c, s) -> {};
    }

    public Case(BuildConstruct construct)
    {
        initialize(construct);
    }

    @Deprecated
    public Case()
    {

    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public int getHappiness()
    {
        return happiness;
    }

    public void setHappiness(int happiness)
    {
        this.happiness = happiness;
    }

    public ReplaceString getString()
    {
        return string;
    }

    public void setString(ReplaceString str)
    {
        this.string = str;
    }

    public Condition getCondition()
    {
        return condition;
    }

    public void setCondition(Condition condition)
    {
        this.condition = condition;
    }

    public String getCommand()
    {
        return action;
    }

    public void setCommand(String action)
    {
        this.action = action;
    }
}
