package main.reader;

import main.CactiiAndCastles;
import main.game.*;
import main.game.Object;
import main.game.util.Command;
import main.helper.ArrayHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.function.Predicate;

public class InputReader
{
    public static HashMap<String, Room> roomNames = new HashMap<>();
    public static HashMap<String, Object> objectNames = new HashMap<>();

    public static HashMap<String, Object> oaObjects = new HashMap<>();
    public static HashMap<String, Action> oaActions = new HashMap<>();

    private static HashMap<String, Command.ActionType> specialActions = new HashMap<>();

    static
    {
        // CHECK OBJECTS
        specialActions.put("objects", Command.ActionType.SHOW_OBJECTS);
        specialActions.put("look around", Command.ActionType.SHOW_OBJECTS);
        specialActions.put("observe", Command.ActionType.SHOW_OBJECTS);

        // CHECK STATS
        specialActions.put("stats", Command.ActionType.STATISTICS);
        specialActions.put("contemplate", Command.ActionType.STATISTICS);

        //LOAD FILE
        specialActions.put("load castle", Command.ActionType.LOAD_MAP);
        specialActions.put("open castle", Command.ActionType.LOAD_MAP);
    }

    public static Command parseInput(String s, Player player)
    {
        Command c = new Command();

        String action = findMatch(new ArrayList<>(specialActions.keySet()), s);
        if (action != null)
        {
            c.setType(specialActions.get(action));
            return c;
        }
        else
        {
            c.setType(Command.ActionType.USER_DEFINED);

            String finalS = s;
            Predicate searcher = is ->
            {
                String[] parts = ((String) is).split("<-");
                return finalS.matches("(?i).*" + parts[0] + ".*" + parts[1] + ".*");
            };
            String os = ((String) ArrayHelper.getFirstSatisfy(new ArrayList<>(oaObjects.keySet()), searcher));
            String as = ((String) ArrayHelper.getFirstSatisfy(new ArrayList<>(oaActions.keySet()), searcher));

            removeFirst(os + ".*" + as, s);

            if (os != null)
            {
                if ((player.getCurrentRoom().getObjects().contains(oaObjects.get(os)) && oaObjects.get(os).isVisible()) || player.getInventory().contains(oaObjects.get(os)))
                {

                    c.setObject(oaObjects.get(os));
/*
                    ArrayList<String> possibleVerbs = new ArrayList<>();
                    c.getObject().getActions().forEach(a -> a.getSynonyms().forEach(possibleVerbs::add));
                    action = findMatch(s, possibleVerbs);*/

                        /*
                        s = removeFirst(action, s);
                        s = removeFirst(object, s);*/

                    String aux = findMatch(new ArrayList<>(objectNames.keySet()), s);
                    if (aux != null)
                    {
                        if ((player.getCurrentRoom().getObjects().contains(objectNames.get(aux)) && objectNames.get(aux).isVisible()) || player.getInventory().contains(objectNames.get(aux)))
                        {
                            c.setAuxiliary(objectNames.get(aux));
                        }
                        else
                        {
                            CactiiAndCastles.println("What Object?");
                        }
                    }
                }
            }
            else
            {
                CactiiAndCastles.println("What object?");
            }

            if (as != null)
            {

                Action a = oaActions.get(as);

                if (a != null)
                {
                    Case cse = ((Case) ArrayHelper.getFirstSatisfy(a.getCases(), c1 -> ((Case) c1).getCondition().evaluate(player, c.getObject(), c.getAuxiliary())));
                    if (cse != null)
                    {
                        System.out.println(cse.getCondition().getLiteral());
                        c.setAction(cse);
                        return c;
                    }
                    else
                    {
                        CactiiAndCastles.println("At this moment you can't do that to the poor object!");
                    }
                }
                else
                {
                    CactiiAndCastles.println("This is not something you can do that to the poor object!");
                }
            }
            else
            {
                CactiiAndCastles.println("What did you want to do again?");
            }
        }

        return null;
    }

    private static String findMatch(ArrayList<String> ba, String s)
    {
        return ((String) ArrayHelper.getFirstSatisfy(ba, is -> s.matches("(?i).*" + is.toString() + ".*")));
    }

    private static String removeFirst(String s, String se)
    {
        return se.replaceFirst("(?i).*" + s + "\\s*", "");
    }
}
