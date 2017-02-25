package main.game.util;

import com.sun.deploy.util.ArrayUtil;
import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import main.game.Object;
import main.game.Player;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Condition
{
    private String literal;

    public Condition(String l)
    {
        literal = l.replaceAll("(^\\s*)|(\\s*$)", "").replaceAll("\\s*([\\.\\[\\]\\(\\)\\{\\}=!<>&/])\\s*", "$1");

        int startIndex = literal.indexOf('('), endIndex = startIndex + 1, parenCount;
        for (parenCount = 1; parenCount != 0 && endIndex < literal.length(); endIndex++) {parenCount += literal.charAt(endIndex)=='(' ? 1 : (literal.charAt(endIndex)==')' ? -1 : 0);}

        if (parenCount != 0)
        {
            literal = literal.replaceAll("[\\(\\)]", "");
        }
    }

    public boolean evaluate(Player player, Object object, Object auxiliary)
    {
        if (literal.equals("default"))
        {
            return true;
        }
        else
        {
            //String evalLit = literal.replace("{object}", object).replace("{auxiliary}", subject);
            Matcher m = Pattern.compile("\\{[\\w\\.]+\\}").matcher(literal);
            String evalLit = literal;

            //System.out.println(literal);

            while (m.find())
            {
                //System.out.println("------" + m.group());
                evalLit = evalLit.replace(m.group(), replacement(m.group(), player, object, auxiliary));
            }

            //System.out.println(evalLit);

            m = Pattern.compile("[^\\(\\)/&]*!*[=<>][^\\(\\)/&]*").matcher(evalLit);
            String sLit = evalLit;
            String[] args;

            while (m.find())
            {

                String process = m.group();
                args = process.split("(!*[=<>])");
                String operator = process.split("[^!=<>]+")[1];
                try
                {
                    int first = Integer.valueOf(args[0]), second = Integer.valueOf(args[1]);
                    switch (operator)
                    {
                        case "=":
                            sLit = sLit.replace(m.group(), String.valueOf(first == second));
                            break;
                        case "!=":
                            sLit = sLit.replace(m.group(), String.valueOf(first != second));
                            break;
                        case "<":
                            sLit = sLit.replace(m.group(), String.valueOf(first < second));
                            break;
                        case ">":
                            sLit = sLit.replace(m.group(), String.valueOf(first > second));
                            break;
                        case "!<":
                            sLit = sLit.replace(m.group(), String.valueOf(first >= second));
                            break;
                        case "!>":
                            sLit = sLit.replace(m.group(), String.valueOf(first <= second));
                            break;
                    }
                }
                catch (NumberFormatException e)
                {
                    switch (operator)
                    {
                        case "=":
                            sLit = sLit.replace(m.group(), String.valueOf(args[0].equals(args[1])));
                            break;
                        case "!=":
                            sLit = sLit.replace(m.group(), String.valueOf(!args[0].equals(args[1])));
                            break;
                        default:
                            sLit = sLit.replace(m.group(), "false");
                            break;
                    }
                }

                //sLit = sLit.replace(m.group(), String.valueOf(args[0].equals(args[1])));
            }

            //System.out.println(sLit);
            String simple = simplifyString(sLit);

            return Boolean.parseBoolean(simple);
        }
    }

    private String simplifyString(String s)
    {
        //System.out.println("--ENTERING: " + s + "--");
        while (s.matches(".*[\\(\\)].*")/*s.split("\\s*[/&]\\s*").length != 2*/)
        {
            int startIndex = s.indexOf('('), endIndex = startIndex + 1, parenCount;
            for (parenCount = 1; parenCount != 0 && endIndex < s.length(); endIndex++)
            {
                parenCount += s.charAt(endIndex)=='(' ? 1 : (s.charAt(endIndex)==')' ? -1 : 0);
            }
            if (parenCount == 0)
            {
                s = s.replace(s.substring(startIndex, endIndex), simplifyString(s.substring(startIndex + 1, endIndex - 1)));
                //System.out.println(s);
            }
        }

        String[] arr = s.split("[/&]");
        String[] jn = s.split("[^/&]+");

        //System.out.println(Arrays.toString(arr) + " joined by " + Arrays.toString(jn));

        boolean run = arr[0].equals("true");
        for (int bl = 1; bl < jn.length; bl++)
        {
            run = jn[bl].equals("&") ? (run && arr[bl].equals("true")) : (run || arr[bl].equals("true"));
        }

        String b = run ? "true" : "false"/*s.contains("/") ? (arr[0].equals("true")||arr[1].equals("true")?"true":"false") : (arr[0].equals("true")&&arr[1].equals("true")?"true":"false")*/;
        //System.out.println("--EXITING: " + b + "--");
        return b;
    }

    private String replacement(String group, Player player, Object object, Object auxiliary)
    {
        //System.out.println("======" + group);
        String[] params = group.split("\\.", 2);
        Object thing = params[0].equalsIgnoreCase("object") ? object : (params[0].equalsIgnoreCase("auxiliary") ? auxiliary : null);

        if (thing != null)
        {
            if (params.length == 1)
            {
                return thing.getName();
            }
            else if (params.length == 2)
            {
                if (thing.getData().containsKey(params[1]))
                {
                    return thing.getData().get(params[1]);
                }
                else if (params[1].equals("context"))
                {
                    return thing.isExistsInWorld() ? "castle" : "player";
                }
                else
                {
                    //System.out.println("Error Interpreting: " + group);
                }
            }
        }
        else if (params[0].equalsIgnoreCase("player"))
        {
            switch (params[1])
            {
                case "health":
                    return String.valueOf(player.getHealth());
                case "happiness":
                    return String.valueOf(player.getHappiness());
                case "room":
                    return player.getCurrentRoom().getName();
                case "object":
                    return player.getCurrentObject() != null ? player.getCurrentObject().getName() : "null";
            }
        }

        return "null";
    }

    public String getLiteral()
    {
        return literal;
    }
}
