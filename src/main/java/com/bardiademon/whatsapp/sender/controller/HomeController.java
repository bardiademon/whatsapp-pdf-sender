package com.bardiademon.whatsapp.sender.controller;

import com.bardiademon.whatsapp.sender.view.Home;

import javax.swing.SwingUtilities;

public class HomeController extends Home
{
    public HomeController()
    {
        SwingUtilities.invokeLater(() -> setView("Whatsapp pdf sender - bardiademon"));
    }
}
