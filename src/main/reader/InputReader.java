package main.reader;

import main.CactiiAndCastles;
import main.game.Object;
import main.game.Player;
import main.game.Room;
import main.game.util.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class InputReader
{
    public static HashMap<String, Room> roomNames = new HashMap<>();
    public static HashMap<String, String> actionNames = new HashMap<>();
    public static HashMap<String, Object> objectNames = new HashMap<>();

    static
    {
        // CHECK OBJECTS
        actionNames.put("objects", "objects");
        actionNames.put("look around", "objects");
        actionNames.put("observe", "objects");

        // CHECK STATS
        actionNames.put("stats", "stats");
        actionNames.put("contemplate", "stats");
    }

    public static Command parseInput(String s, Player player)
    {
        //TODO: Add local objects, actions generation

        Command c = new Command();

        String action = findMatch(s, actionNames.keySet());
        if (action != null)
        {
            c.setType(actionNames.get(action));

            s = removeFirst(action, s);
            String object = findMatch(s, objectNames.keySet());
            if (object != null)
            {
                if ((player.getCurrentRoom().getObjects().contains(objectNames.get(object)) && objectNames.get(object).isVisible()) || player.getInventory().contains(objectNames.get(object)))
                {
                    c.setObject(objectNames.get(object));

                    s = removeFirst(object, s);
                    String aux = findMatch(s, objectNames.keySet());
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

                    /*if (c.getObject().getActions().containsKey(actionNames.get(action)))
                    {
                        ArrayList<ActionParameters> apList = c.getObject().getActions().get(actionNames.get(action)).getCases();

                        int i;
                        for (i = 0; i < apList.size() && !apList.get(i).getCondition().evaluate(player, c.getObject(), c.getAuxiliary()); i++) {}

                        if (i < apList.size() && apList.get(i).getCondition().evaluate(player, c.getObject(), c.getAuxiliary()))
                        {
                            c.setAction(apList.get(i));
                        }
                        else
                        {
                            CactiiAndCastles.println("You can't do that to the poor object!");
                        }
                    }
                    else
                    {
                        CactiiAndCastles.println("You can't do that to the poor object!");
                    }*/
                }
                else
                {
                    CactiiAndCastles.println("What object?");
                }
            }

            return c;
        }
        else
        {
            CactiiAndCastles.println("Sorry, what do you want to do again?");
        }

        return null;
    }

    private static String findMatch(String s, Set<String> ba)
    {
        ArrayList<String> a = new ArrayList<>(ba);
        boolean b = false;
        int i;
        for (i = -1; i < a.size()-1 && !b; i++) {b = s.matches("(?i).*" + a.get(i+1) + ".*");}
        return b ? a.get(i) : null;
    }

    private static String removeFirst(String s, String se)
    {
        return se.replaceFirst("(?i).*" + s + "\\s*", "");
    }
}
