package main.game.util;

import java.util.ArrayList;

public class Action
{
    private ArrayList<String> synonyms;
    private ArrayList<ActionParameters> cases;

    public Action() {}

    public ArrayList<String> getSynonyms()
    {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms)
    {
        this.synonyms = synonyms;
    }

    public ArrayList<ActionParameters> getCases()
    {
        return cases;
    }

    public void setCases(ArrayList<ActionParameters> cases)
    {
        this.cases = cases;
    }
}
