package main.reader.util;

import main.game.util.ConstructableObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExtendingConstruct
{
    private RawTextConstruct raw;
    private ExtendingConstruct parent;
    private String baseName = "";
    private int depth = 0;

    private Function<BuildConstruct, ConstructableObject> builder;
    private ConstructableObject out;

    public ExtendingConstruct(RawTextConstruct raw, Function<BuildConstruct, ConstructableObject> builder)
    {
        this.raw = raw;
        this.builder = builder;
    }

    public void extendParent()
    {
        parent.getRaw().getValues().keySet().stream().filter(k -> !raw.getValues().containsKey(k)).forEach(k -> raw.getValues().put(k, parent.getRaw().getValues().get(k)));
        //parent.getRaw().getConstructs().stream().filter(c -> )
    }

    public void calculateBaseName()
    {
        requestCalculateBaseName(this);
    }

    private String requestCalculateBaseName(ExtendingConstruct requester)
    {
        if (requester != this)
        {
            if (baseName.equals("") && parent != null)
            {
                baseName = parent.requestCalculateBaseName(requester);
            }
        }
        else
        {
            baseName = "";
        }
        return baseName;
    }

    public void calculateDepth()
    {
        requestCalculateDepth(this);
    }

    private int requestCalculateDepth(ExtendingConstruct requester)
    {
        if (requester != this)
        {
            if (depth == 0 && parent != null)
            {
                depth = parent.requestCalculateDepth(requester) + 1;
            }
        }
        else
        {
            depth = -1;
        }
        return depth;
    }

    public ConstructableObject buildOut(HashMap<RawTextConstruct, ConstructableObject> children)
    {
        HashMap<String, String> values = raw.getValues();
        ArrayList<ConstructableObject> objects = new ArrayList<>();
        if (children != null)
        {
            children.keySet().stream().filter(k -> raw.getConstructs().contains(k)).forEach(k -> objects.add(children.get(k)));
        }
        out = builder.apply(new BuildConstruct(values, objects));
        return out;
    }

    public ExtendingConstruct getParent()
    {
        return parent;
    }

    public void setParent(ExtendingConstruct parent)
    {
        this.parent = parent;
    }

    public String getBaseName()
    {
        return baseName;
    }

    public void setBaseName(String baseName)
    {
        this.baseName = baseName;
    }

    public RawTextConstruct getRaw()
    {
        return raw;
    }

    public int getDepth()
    {
        return depth;
    }

    public ConstructableObject getOut()
    {
        return out;
    }
}
