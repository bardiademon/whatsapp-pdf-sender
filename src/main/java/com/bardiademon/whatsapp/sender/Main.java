package com.bardiademon.whatsapp.sender;

import com.bardiademon.whatsapp.sender.controller.HomeController;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        if (args != null && args.length > 0)
        {
            final File file = new File(args[0]);
            if (file.exists()) System.setProperty("user.dir" , file.getAbsolutePath());
        }

        setLookAndFeel();
        new HomeController();
    }

    private static void setLookAndFeel()
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
               InstantiationException ex)
        {
            ex.printStackTrace();
        }
    }
}