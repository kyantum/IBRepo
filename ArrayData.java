import javax.crypto.*;
public class ArrayData
{
    private String accountType, username, password;   
    private SealedObject encryptedPassword;

    public ArrayData(String aType, String name, String pass)
    {
        accountType = aType;
        username = name;
        password = pass;
    }
    public ArrayData(String aType, String name, SealedObject p)
    {
        accountType = aType;
        username = name;
        encryptedPassword = p;
    }
    public String getType()
    {
        return accountType;
    }
    public String getName()
    {
        return username;
    }
    public String getPass()
    {
        return password;
    }
    public String toString(SealedObject message)
    {
        return message.toString();
    }
    public SealedObject getEncryptedPassword()
    {
        return encryptedPassword;
    }
}