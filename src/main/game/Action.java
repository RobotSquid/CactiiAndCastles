package main.game;

import main.game.util.Command;
import main.game.util.ConstructableObject;
import main.reader.InputReader;
import main.reader.util.BuildConstruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Action extends ConstructableObject
{
    private ArrayList<String> synonyms;
    private ArrayList<Case> cases;

    @Override
    public HashMap<String, BiConsumer<ConstructableObject, String>> fieldConsumers()
    {
        return new HashMap<String, BiConsumer<ConstructableObject, String>>()
        {{
            put("action", (action, s) -> ((Action) action).setSynonyms(new ArrayList<>(Arrays.asList(s.split("\\s*,\\s*")))));
        }};
    }

    @Override
    public BiConsumer<ConstructableObject, ArrayList<ConstructableObject>> objectConsumer()
    {
        return (ac, a) -> ((Action) ac).setCases(a.stream().map(o -> ((Case) o)).collect(Collectors.toCollection(ArrayList::new)));
    }

    @Deprecated
    public Action() {}

    public Action(BuildConstruct construct)
    {
        initialize(construct);
    }

    public ArrayList<String> getSynonyms()
    {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms)
    {
        this.synonyms = synonyms;
    }

    public ArrayList<Case> getCases()
    {
        return cases;
    }

    public void setCases(ArrayList<Case> cases)
    {
        this.cases = cases;
    }

}
