package main.util;

import main.game.Castle;
import main.game.Object;
import main.game.Room;
import main.game.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Stream;

public class MapReader
{

    private static ArrayList<String> readLinesFromFile(String path)
    {
        ArrayList data = new ArrayList<String>();
        try
        {
            FileReader reader = new FileReader(path);
            BufferedReader textReader = new BufferedReader(reader);
            String line = "";
            while (line != null)
            {
                line = textReader.readLine();
                if (line != null)
                {
                    data.add(line);
                }
            }
            textReader.close();
        }
        catch (Exception e)
        {
            System.out.println("Couldn't load map, " + e.getMessage());
        }
        return data;
    }

    private static ArrayList<ArrayList<String>> getSubObjects(ArrayList<String> list)
    {
        ArrayList out = new ArrayList<ArrayList<String>>();
        int parenCount = 0;
        int start = 0;
        int end = 0;
        for (int i = 0; i < list.size(); i++)
        {
            String line = list.get(i);
            for (char c : line.toCharArray())
            {
                if (c == '[')
                {
                    parenCount++;
                    if (parenCount == 1)
                    {
                        start = i;
                    }
                }
                if (c == ']')
                {
                    parenCount--;
                    if (parenCount == 0)
                    {
                        end = i;
                        out.add(new ArrayList<String>(list.subList(start + 1, end)));
                    }
                }
            }
        }
        return out;
    }

    private static HashMap<String, String> getValues(ArrayList<String> list)
    {
        HashMap out = new HashMap<String, String>();
        String line;
        String[] parts;

        for (int i = 0; i < list.size() && !list.get(i).contains("["); i++)
        {
            line = list.get(i);
            if (line.contains(":"))
            {
                parts = line.split(":");

                String s = parts[0];
                int start;
                int end;
                for (start = 0; start < s.length() && (((int) s.charAt(start) < 33) || ((int) s.charAt(start)) > 122); start++) {}
                for (end = start; end < s.length() && ((int) s.charAt(end)) > 32 && ((int) s.charAt(end)) < 123; end++) {}
                parts[0] = parts[0].substring(start, end);

                out.put(parts[0], parts[1]);
            }
        }
        return out;
    }

    public static Castle getCastle(String path)
    {
        ArrayList<String> input = readLinesFromFile(path);

        HashMap<String, String> castleFields;
        ArrayList<ArrayList<String>> rooms;
        HashMap<String, String> roomFields;
        ArrayList<ArrayList<String>> objects;
        HashMap<String, String> objectFields;
        ArrayList<ArrayList<String>> actions;
        HashMap<String, String> actionFields;
        ArrayList<ArrayList<String>> cases;
        HashMap<String, String> caseFields;

        castleFields = getValues(input);
        rooms = getSubObjects(input);


        //CASTLE DATA
        Castle realCastle = new Castle();

        for (String key : castleFields.keySet())
        {
            switch (key)
            {
                case "welcome":
                    realCastle.setWelcome(castleFields.get(key));
                    break;
                default:
                    System.out.println("No field found in Castle: " + key);
                    break;
            }
        }

        //ORDER OBJECTS
        HashMap<ArrayList<String>, ArrayList<String>> roomMap = new HashMap<>();
        ArrayList<ArrayList<String>> rawObjects = new ArrayList<>();
        for (ArrayList<String> seqRoom : rooms)
        {
            for (ArrayList<String> seqObj : getSubObjects(seqRoom))
            {
                roomMap.put(seqObj, seqRoom);
                rawObjects.add(seqObj);
            }
        }
        rawObjects = inheritOrderList(roomMap);

        //ORDER ACTIONS
        HashMap<ArrayList<String>, ArrayList<String>> objectMap = new HashMap<>();
        ArrayList<ArrayList<String>> rawActions = new ArrayList<>();

        for (ArrayList<String> seqObj : rawObjects)
        {
            for (ArrayList<String> seqAct : getSubObjects(seqObj))
            {
                objectMap.put(seqAct, seqObj);
                rawActions.add(seqAct);
            }
        }
        rawActions = inheritOrderList(objectMap);

        //TODO actions parse

        //TODO objects parse

        ArrayList<Room> realRooms = new ArrayList<>();

        for (ArrayList<String> room : rooms)
        {
            roomFields = getValues(room);
            objects = getSubObjects(room);

            Room realRoom = new Room();

            for (String key : roomFields.keySet())
            {
                switch (key)
                {
                    case "name":
                        InputReader.roomNames.put(roomFields.get(key), realRoom);
                        realRoom.setName(roomFields.get(key));
                        break;
                    default:
                        System.out.println("No field found in Room: " + key);
                        break;
                }
            }

            /*
            ArrayList<Object> realObjects = new ArrayList<>();

            for (ArrayList<String> object : objects)
            {
                objectFields = getValues(object);
                actions = getSubObjects(object);

                Object realObject = new Object();

                HashMap data = new HashMap<String, String>();

                for (String key : objectFields.keySet())
                {
                    switch (key)
                    {
                        case "name":
                            //InputReader.objectNames.put(objectFields.get(key), realObject);
                            realObject.setName(objectFields.get(key));
                            break;
                        case "defined":
                            realObject.setDefined(objectFields.get(key));
                            break;
                        case "inheriting":
                            realObject.setInheriting(objectFields.get(key));
                            break;
                        case "visible":
                            realObject.setVisible(Boolean.valueOf(objectFields.get(key)));
                            break;
                        default:
                            data.put(key, objectFields.get(key));
                            break;
                    }
                }

                HashMap<String, String> defineMap = new HashMap<>();

                for (ArrayList<String> action : actions)
                {
                    actionFields = getValues(action);

                    if (actionFields.containsKey("defined") && actionFields.containsKey("inheriting"))
                    {
                        defineMap.put(actionFields.get("defined"), actionFields.get("inheriting"));
                    }
                }

                HashMap<Integer, Integer> inheritDepth = new HashMap<>();

                for (int actionN = 0; actionN < actions.size(); actionN++)
                {
                    ArrayList<String> action = actions.get(actionN);
                    actionFields = getValues(action);

                    if (actionFields.containsKey("inheriting"))
                    {
                        inheritDepth.put(actionN, findInheritDepth(actionFields.get("inheriting"), new ArrayList<>(), defineMap));
                    }
                    else
                    {
                        inheritDepth.put()
                    }
                }

                ArrayList<Action> realActions = new ArrayList<>();

                for (ArrayList<String> action : actions)
                {
                    actionFields = getValues(action);
                    cases = getSubObjects(action);

                    Action realAction = new Action();

                    for (String key : actionFields.keySet())
                    {
                        switch (key)
                        {
                            case "action":
                                realAction.setSynonyms(new ArrayList<>(Arrays.asList(actionFields.get(key).split(","))));
                                break;
                            case "inheriting":
                                realAction.setInheriting(actionFields.get(key));
                                break;
                            case "defined":
                                realAction.setDefined(actionFields.get(key));
                                break;
                            default:
                                System.out.println("No field found in Action: " + key);
                                break;
                        }
                    }

                    ArrayList<ActionParameters> realCases = new ArrayList<>();

                    for (ArrayList<String> acase : cases)
                    {
                        caseFields = getValues(acase);

                        ActionParameters ap = new ActionParameters();

                        for (String key : caseFields.keySet())
                        {
                            switch (key)
                            {
                                case "health":
                                    ap.setHealth(Integer.valueOf(caseFields.get(key)));
                                    break;
                                case "happiness":
                                    ap.setHappiness(Integer.valueOf(caseFields.get(key)));
                                    break;
                                case "case":
                                    ap.setCondition(new Condition(caseFields.get(key)));
                                    break;
                                case "string":
                                    ap.setString(new ReplaceString(caseFields.get(key)));
                                    break;
                                case "command":
                                    ap.setCommand(caseFields.get(key));
                                    break;
                                default:
                                    System.out.println("No field found in ActionParameters: " + key);
                                    break;
                            }
                        }

                        realCases.add(ap);
                    }

                    realAction.setCases(realCases);

                    realActions.add(realAction);
                }

                realActions.sort((o1, o2) ->
                {
                    int o2l = inheritDepth.get(o2);
                    int o1l = inheritDepth.get(o1);
                    return o2l < o1l ? -1 : (o1l < o2l ? 1 : 0);
                });

                realActions.stream().filter(a -> inheritDepth.get(a) > 0).forEach(a ->
                {
                    a.inheritFrom(searchInheriting(a.getInheriting(), new ArrayList<>(realActions)));
                });

                realObject.setActions(realActions);
                realObject.setData(data);

                realObjects.add(realObject);
            }

            HashMap<Object, Integer> inheritDepth = new HashMap<>();

            for (Object o : realObjects)
            {
                inheritDepth.put(o, findInheritDepth(o, new ArrayList<>(), new ArrayList<>(realObjects)));
            }

            realObjects.sort((o1, o2) ->
            {
                int o1l = inheritDepth.get(o1);
                int o2l = inheritDepth.get(o2);
                return o2l < o1l ? -1 : (o1l < o2l ? 1 : 0);
            });

            realObjects.stream().filter((object -> inheritDepth.get(object) > 0)).forEach(object ->
            {
                object.inheritFrom(searchInheriting(object.getInheriting(), new ArrayList<>(realObjects)));
            });

            realRoom.setObjects(realObjects);
            */

            realRooms.add(realRoom);
        }

        realCastle.setRooms(realRooms);

        return realCastle;
    }

    private static ArrayList<ArrayList<String>> inheritOrderList(HashMap<ArrayList<String>, ArrayList<String>> actions)
    {
        HashMap<String, String> actionJunctions = new HashMap<>();
        HashMap<String, String> values;

        for (ArrayList<String> seqObj : actions.keySet())
        {
            values = getValues(seqObj);
            if (values.containsKey("inheriting") && values.containsKey("defined"))
            {
                actionJunctions.put(values.get("defined"), values.get("inheriting"));
            }
        }

        HashMap<ArrayList<String>, Integer> inheritDepth = new HashMap<>();

        for (ArrayList<String> rawObj : actions.keySet())
        {
            HashMap<String, String> rawValues = getValues(rawObj);

            if (rawValues.containsKey("inheriting"))
            {
                inheritDepth.put(rawObj, findInheritDepth(rawValues.get("inheriting"), new ArrayList<>(), actionJunctions));
            }
            else
            {
                inheritDepth.put(rawObj, 0);
            }
        }

        ArrayList<ArrayList<String>> out = new ArrayList<>(actions.keySet());

        out.sort((o1, o2) ->
        {
            int o1d = inheritDepth.get(o1);
            int o2d = inheritDepth.get(o2);
            return o2d < o1d ? -1 : (o1d < o2d ? 1 : 0);
        });

        return out;
    }

    private static int findInheritDepth(String inherits, ArrayList<String> inheritsHistory, HashMap<String, String> definingToInheriting)
    {
        if (inheritsHistory.contains(inherits))
        {
            return -1;
        }
        else
        {
            if (!definingToInheriting.containsKey(inherits))
            {
                return 0;
            }
            else
            {
                inheritsHistory.add(inherits);
                int nextDepth = findInheritDepth(definingToInheriting.get(inherits), inheritsHistory, definingToInheriting);
                if (nextDepth == -1)
                {
                    return -1;
                }
                return nextDepth + 1;
            }
        }
    }
}
