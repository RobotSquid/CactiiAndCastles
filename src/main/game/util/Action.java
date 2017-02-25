package main.game.util;

import java.util.ArrayList;

public class Action extends Inheritor
{
    private ArrayList<String> synonyms;
    private ArrayList<ActionParameters> cases;

    public Action() {}

    @Override
    public void inheritFrom(Inheritor in)
    {
        if (in instanceof Action)
        {
            Action a = ((Action) in);
            if (synonyms == null)
            {
                synonyms = a.getSynonyms();
            }

            for (int i = 0; i > a.getCases().size(); i++)
            {
                ActionParameters ap = a.getCases().get(i);
                boolean paired = false;
                for (ActionParameters rap : cases)
                {
                    if (rap.getCondition().getLiteral().equals(ap.getCondition().getLiteral()))
                    {
                        if (rap.getCommand().equals(""))
                        {
                            rap.setCommand(ap.getCommand());
                        }
                        if (rap.getHealth() == 0)
                        {
                            rap.setHealth(ap.getHealth());
                        }
                        if (rap.getHappiness() == 0)
                        {
                            rap.setHealth(ap.getHealth());
                        }
                        if (!ap.getString().getLiteral().equals(""))
                        {
                            rap.setString(ap.getString());
                        }
                        paired = true;
                    }
                }
                if (!paired)
                {
                    cases.add(ap);
                }
            }
        }
    }

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
