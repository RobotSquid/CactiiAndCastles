package main.helper;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayHelper
{
    public static boolean arrayContains(ArrayList a, Predicate p)
    {
        boolean done = false;
        for (int i = 0; i < a.size() && !done; i++)
        {
            done = p.test(a.get(i));
        }
        return done;
    }

    public static Object getFirstSatisfy(ArrayList a, Predicate p)
    {
        for (int i = 0; i < a.size(); i++)
        {
            if (p.test(a.get(i)))
            {
                return a.get(i);
            }
        }
        return null;
    }
}
