import java.security.*;
import javax.crypto.*;
public class RSAEncryption 
{
    private static final KeyPairGenerator kpg = key();
    private static final KeyPair myKey = kpg.generateKeyPair();            
    private static final Cipher cipher =ciph();
    public static SealedObject encrypt (String str)
    {
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, myKey.getPublic());        
            SealedObject encrypted= new SealedObject(str, cipher);
            return encrypted;            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static String decrypt (SealedObject encrypted)
    {
        try
        {
            Cipher decrypt = Cipher.getInstance("RSA");         
            decrypt.init(Cipher.DECRYPT_MODE, myKey.getPrivate());
            String message = (String) encrypted.getObject(decrypt);
            return message;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    private static KeyPairGenerator key()
    {
        try
        {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            return kpg;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    private static Cipher ciph()
    {
        try
        {
            Cipher cipher = Cipher.getInstance("RSA");
            return cipher;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}