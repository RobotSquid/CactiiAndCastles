package main.reader.util;

import main.game.Room;
import main.game.util.ConstructableObject;
import main.helper.ArrayHelper;
import main.reader.MapReader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
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

    public void extendParent(ArrayList<ExtendingConstruct> children)
    {
        if (parent != null)
        {
            parent.getRaw().getValues().keySet().stream().filter(k -> !MapReader.NOT_USED.contains(k) && !raw.getValues().containsKey(k)).forEach(k -> raw.getValues().put(k, parent.getRaw().getValues().get(k)));

            if (children != null)
            {
                HashMap<RawTextConstruct, String> childrenBase = new HashMap<>();
                children.forEach(ec -> childrenBase.put(ec.getRaw(), ec.baseName));
                parent.getRaw().getConstructs().stream().filter(c -> !ArrayHelper.arrayContains(raw.getConstructs(), p -> childrenBase.get(p).equals(childrenBase.get(c)))).forEach(c -> raw.getConstructs().add(/*0,*/ c));
            }
        }
    }

    public void calculateBaseName()
    {
        if (baseName.equals("") && parent != null)
        {
            baseName = parent.requestCalculateBaseName(this);
        }
        else if (parent == null && raw.getValues().containsKey("defined"))
        {
            baseName = raw.getValues().get("defined");
        }
    }

    private String requestCalculateBaseName(ExtendingConstruct requester)
    {
        if (requester != this)
        {
            calculateBaseName();
        }
        else
        {
            baseName = "";
        }
        return baseName;
    }

    public void calculateDepth()
    {
        if (depth == 0 && parent != null)
        {
            depth = parent.requestCalculateDepth(this);
            depth = depth == -1 ? -1 : depth + 1;
        }
    }

    private int requestCalculateDepth(ExtendingConstruct requester)
    {
        if (requester != this)
        {
            calculateDepth();
        }
        else
        {
            depth = -1;
        }
        return depth;
    }

    public ConstructableObject buildOut(HashMap<ExtendingConstruct, ConstructableObject> children)
    {
        HashMap<String, String> values = raw.getValues();
        ArrayList<ConstructableObject> objects = new ArrayList<>();
        if (children != null)
        {
            children.keySet().stream().filter(k -> raw.getConstructs().contains(k.getRaw())).forEach(k -> objects.add(children.get(k)));
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
