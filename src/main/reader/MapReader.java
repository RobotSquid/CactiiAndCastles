package main.reader;

import main.game.Castle;
import main.reader.util.RawTextConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MapReader
{
    public static Castle getCastle(String path)
    {
        //ALL CONSTRUCTS INITIALIZE
        RawTextConstruct castle = new RawTextConstruct(path);
        ArrayList<RawTextConstruct> rooms = castle.getConstructs();
        ArrayList<RawTextConstruct> objects = processSortedList(allSubObjects(rooms));
        ArrayList<RawTextConstruct> actions = processSortedList(allSubObjects(objects));

        //CASTLE DATA
        Castle realCastle = new Castle();

        for (String key : castle.getValues().keySet())
        {
            switch (key)
            {
                case "welcome":
                    realCastle.setWelcome(castle.getValues().get(key));
                    break;
                default:
                    System.out.println("No field found in Castle: " + key);
                    break;
            }
        }

        //TODO actions parse

        //TODO objects parse


        /*
        ArrayList<Room> realRooms = new ArrayList<>();

        for (RawTextConstruct room : rooms)
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

            for (RawTextConstruct object : objects)
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

                for (RawTextConstruct action : actions)
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
                    RawTextConstruct action = actions.get(actionN);
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

                for (RawTextConstruct action : actions)
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

                    for (RawTextConstruct acase : cases)
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


            realRooms.add(realRoom);
        }

        realCastle.setRooms(realRooms);*/

        return realCastle;
    }

    private static ArrayList<RawTextConstruct> allSubObjects(ArrayList<RawTextConstruct> parents)
    {
        final ArrayList<RawTextConstruct> children = new ArrayList<>();
        parents.stream().forEach(o -> children.addAll(o.getConstructs()));
        return children;
    }

    private static ArrayList<RawTextConstruct> processSortedList(ArrayList<RawTextConstruct> sort)
    {
        //DEFINED
        HashMap<String, RawTextConstruct> constructDefined = new HashMap<>();
        sort.stream().filter(o -> o.getValues().containsKey("defined")).forEach(o -> constructDefined.put(o.getValues().get("defined"), o));
        //EXTENDING
        HashMap<RawTextConstruct, RawTextConstruct> constructExtends = new HashMap<>();
        sort.stream().filter(a -> a.getValues().containsKey("extends") && constructDefined.containsKey(a.getValues().get("extends"))).forEach(a -> constructExtends.put(a, constructDefined.get(a.getValues().get("extends"))));
        //DEPTH
        HashMap<RawTextConstruct, Integer> constructInheritDepth = new HashMap<>();
        sort.stream().forEach(r -> constructInheritDepth.put(r, findInheritDepth(r, new ArrayList<>(), constructExtends)));
        //SORT
        sort.sort((o1, o2) -> findProcessPosition(o1, o2, constructInheritDepth));

        return sort;
    }

    private static Integer findProcessPosition(RawTextConstruct o1, RawTextConstruct o2, HashMap<RawTextConstruct, Integer> inheritDepth)
    {
        int o1d = inheritDepth.get(o1);
        int o2d = inheritDepth.get(o2);
        return o2d < o1d ? -1 : (o1d < o2d ? 1 : 0);
    }

    private static int findInheritDepth(RawTextConstruct inherits, ArrayList<RawTextConstruct> inheritsHistory, HashMap<RawTextConstruct, RawTextConstruct> eragon)
    {
        if (inheritsHistory.contains(inherits))
        {
            return -1;
        }
        else
        {
            if (!eragon.containsKey(inherits))
            {
                return 0;
            }
            else
            {
                inheritsHistory.add(inherits);
                int nextDepth = findInheritDepth(eragon.get(inherits), inheritsHistory, eragon);
                if (nextDepth == -1)
                {
                    return -1;
                }
                return nextDepth + 1;
            }
        }
    }
}
