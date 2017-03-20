package main.reader;

import main.game.*;
import main.game.Object;
import main.game.util.ConstructableObject;
import main.reader.util.BuildConstruct;
import main.reader.util.ExtendingConstruct;
import main.reader.util.RawTextConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapReader
{
    public static Castle getCastle(String path)
    {
        RawTextConstruct castle = new RawTextConstruct(path);

        ArrayList<RawTextConstruct> rawCastles = new ArrayList<RawTextConstruct>() {{add(castle);}};
        ArrayList<Function<BuildConstruct, ConstructableObject>> builders = new ArrayList<Function<BuildConstruct, ConstructableObject>>() {{add(Castle::new);add(Room::new);add(Object::new);add(Action::new);add(Case::new);}};

        HashMap<ExtendingConstruct, ConstructableObject> realCastles = buildObjects(rawCastles, builders);

        return ((Castle) ((ArrayList) realCastles.values()).get(0));
    }

    private static HashMap<ExtendingConstruct, ConstructableObject> buildObjects(ArrayList<RawTextConstruct> raw, ArrayList<Function<BuildConstruct, ConstructableObject>> builders)
    {
        return buildConstructs(raw, builders.get(0), builders.size() != 0 ? buildObjects(allSubObjects(raw), ((ArrayList<Function<BuildConstruct, ConstructableObject>>) builders.subList(1, builders.size()))) : null);
    }

    private static ArrayList<RawTextConstruct> allSubObjects(ArrayList<RawTextConstruct> parents)
    {
        final ArrayList<RawTextConstruct> children = new ArrayList<>();
        parents.forEach(o -> children.addAll(o.getConstructs()));
        return children;
    }

    private static HashMap<ExtendingConstruct, ConstructableObject> buildConstructs(ArrayList<RawTextConstruct> rawConstructs, Function<BuildConstruct, ConstructableObject> builder, HashMap<ExtendingConstruct, ConstructableObject> children)
    {
        HashMap<RawTextConstruct, ExtendingConstruct> constructs = new HashMap<>();
        HashMap<String, RawTextConstruct> constructDefined = new HashMap<>();
        HashMap<ExtendingConstruct, ConstructableObject> built = new HashMap<>();

        //CONSTRUCT
        rawConstructs.forEach(r -> constructs.put(r, new ExtendingConstruct(r, builder)));
        //DEFINED
        rawConstructs.stream().filter(o -> o.getValues().containsKey("defined")).forEach(o -> constructDefined.put(o.getValues().get("defined"), o));
        //EXTENDING
        rawConstructs.stream().filter(a -> a.getValues().containsKey("extends") && constructDefined.containsKey(a.getValues().get("extends"))).forEach(a -> constructs.get(a).setParent(constructs.get(constructDefined.get(a.getValues().get("extends")))));
        //BASENAME
        rawConstructs.forEach(r -> constructs.get(r).calculateBaseName());
        //DEPTH
        rawConstructs.forEach(r -> constructs.get(r).calculateDepth());
        //SORT
        rawConstructs.sort((o1, o2) -> constructs.get(o2).getDepth() < constructs.get(o1).getDepth() ? -1 : (constructs.get(o1).getDepth() < constructs.get(o2).getDepth() ? 1 : 0));
        //INHERIT
        rawConstructs.forEach(r -> constructs.get(r).extendParent(new ArrayList<>(children.keySet())));
        //BUILD
        rawConstructs.forEach(r -> built.put(constructs.get(r), constructs.get(r).buildOut(children)));

        return built;
    }
}
