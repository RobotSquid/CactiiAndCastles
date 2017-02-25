package main.game;

import java.util.ArrayList;

public class Castle
{

    private String welcome = "";
    private ArrayList<Room> rooms = new ArrayList<Room>();
    //private ArrayList connections = new ArrayList<Connection>();

    public Castle() {}

    public ArrayList<Room> getRooms()
    {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms)
    {
        this.rooms = rooms;
    }

    public String getWelcome()
    {
        return welcome;
    }

    public void setWelcome(String welcome)
    {
        this.welcome = welcome;
    }
}
