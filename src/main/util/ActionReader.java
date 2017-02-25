package main.util;

import main.game.Object;
import main.game.Player;
import main.game.Room;
import main.game.util.Command;

import static main.CactiiAndCastles.castle;

public class ActionReader
{
    public static void executeAction(Command details, Player player)
    {
        String[] actions = details.getAction().getCommand().split(";");
        String cmd;
        String[] args;
        for (String s : actions)
        {
            cmd = s.substring(0, 3);
            args = s.substring(4).split(",");
            switch (cmd)
            {
                case "sdp":
                    if (args.length == 3)
                    {
                        Object object = InputReader.objectNames.get(objectName(args[0], details.getObject(), details.getAuxiliary()));
                        if (object != null)
                        {
                            object.getData().put(args[1], args[2]);
                            //System.out.println(castle.getRooms().get(0).getObjects().get(0).getData().get("type") + " " + InputReader.objectNames.get("Useless Sword").getData().get("type"));
                            //InputReader.objectNames.put(objectName(args[0], details.getObject(), details.getAuxiliary()), object);
                        }
                        else
                        {
                            System.out.println("Incorrect object");
                        }
                    }
                    else
                    {
                        System.out.println("Not enough parameters");
                    }
                    break;
                case "mtr":
                    if (args.length == 1)
                    {
                        Room room = InputReader.roomNames.get(args[0]);
                        if (room != null)
                        {
                            player.setCurrentRoom(room);
                        }
                    }
                    else
                    {
                        System.out.println("Not enough parameters");
                    }
                    break;
                case "dlo":
                    if (args.length == 1)
                    {
                        Object object = InputReader.objectNames.get(objectName(args[0], details.getObject(), details.getAuxiliary()));
                        if (object != null)
                        {
                            if (player.inventoryContains(object))
                            {
                                player.removeFromInventory(object);
                            }
                            else if (player.getCurrentRoom().getObjects().contains(object))
                            {
                                player.getCurrentRoom().getObjects().remove(object);
                                if (player.getCurrentObject() == object)
                                {
                                    player.setCurrentObject(null);
                                }
                            }
                            else
                            {
                                System.out.println("Object not found");
                            }
                        }
                        else
                        {
                            System.out.println("Object not found");
                        }
                    }
                    else
                    {
                        System.out.println("Not enough parameters");
                    }
                    break;
                case "ati":
                    if (args.length == 1)
                    {
                        Object object = InputReader.objectNames.get(objectName(args[0], details.getObject(), details.getAuxiliary()));
                        if (object != null)
                        {
                            if (player.getCurrentRoom().getObjects().contains(object))
                            {
                                player.addToInventory(object);
                                object.setExistsInWorld(false);
                                //InputReader.objectNames.put(objectName(args[0], details.getObject(), details.getAuxiliary()), object);
                                player.getCurrentRoom().getObjects().remove(object);
                                if (player.getCurrentObject() == object)
                                {
                                    player.setCurrentObject(null);
                                }
                            }
                            else
                            {
                                System.out.println("Object not found");
                            }
                        }
                        else
                        {
                            System.out.println("Object not found");
                        }
                    }
                    else
                    {
                        System.out.println("Not enough parameters");
                    }
                    break;
                case "rfi":
                    if (args.length == 1)
                    {
                        Object object = InputReader.objectNames.get(objectName(args[0], details.getObject(), details.getAuxiliary()));
                        if (object != null)
                        {
                            if (player.inventoryContains(object))
                            {
                                object.setExistsInWorld(true);
                                //InputReader.objectNames.put(objectName(args[0], details.getObject(), details.getAuxiliary()), object);
                                player.getCurrentRoom().getObjects().add(object);
                                player.removeFromInventory(object);
                                player.setCurrentObject(object);
                            }
                            else
                            {
                                System.out.println("Object not found");
                            }
                        }
                        else
                        {
                            System.out.println("Object not found");
                        }
                    }
                    else
                    {
                        System.out.println("Not enough parameters");
                    }
                    break;
                case "mto":
                    if (args.length == 1)
                    {
                        Object object = InputReader.objectNames.get(objectName(args[0], details.getObject(), details.getAuxiliary()));
                        if (object != null)
                        {
                            if (player.getCurrentRoom().getObjects().contains(object))
                            {
                                player.setCurrentObject(object);
                            }
                            else
                            {
                                System.out.println("Object not found");
                            }
                        }
                        else
                        {
                            System.out.println("Object not found");
                        }
                    }
                    else
                    {
                        System.out.println("Not enough parameters");
                    }
                    break;
            }
        }
    }

    private static String objectName(String s, Object o, Object a)
    {
        return s.replace("{object}", o == null ? "null" : o.getName()).replace("{auxiliary}", a == null ? "null" : a.getName());
    }
}
