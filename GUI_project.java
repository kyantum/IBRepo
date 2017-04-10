import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.*;  
import java.util.*;
import java.util.prefs.Preferences;
import javax.crypto.*;
public class GUI_project extends JFrame 
{
    private JMenuBar menuBar;       
    private JButton button1;
    private JPasswordField passwordField1;
    private static JPasswordField passwordField2;
    private static SealedObject password; //THIS IS THE MASTER PASSWORD TO COMPARE AGAINST EVERYGTING
    private static char [] attemptedPass = new char[10];
    private static String enteredStringPass;
    private static int timesClicked;
    private final JFrame frame = new JFrame("Passwords");    
  
    
    
    //Constructor: Sets up home "SafeBox" blue screen
    //The driver class below will run this constructor
    //No paramaters
    public GUI_project()
    {        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter your master password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "First Time Set-up",
        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[1]);
        try
        {                
            if(option == 0) // pressing OK button
            password = RSAEncryption.encrypt(new String(pass.getPassword()));            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        JFrame frame= new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);

        
        JPanel pan = new JPanel(new BorderLayout());
        pan.setBackground(new Color(153, 183, 246));
        JLabel title = new JLabel("SafeBox");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Garamond", 0, 180));                 
        JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        button1 = new JButton();
        button1.setBounds(875,565,225,90);
        button1.setBackground(new Color(255,255,255));
        button1.setForeground(new Color(0,0,0));
        button1.setEnabled(true);
        button1.setFont(new Font("sansserif",0,40));
        button1.setText("Enter");
        button1.setVisible(true);
              
        passwordField1 = new JPasswordField();
        passwordField1.setBounds(600,565,270,90);
        passwordField1.setBackground(new Color(214,217,223));
        passwordField1.setForeground(new Color(0,0,0));
        passwordField1.setEnabled(true);
        passwordField1.setFont(new Font("sansserif",0,40));
        passwordField1.setVisible(true);
        
        //If passwords match, allow user to enter the main data screen 
        button1.addMouseListener(new MouseAdapter() 
          {
            public void mouseClicked(MouseEvent evt) 
            {                               
                attemptedPass = passwordField1.getPassword();
                enteredStringPass = new String(attemptedPass);
                try
                {
                    if(enteredStringPass.equalsIgnoreCase(RSAEncryption.decrypt(password)))                
                        passwordsMatched(evt);                                          
                    else
                        JOptionPane.showMessageDialog(null, "Wrong Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        pan.add(button1);
        pan.add(passwordField1);
        pan.add(title ,BorderLayout.CENTER);  
        //pan.add(pane);
        frame.add(pane);
        frame.add(pan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        writeDataToFrame(FileMethods.loadAccountData());
        frame.setVisible(true);
        

    }
    
    //OPENS PASSWORD MANAGER ORANGE SCREEN
    //Used by constructor, private void method
    private void passwordsMatched (MouseEvent evt)
    {
        final JPanel panel = new JPanel(null);
        
        panel.setPreferredSize(new Dimension(1800,1080));
        panel.setBackground(new Color(255,164,99)); //thisline is the color
        frame.add(panel);
        
        frame.setSize(1800,1080);        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setEnabled(true);                       
        frame.setVisible(true);
        panel.setVisible(true); 
          
        JLabel label1 = new JLabel("Account Type");
        label1.setBackground(new Color(214, 217, 223));
        frame.add(label1);
        panel.add(label1);
        label1.setBounds(275,50,200,30); //location, width, height
        label1.setFont(new Font("Sans Serif", Font.BOLD, 18));
        
        JLabel label2 = new JLabel("Username");
        frame.add(label2);
        panel.add(label2);
        label2.setBounds(738,50,200,30);
        label2.setFont(new Font("Sans Serif", Font.BOLD, 18));
        
        JLabel label3 = new JLabel("Password");
        frame.add(label3);
        panel.add(label3);
        label3.setBounds(1175, 50, 200, 30);
        label3.setFont(new Font("Sans Serif", Font.BOLD, 18));
        
        JButton button1 = new JButton();
        frame.add(button1);
        panel.add(button1);
        button1.setBounds(100,50,150,31);
        button1.setBackground(new Color(255,255,255));
        button1.setForeground(Color.BLUE);
        button1.setEnabled(true);
        button1.setFont(new Font("sansserif",0,12));
        button1.setText("Create a New Entry");
        button1.setVisible(true);
        
        JButton button2 = new JButton();
        frame.add(button2);
        panel.add(button2);
        button2.setBounds(1400,50,150,31);
        button2.setBackground(new Color(255,255,255));
        button2.setForeground(Color.BLUE);
        button2.setEnabled(true);
        button2.setFont(new Font("sansserif",0,12));
        button2.setText("Get Password");
        button2.setVisible(true);
        
        button2.addMouseListener(new MouseAdapter() 
        //GET PASSWORD CHOSEN PASSWORD 
        //RETURNS A CHOSEN PASSWORD BASED ON THE ENTERED USERNAME FROM THE ORANGE SCREEN
        {
            public void mouseClicked(MouseEvent evt)
            {
                               
                String username;
                
                JPanel panel1 = new JPanel(new FlowLayout(SwingConstants.LEADING, 10,10));               
                JLabel label1 = new JLabel("Enter the username of the password you want: ");
                JTextField data1 = new JTextField(7);    
                data1.setFont(data1.getFont().deriveFont(15f));
                panel1.add(label1);
                panel1.add(data1);
                panel1.setVisible(true);
                String[] options1 = new String[]{"Confirm", "Cancel"};
                int option1 = JOptionPane.showOptionDialog(null, panel1, "New Password", 
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options1, options1[1]);
                username = data1.getText();      
                if(option1 == 0) // pressing OK button
                {
                    //char[] masterPassword;                
                    JPanel panel2 = new JPanel();               
                    JLabel label2 = new JLabel("Enter your master password ");
                    
                    JPasswordField passwordfield4 = new JPasswordField(10);
                    passwordfield4.setFont(new Font("sansserif",0,12));
                    panel2.add(label2);
                    panel2.add(passwordfield4);
                    
                    passwordfield4.setVisible(true);
                    String[] options2 = new String[]{"Confirm", "Cancel"};
                    int option2 = JOptionPane.showOptionDialog(null, panel2, "Get Password", 
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options2, options2[1]); 
                    attemptedPass = passwordField1.getPassword();
                    enteredStringPass= new String(attemptedPass);
                    try
                    {
                    if(enteredStringPass.equalsIgnoreCase(RSAEncryption.decrypt(password)) && option2 ==0)
                    {                        
                        JPanel panel3 = new JPanel(new FlowLayout(SwingConstants.LEADING, 10,10));               
                        JLabel label3 = new JLabel(FileMethods.getPassword(username));                        
                        panel3.add(label3);
                        String[] options3 = new String[]{"Confirm", "Cancel"};
                        int option3 = JOptionPane.showOptionDialog(null, panel3, "Get Password", 
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options3, options3[1]);
                        panel3.setVisible(true);                                            
                    }
                    else
                       JOptionPane.showMessageDialog(null, "Wrong Password!", "Error", JOptionPane.ERROR_MESSAGE);     
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                    return;                
            }
        
        }); 
        
        button1.addMouseListener(new MouseAdapter() 
        //CREATE AN ACCOUNT CREATE A NEW ENTRY TO ADD TO THE ORANGE SCREEN, AND ALSO
        //SAVES DATA TO THE TEXT FILE AND ADDS TO THE ARRAYLIST
          {
            public void mouseClicked(MouseEvent evt) 
            {                               
                timesClicked++;
                
                String accountType;
                
                JPanel panel1 = new JPanel(new FlowLayout(SwingConstants.LEADING, 10,10));               
                JLabel label1 = new JLabel("Enter the type of account: ");
                JTextField data1 = new JTextField(7);    
                data1.setFont(data1.getFont().deriveFont(15f));
                panel1.add(label1);
                panel1.add(data1);
                panel1.setVisible(true);
                String[] options1 = new String[]{"Confirm", "Cancel"};
                int option1 = JOptionPane.showOptionDialog(null, panel1, "New Password", 
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options1, options1[1]);
                if(option1 == 0) // pressing OK button
                {
                    accountType = data1.getText();
                    System.out.println(accountType);                    
                    JLabel accounts = new JLabel(accountType);
                    panel.add(accounts);
                    accounts.setBounds(275, 100*timesClicked, 200,50);
                    accounts.setVisible(true);
                }
                else
                    return;
                
                
                String username;
                
                JPanel panel2 = new JPanel(new FlowLayout(SwingConstants.LEADING, 10,10));               
                JLabel label2 = new JLabel("Enter the username: ");
                JTextField data2 = new JTextField(7);    
                data2.setFont(data2.getFont().deriveFont(15f));
                panel2.add(label2);
                panel2.add(data2);
                panel2.setVisible(true);
                String[] options2 = new String[]{"Confirm", "Cancel"};
                int option2 = JOptionPane.showOptionDialog(null, panel2, "New Password", 
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options2, options2[1]);
                if(option2 == 0) // pressing OK button
                {
                    username = data2.getText();
                    System.out.println(username);
                    
                    JLabel usernames = new JLabel(username);
                    frame.add(usernames);
                    panel.add(usernames);
                    usernames.setBounds(738, 100*timesClicked, 200,50);
                    usernames.setVisible(true);
                }
                else
                    return;
                
                SealedObject createPassword;
                
                JPanel panel3 = new JPanel(new FlowLayout(SwingConstants.LEADING, 10,10));               
                JLabel label3 = new JLabel("Enter the password: ");
                JPasswordField data3 = new JPasswordField(7);    
                data3.setFont(data3.getFont().deriveFont(15f));
                panel3.add(label3);
                panel3.add(data3);
                panel3.setVisible(true);
                String[] options3 = new String[]{"Confirm", "Cancel"};
                int option3 = JOptionPane.showOptionDialog(null, panel3, "New Password", 
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options3, options3[1]);
                try
                {
                if(option3 == 0) // pressing OK button
                {
                    attemptedPass = data3.getPassword();
                    createPassword = RSAEncryption.encrypt(new String(attemptedPass));
                    String output = "";
                    for(int i = 0; i < (new String(attemptedPass)).length(); i++)
                        output += "*";
                    
                    JLabel passwords = new JLabel(output);
                    frame.add(passwords);
                    panel.add(passwords);
                    passwords.setBounds(1200, 100*timesClicked, 200,50);
                    passwords.setVisible(true);
                }
                else
                    return;                    
                    FileMethods.writeAddedEntryToFile(accountType, username, createPassword.toString());  
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        //Delete entry button
        JButton button3 = new JButton();
        frame.add(button3);
        panel.add(button3);
        button3.setBounds(1400,100,150,31);
        button3.setBackground(new Color(255,255,255));
        button3.setForeground(Color.BLUE);
        button3.setEnabled(true);
        button3.setFont(new Font("sansserif",0,12));
        button3.setText("Delete Entry");
        button3.setVisible(true);
        
        button3.addMouseListener(new MouseAdapter() 
        //DELETE AN ENTRY FROM THE ORANGE SCREEN, FROM THE ARRAYLIST, AND ALSO THE TEXTFILE
        {
            public void mouseClicked(MouseEvent evt)
            {
                               
                String username;
                
                JPanel panel1 = new JPanel(new FlowLayout(SwingConstants.LEADING, 10,10));               
                JLabel label1 = new JLabel("Enter the username of the entry to be deleted: ");
                JTextField data1 = new JTextField(7);    
                data1.setFont(data1.getFont().deriveFont(15f));
                panel1.add(label1);
                panel1.add(data1);
                panel1.setVisible(true);
                String[] options1 = new String[]{"Confirm", "Cancel"};
                int option1 = JOptionPane.showOptionDialog(null, panel1, "New Password", 
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options1, options1[1]);
                username = data1.getText();      
                if(option1 == 0) // pressing OK button
                {
                    String masterPassword;                
                    JPanel panel2 = new JPanel(new FlowLayout(SwingConstants.LEADING, 10,10));               
                    JLabel label2 = new JLabel("Enter your master password: ");
                    JPasswordField data2 = new JPasswordField(7);    
                    data2.setFont(data2.getFont().deriveFont(15f));
                    panel2.add(label2);
                    panel2.add(data2);
                    panel2.setVisible(true);
                    String[] options2 = new String[]{"Confirm", "Cancel"};
                    int option2 = JOptionPane.showOptionDialog(null, panel2, "Get Password", 
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options2, options2[1]);                    
                    
                    attemptedPass= data2.getPassword();     
                    enteredStringPass  = new String(attemptedPass);
                    try
                    {
                        if(option2 ==0 && enteredStringPass.equalsIgnoreCase(RSAEncryption.decrypt(password)))           
                        {                        
                            FileMethods.deleteEntry(username); 
                            timesClicked = 0;  
                            writeDataToFrame(FileMethods.loadAccountData());
                            JOptionPane.showMessageDialog(null, 
                            "Remeber to exit the program to update the screen!", "Reminder", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Wrong Password!", "Error", JOptionPane.ERROR_MESSAGE);       
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                    return;   
            }
        
        }); 
    }                    
    private void writeEntryToFrame(String type, String name, String pass)
    //right after newentry
    //WRITE JUST CREATED ENTRY TO ACCOUNT
    {                    
    
           JLabel accounts = new JLabel(type);
           accounts.setFont(new Font("Sans Serif", 0,50));
           frame.add(accounts);
            accounts.setVisible(true);
            accounts.setBounds(275, 100*timesClicked, 200,30);


          JLabel usernames = new JLabel(name);
            usernames.setFont(new Font("Sans Serif", 0, 50));
            frame.add(usernames);
            usernames.setVisible(true);
            usernames.setBounds(738,100*timesClicked,200,30);        
                    

            JLabel passwords = new JLabel(pass);
            passwords.setFont(new Font("Sans Serif", 0, 50));
            frame.add(passwords);
            passwords.setVisible(true);
            passwords.setBounds(1200, 100*timesClicked, 200,30);
           
            
            timesClicked++;
        
        
    }
    private void writeDataToFrame(ArrayList <ArrayData>data)//loads old data from the arraylist
    {
        timesClicked++;
        String accountType, username, password;
        for(int i = 0; i < data.size(); i++)
        {               
            accountType = data.get(i).getType();                    
            JLabel accounts = new JLabel(accountType);
            frame.add(accounts);
            accounts.setVisible(true);
            accounts.setBounds(275, 100*timesClicked, 200,30);

            username = data.get(i).getName();
            JLabel usernames = new JLabel(username);
            frame.add(usernames);
            usernames.setVisible(true);
            usernames.setBounds(738,100*timesClicked,200,30);        
                    
            password = (data.get(i).getPass()).toString();
            JLabel passwords = new JLabel(password);
            frame.add(passwords);
            passwords.setVisible(true);
            passwords.setBounds(1200, 100*timesClicked, 200,30);
            
            timesClicked++;
        }
    }
     //DRIVER RUNS CONSTRUCTOR, WHICH BUTTONS ARE BASED OFF OF, AND SO ARE ALL OTHER FUNCTIONS
    public static void main(String[] args)
     {
        System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        javax.swing.SwingUtilities.invokeLater(new Runnable()         
        {
            public void run() 
            {
                new GUI_project();
            }
        });
    }
}


