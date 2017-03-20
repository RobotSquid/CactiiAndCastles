package main.game.util;

import main.reader.util.BuildConstruct;
import main.reader.util.RawTextConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

public abstract class ConstructableObject
{
    public abstract HashMap<String, BiConsumer<ConstructableObject, String>> fieldConsumers();

    public abstract BiConsumer<ConstructableObject, ArrayList<ConstructableObject>> objectConsumer();

    public ConstructableObject(BuildConstruct construct)
    {
        for (String s : construct.getValues().keySet())
        {
            if (fieldConsumers().containsKey(s))
            {
                fieldConsumers().get(s).accept(this, construct.getValues().get(s));
            }
            else
            {
                if (fieldConsumers().containsKey("default"))
                {
                    fieldConsumers().get("default").accept(this, s + "," + construct.getValues().get(s));
                }
                else
                {
                    System.out.println("No field found: " + s);
                }
            }
        }

        objectConsumer().accept(this, construct.getObjects());
    }
}
