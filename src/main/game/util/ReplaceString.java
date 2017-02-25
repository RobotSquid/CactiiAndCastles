package main.game.util;

import main.CactiiAndCastles;
import main.game.Object;

public class ReplaceString
{
    private String literal = "";

    public ReplaceString() {}

    public ReplaceString(String s)
    {
        literal = s;
    }

    public String getLiteral()
    {
        return literal;
    }

    public void setLiteral(String lit)
    {
        this.literal = lit;
    }

    public void say(Object object, Object auxiliary)
    {
        String say = literal;
        say = say.replace("{object}", object == null ? "something" : object.getName());
        say = say.replace("{auxiliary}", auxiliary == null ? "something" : auxiliary.getName());
        CactiiAndCastles.println(say);
    }
}
