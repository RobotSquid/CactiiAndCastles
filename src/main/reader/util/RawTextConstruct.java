package main.reader.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RawTextConstruct
{
    public ArrayList<String> raw;

    private HashMap<String, String> values;
    private ArrayList<RawTextConstruct> constructs;

    public RawTextConstruct(ArrayList<String> raw)
    {
        this.raw = raw;

        this.constructs = new ArrayList<>();
        this.values = getValues(raw);

        getSubObjects(raw).forEach(a -> constructs.add(new RawTextConstruct(a)));
    }

    public RawTextConstruct(String path)
    {
        this(getData(path));
    }

    private static ArrayList<String> getData(String path)
    {
        ArrayList<String> data = new ArrayList<>();
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

            return data;
        }
        catch (Exception e)
        {
            System.out.println("Couldn't load construct, " + e.getMessage());
        }
        return null;
    }

    public HashMap<String, String> getValues()
    {
        return values;
    }

    public ArrayList<RawTextConstruct> getConstructs()
    {
        return constructs;
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
}
