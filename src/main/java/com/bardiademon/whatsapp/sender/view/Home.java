package com.bardiademon.whatsapp.sender.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Font;

public abstract class Home extends View
{
    protected final DefaultListModel<String> lstLogModel = new DefaultListModel<>();

    protected JButton btnClearLog;
    protected JButton btnConnect;
    protected JButton btnImportNumber;
    protected JButton btnPdfChooser;
    protected JButton btnSendPdf;
    protected JLabel lblNumberOfPhones;
    protected JLabel lblPathPdf;
    protected JLabel lblValStatus;
    protected JList<String> lstLog;

    protected JTextArea txtHiMessage;

    private void initComponents()
    {

        btnConnect = new javax.swing.JButton();
        javax.swing.JLabel lblStatus = new javax.swing.JLabel();
        lblValStatus = new javax.swing.JLabel();
        lblPathPdf = new javax.swing.JLabel();
        btnPdfChooser = new javax.swing.JButton();
        javax.swing.JScrollPane scrollLstLog = new javax.swing.JScrollPane();
        lstLog = new javax.swing.JList<>();
        btnImportNumber = new javax.swing.JButton();
        lblNumberOfPhones = new javax.swing.JLabel();
        btnSendPdf = new javax.swing.JButton();
        btnClearLog = new javax.swing.JButton();
        javax.swing.JLabel lblHiMessage = new javax.swing.JLabel();
        javax.swing.JScrollPane srollTxtHiMessage = new javax.swing.JScrollPane();
        txtHiMessage = new JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnConnect.setText("Connect");

        lblStatus.setText("Status");

        btnPdfChooser.setText("Pdf chooser");

        scrollLstLog.setViewportView(lstLog);

        btnImportNumber.setText("Import number");

        btnSendPdf.setText("Send pdf");

        btnClearLog.setText("Clear Log");

        lblHiMessage.setText("Hi message:");

        txtHiMessage.setColumns(20);
        txtHiMessage.setRows(5);
        srollTxtHiMessage.setViewportView(txtHiMessage);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false)
                                                        .addComponent(btnPdfChooser , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                                        .addComponent(btnImportNumber , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNumberOfPhones , javax.swing.GroupLayout.DEFAULT_SIZE , 253 , Short.MAX_VALUE)
                                                        .addComponent(lblPathPdf , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblStatus)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblValStatus , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnSendPdf , javax.swing.GroupLayout.PREFERRED_SIZE , 376 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblHiMessage)
                                                        .addComponent(srollTxtHiMessage , javax.swing.GroupLayout.PREFERRED_SIZE , 376 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0 , 1 , Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false)
                                                .addComponent(btnConnect , javax.swing.GroupLayout.DEFAULT_SIZE , 182 , Short.MAX_VALUE)
                                                .addComponent(btnClearLog , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                        .addComponent(scrollLstLog , javax.swing.GroupLayout.PREFERRED_SIZE , 182 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(13 , Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblStatus , javax.swing.GroupLayout.PREFERRED_SIZE , 25 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0 , 0 , Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnConnect , javax.swing.GroupLayout.PREFERRED_SIZE , 36 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblValStatus , javax.swing.GroupLayout.PREFERRED_SIZE , 25 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(btnPdfChooser)
                                                                        .addComponent(lblPathPdf , javax.swing.GroupLayout.PREFERRED_SIZE , 24 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18 , 18 , 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(btnImportNumber)
                                                                        .addComponent(lblNumberOfPhones , javax.swing.GroupLayout.PREFERRED_SIZE , 25 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblHiMessage)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(srollTxtHiMessage , javax.swing.GroupLayout.DEFAULT_SIZE , 106 , Short.MAX_VALUE)
                                                                .addGap(18 , 18 , 18)
                                                                .addComponent(btnSendPdf , javax.swing.GroupLayout.PREFERRED_SIZE , 36 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup()
                                                                .addGap(1 , 1 , 1)
                                                                .addComponent(scrollLstLog)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnClearLog , javax.swing.GroupLayout.PREFERRED_SIZE , 36 , javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap())
        );

        pack();
    }

    @Override
    protected void setView(String title)
    {
        initComponents();
        SwingUtilities.invokeLater(() ->
        {
            final Font txtHiMessageFont = txtHiMessage.getFont();
            txtHiMessage.setFont(new Font(txtHiMessageFont.getName() , txtHiMessageFont.getStyle() , 13));
        });


        lstLog.setModel(lstLogModel);
        super.setView(title);
        setOnListener();
    }

    private void setOnListener()
    {
        btnConnect.addActionListener(e -> onClickBtnConnect());
        btnClearLog.addActionListener(e -> onClickBtnClearLog());
        btnImportNumber.addActionListener(e -> onClickBtnImportNumber());
        btnSendPdf.addActionListener(e -> onClickBtnSendPdf());
        btnPdfChooser.addActionListener(e -> onClickBtnPdfChooser());
        txtHiMessage.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                onChangeTxtHiMessage();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                onChangeTxtHiMessage();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                onChangeTxtHiMessage();
            }
        });
    }

    protected abstract void onChangeTxtHiMessage();

    protected abstract void onClickBtnPdfChooser();

    protected abstract void onClickBtnSendPdf();

    protected abstract void onClickBtnImportNumber();

    protected abstract void onClickBtnClearLog();

    protected abstract void onClickBtnConnect();
}
