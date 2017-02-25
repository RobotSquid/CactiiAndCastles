package main.game.util;

public class ActionParameters
{
    private int health;
    private int happiness;
    private ReplaceString string;
    private Condition condition;
    private String action;

    public ActionParameters()
    {
        health = 0;
        happiness = 0;
        string = new ReplaceString();
        condition = new Condition("default");
        action = "";
    }

    public ActionParameters(int h1, int h2, ReplaceString s, boolean u, boolean ss, String c, String a)
    {
        health = h1;
        happiness = h2;
        string = s;
        condition = new Condition(c);
        action = a;
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
