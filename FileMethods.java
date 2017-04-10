import java.io.*;
import java.util.*;
import javax.crypto.*;
//File loc: users, me, drive, senior, compsci, progs
public class FileMethods
{
    private static ArrayList <ArrayData> list = new ArrayList <ArrayData>();
    //This method reads data from the textfile
    //This method will take said data and read it into the ArrayList
    public static ArrayList loadAccountData()
    {        
        try
        {
                Scanner scan = new Scanner(new File("Passwords.txt"));
                while(scan.hasNextLine())
                {                 
                    String accountType = scan.next();
                    scan.nextLine();
                    String username = scan.nextLine();
                    ObjectInputStream s= new ObjectInputStream(new FileInputStream("Passwords.txt"));
                    SealedObject password = (SealedObject) s.readObject();
                    ArrayData object= new ArrayData(accountType, username, password);
                    list.add(object);
                }                
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }            
        return list;
    }
    //This method takes the paramaters of an ArrayData object
    //This method writes the object to the textfile
    public static void writeAddedEntryToFile(String type, String user, String pass)
    {
        try
        {        
            File file = new File("Passwords.txt");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(type);
            bw.newLine();
            bw.write(user);
            bw.newLine();
            bw.write(pass);
            bw.newLine();
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    //This method takes a username, and returns their password from
    //The arraylist
    public static String getPassword(String username)
    {
        String password;
        try
        {
            Scanner scan = new Scanner(new File ("Passwords.txt"));
            for(int i = 0; i < list.size(); i++)
            {
                if(list.get(i).getName().equalsIgnoreCase(username))
                    return list.get(i).getPass();
                else
                    continue;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }        
        return "failed";
    }
    //This method deletes all instances of a specific username and account and password
    //Since you cannot delete a single line from a file, I clear it and rewrite a new file
    //without that entry
    //Then, deletes from the arraylist
    public static boolean deleteEntry(String username)
    {
        try
        {
            PrintWriter writer = new PrintWriter("Passwords.txt");
            writer.print("");
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getName().equalsIgnoreCase(username))
            {
                list.remove(i);
                continue;
            }
            else
            { 
                writeAddedEntryToFile(list.get(i).getType(), list.get(i).getName(), list.get(i).getPass());
                continue;
            }
        }
        return false;
    }
}
