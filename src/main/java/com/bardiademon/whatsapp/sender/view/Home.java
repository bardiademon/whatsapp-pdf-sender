package com.bardiademon.whatsapp.sender.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

public abstract class Home extends View
{

    protected JButton btnClearLog;
    protected JButton btnConnect;
    protected JButton btnImportNumber;
    protected JButton btnPdfChooser;
    protected JButton btnSendPdf;
    protected JLabel lblNumberOfPhones;
    protected JLabel lblPathPdf;
    protected JLabel lblValStatus;
    protected JList<String> lstLog;

    private void initComponents()
    {

        btnConnect = new JButton();
        JLabel lblStatus = new JLabel();
        lblValStatus = new JLabel();
        lblPathPdf = new JLabel();
        btnPdfChooser = new JButton();
        javax.swing.JScrollPane scrollLstLog = new javax.swing.JScrollPane();
        lstLog = new javax.swing.JList<>();
        btnImportNumber = new JButton();
        lblNumberOfPhones = new JLabel();
        btnSendPdf = new JButton();
        btnClearLog = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnConnect.setText("Connect");

        lblStatus.setText("Status");

        btnPdfChooser.setText("Pdf chooser");

        scrollLstLog.setViewportView(lstLog);

        btnImportNumber.setText("Import number");

        btnSendPdf.setText("Send pdf");

        btnClearLog.setText("Clear Log");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false).addComponent(btnPdfChooser , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(btnImportNumber , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblNumberOfPhones , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(lblPathPdf , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))).addComponent(btnSendPdf , javax.swing.GroupLayout.PREFERRED_SIZE , 376 , javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(layout.createSequentialGroup().addComponent(lblStatus).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(lblValStatus , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false).addComponent(btnConnect , javax.swing.GroupLayout.DEFAULT_SIZE , 182 , Short.MAX_VALUE).addComponent(btnClearLog , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)).addComponent(scrollLstLog , javax.swing.GroupLayout.PREFERRED_SIZE , 182 , javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false).addComponent(lblValStatus , javax.swing.GroupLayout.Alignment.TRAILING , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(lblStatus , javax.swing.GroupLayout.Alignment.TRAILING , javax.swing.GroupLayout.DEFAULT_SIZE , 25 , Short.MAX_VALUE)).addComponent(btnConnect , javax.swing.GroupLayout.PREFERRED_SIZE , 36 , javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(btnPdfChooser).addComponent(lblPathPdf , javax.swing.GroupLayout.PREFERRED_SIZE , 24 , javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18 , 18 , 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnImportNumber).addComponent(lblNumberOfPhones , javax.swing.GroupLayout.PREFERRED_SIZE , 25 , javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(btnSendPdf , javax.swing.GroupLayout.PREFERRED_SIZE , 36 , javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup().addGap(1 , 1 , 1).addComponent(scrollLstLog).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnClearLog , javax.swing.GroupLayout.PREFERRED_SIZE , 36 , javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));

        pack();
    }

    @Override
    protected void setView(String title)
    {
        initComponents();
        super.setView(title);
    }
}
