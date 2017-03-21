package main;

import main.game.Castle;
import main.game.Object;
import main.game.Player;
import main.game.util.*;
import main.gui.GameApp;
import main.reader.ActionReader;
import main.reader.InputReader;
import main.reader.MapReader;

import javax.swing.*;
import java.util.ArrayList;

public class CactiiAndCastles
{
    private static final String mapPath = "C:\\Projects\\CactiiAndCastles\\map\\default.castle";

    public static Castle castle;
    private static Player player;
    private static GameApp app;

    public static void main(String[] args)
    {
        castle = MapReader.getCastle(mapPath);
        player = new Player(800, 200, castle.getRooms().get(0), null, new ArrayList<>());
        SwingUtilities.invokeLater(() -> {app = new GameApp();app.println(castle.getWelcome());app.println("");});
    }

    public static void handleInput(String s)
    {
        println(">>> " + s);
        Command command = InputReader.parseInput(s, player);

        if (command != null)
        {
            switch (command.getType())
            {
                case SHOW_OBJECTS:
                {
                    String pr = "You look around the room. You see the following objects: " + String.join(", ", player.getCurrentRoom().getObjects().stream().filter(Object::isVisible).map(Object::getName).toArray(String[]::new));
                    println(pr);
                    break;
                }
                case STATISTICS:
                {
                    String pr = String.format("You sit back and think for a while. You have %d health, and %d happiness. You are currently in %s%s. %s", player.getHealth(), player.getHappiness(), player.getCurrentRoom().getName(), player.getCurrentObject() != null ? ", at the " + player.getCurrentObject().getName() : "", player.getInventory().size() > 0 ? "Your inventory contains the following objects: " + String.join(", ", player.getInventory().stream().map(Object::getName).toArray(String[]::new)) : " You aren't carrying any objects");
                    println(pr);
                    break;
                }
                case USER_DEFINED:
                    command.getAction().getString().say(command.getObject(), command.getAuxiliary());
                    player.changeHappiness(command.getAction().getHappiness());
                    player.changeHealth(command.getAction().getHealth());
                    if (!command.getAction().getCommand().equals(""))
                    {
                        ActionReader.executeAction(command, player);
                    }
                    break;
            }
        }
    }

    public static void println(String s)
    {
        app.println(s);
    }
}
