package main.game;

import main.CactiiAndCastles;

import java.net.CacheRequest;
import java.util.ArrayList;

public class Player
{
    private int health;
    private int happiness;
    private Room currentRoom;
    private Object currentObject;
    private ArrayList<Object> inventory;

    public Player()
    {
        health = 800;
        happiness = 200;
        currentRoom = null;
        currentObject = null;
        inventory = new ArrayList<>();
    }

    public Player(int health, int happiness, Room currentRoom, Object currentObject, ArrayList<Object> inventory)
    {
        this.health = health;
        this.happiness = happiness;
        this.currentRoom = currentRoom;
        this.currentObject = currentObject;
        this.inventory = inventory;
    }

    public void changeHealth(int health)
    {
        this.health += health;
    }

    public void changeHappiness(int happiness)
    {
        this.happiness += happiness;
    }

    public boolean inventoryContains(Object object)
    {
        return inventory.contains(object);
    }

    public void removeFromInventory(Object object)
    {
        inventory.remove(object);
    }

    public void addToInventory(Object object)
    {
        inventory.add(object);
    }

    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    public void setCurrentObject(Object currentObject)
    {
        this.currentObject = currentObject;
    }

    public int getHealth()
    {
        return health;
    }

    public int getHappiness()
    {
        return happiness;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public Object getCurrentObject()
    {
        return currentObject;
    }

    public ArrayList<Object> getInventory()
    {
        return inventory;
    }
}
