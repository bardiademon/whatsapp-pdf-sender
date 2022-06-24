package com.bardiademon.whatsapp.sender.controller;

import com.bardiademon.whatsapp.sender.controller.connector.Connector;
import com.bardiademon.whatsapp.sender.controller.connector.ConnectorStatus;
import com.bardiademon.whatsapp.sender.io.Path;
import com.bardiademon.whatsapp.sender.model.Message;
import com.bardiademon.whatsapp.sender.model.Message.Media;
import com.bardiademon.whatsapp.sender.model.PhoneNumberImport;
import com.bardiademon.whatsapp.sender.view.Home;
import it.auties.whatsapp.model.message.standard.DocumentMessage;
import it.auties.whatsapp.model.message.standard.TextMessage;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeController extends Home implements ConnectorStatus
{
    private final DateTimeFormatter logTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd");

    private final Connector connector = new Connector();

    private QrCodeViewerController qrCodeViewerController;

    private List<PhoneNumberImport> phoneNumberImports;
    private Timer timerSendListMessage;
    private boolean timerSendListMessageIsRunning;
    private boolean cancelImportNumber;
    private boolean sendingListMessage = false;
    private static final int MAX_MIN_WAIT_SEND_LIST_MESSAGE = 1;

    private final Message message = new Message();
    private boolean sendingMessage = false;

    public HomeController()
    {
        SwingUtilities.invokeLater(() ->
        {
            setView("Whatsapp pdf sender - bardiademon");

            final String[] pathPdf = getPathPdf();
            if (pathPdf != null)
            {
                importPdf(new File(pathPdf[0]));
                txtHiMessage.setText(pathPdf[1]);
                importPhones(new File(pathPdf[2]));
            }
        });
    }

    @Override
    protected void onChangeTxtHiMessage()
    {
        SwingUtilities.invokeLater(() -> message.setText(txtHiMessage.getText()));
    }

    @Override
    protected void onClickBtnPdfChooser()
    {
        SwingUtilities.invokeLater(() ->
        {
            final String[] pathPdf = getPathPdf();
            final JFileChooser chooser = new JFileChooser(pathPdf == null ? null : pathPdf[0]);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            final int dialogResult = chooser.showOpenDialog(null);
            if (dialogResult == JFileChooser.OPEN_DIALOG)
            {
                final File selectedFile = chooser.getSelectedFile();
                importPdf(selectedFile);
            }
            else setStatus("Error Load pdf files");
        });
    }

    private void importPdf(final File selectedFile)
    {
        new Thread(() ->
        {
            if (selectedFile != null && selectedFile.exists())
            {
                final File[] files = selectedFile.listFiles();
                if (files != null)
                {
                    final List<Media> media = new ArrayList<>();
                    for (final File file : files)
                    {
                        final String extension = FilenameUtils.getExtension(file.getName());
                        System.out.println(extension);
                        if (file.isFile() && !file.isDirectory() && extension.toLowerCase(Locale.ROOT).equals("pdf"))
                        {
                            final Media pdf = new Media();
                            pdf.setTest("Powered by bardiademon");
                            pdf.setPath(file.getAbsolutePath());
                            media.add(pdf);
                        }
                    }
                    setStatus("Pdf found: " + media.size());
                    SwingUtilities.invokeLater(() -> lblPathPdf.setText(selectedFile.getAbsolutePath()));
                    setPathPdf(selectedFile , null , null);
                    message.setPdf(media);
                }
                else setStatus("Error Load pdf files");
            }
        }).start();
    }

    private void setPathPdf(final File file , final File pathPhones , final String hiMessage)
    {
        new Thread(() ->
        {
            final File pathPdf = new File(Path.PATH_PDF);

            try
            {
                if (pathPdf.exists() || pathPdf.createNewFile())
                {
                    try
                    {
                        final JSONObject jsonPathPdf = new JSONObject(Files.readString(pathPdf.toPath()));
                        if (file != null) jsonPathPdf.put("path" , file.getAbsolutePath());
                        if (notEmpty(hiMessage)) jsonPathPdf.put("hi_message" , hiMessage);
                        if (pathPhones != null) jsonPathPdf.put("path_phones" , pathPhones.getAbsolutePath());
                        Files.writeString(pathPdf.toPath() , jsonPathPdf.toString());
                        setStatus("Set path pdf");
                    }
                    catch (JSONException e)
                    {
                        setStatus("Error set path pdf: " + e.getMessage());
                    }
                }
            }
            catch (IOException e)
            {
                setStatus("Error set path pdf: " + e.getMessage());
            }

        }).start();
    }

    private String[] getPathPdf()
    {
        final File pathPdf = new File(Path.PATH_PDF);

        try
        {
            if (pathPdf.exists())
            {
                try
                {
                    final JSONObject jsonPathPdf = new JSONObject(Files.readString(pathPdf.toPath()));
                    return new String[]{jsonPathPdf.getString("path") , jsonPathPdf.getString("hi_message") , jsonPathPdf.getString("path_phones")};
                }
                catch (JSONException e)
                {
                    setStatus("Error get path pdf: " + e.getMessage());
                }
            }
        }
        catch (IOException e)
        {
            setStatus("Error get path pdf: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onClickBtnSendPdf()
    {
        if (sendingListMessage)
        {
            setStatus("Sending message is running...");
            return;
        }
        new Thread(() ->
        {
            try
            {
                if (phoneNumberImports == null || phoneNumberImports.size() == 0)
                {
                    setStatus("Phones is empty!");
                    return;
                }

                sendingListMessage = true;
                for (PhoneNumberImport phoneNumberImport : phoneNumberImports)
                {
                    if (cancelImportNumber) break;

                    message.setPhone(phoneNumberImport.getPhone());
                    message.setText(txtHiMessage.getText());
                    timerSendListMessageIsRunning = true;
                    send(HomeController.this::setTimer);
                    while (HomeController.this.sendingMessage || HomeController.this.timerSendListMessageIsRunning)
                    {
                        try
                        {
                            Thread.sleep(100);
                        }
                        catch (InterruptedException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                    phoneNumberImport.setSend(true);
                }
            }
            catch (Exception ignored)
            {
            }

            sendingListMessage = false;
            setStatus("Completed!");
            stopImportNumber();
        }).start();
    }

    private void send(final CompletedMessageSend completedMessageSend)
    {
        new Thread(() ->
        {
            final String phone = checkPhone();
            if (phone != null)
            {
                if (Media.checkEmpty(message.getPdf()))
                {
                    connector.hasWhatsapp(message.getPhone() , has ->
                    {
                        if (has)
                        {
                            sendText();
                            sleepForSendMessage();

                            final List<Media> media = message.getPdf();
                            if (Media.checkEmpty(media))
                            {
                                for (final Media pdf : media)
                                {
                                    sendPdfMessage(pdf);
                                    sleepForSendMessage();
                                }
                            }
                        }
                        else
                        {
                            sendingMessage = false;
                            setStatus(String.format("This phone{%s} does not have whatsapp" , message.getPhone()));
                        }

                        if (completedMessageSend != null) completedMessageSend.onCompleted();
                    });
                }
                else
                {
                    if (completedMessageSend != null) completedMessageSend.onCompleted();
                    setStatus("Media is empty");
                }
            }
            else
            {
                setStatus("Phone number is empty");
                if (completedMessageSend != null) completedMessageSend.onCompleted();
            }
        }).start();
    }

    private void sleepForSendMessage()
    {
        while (sendingMessage)
        {
            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendPdfMessage(final Media media)
    {
        sendingMessage = true;
        new Thread(() ->
        {
            if (Media.checkEmpty(message.getPdf()))
            {
                setStatus(String.format("PDF message sending to {%s}" , message.getPhone()));

                try (final InputStream inputStream = new File(media.getPath()).toURI().toURL().openStream())
                {
                    connector.whatsapp.sendMessage(connector.getContactJid(message.getPhone()) , DocumentMessage.newDocumentMessage()
                                    .storeId(connector.whatsapp.store().id())
                                    .fileName(FilenameUtils.getName(media.getPath()))
                                    .media(inputStream.readAllBytes()).create())
                            .thenAccept(messageInfo -> thenAcceptSendPdf());
                }
                catch (Exception e)
                {
                    setStatus(String.format("Error send PDF message to {%s}: %s" , message.getPhone() , e.getMessage()));
                    sendingMessage = false;
                }
            }
            else sendingMessage = false;
        }).start();
    }

    private void thenAcceptSendPdf()
    {
        setPathPdf(null , null , message.getText());
        setStatus(String.format("PDF message sent to {%s}" , message.getPhone()));
        sendingMessage = false;
    }

    private void sendText()
    {
        sendingMessage = true;
        new Thread(() ->
        {
            if (notEmpty(message.getText()))
            {
                try
                {
                    setStatus(String.format("Text message sending to {%s}" , message.getPhone()));

                    connector.whatsapp.sendMessage(connector.getContactJid(message.getPhone()) , TextMessage.newTextMessage().text(message.getText()).description("Powered by bardiademon.com").create()).thenAccept(messageInfo ->
                    {
                        setStatus(String.format("Text message sent to {%s}" , message.getPhone()));
                        sendingMessage = false;
                    });
                }
                catch (Exception e)
                {
                    setStatus(String.format("Error send text message to {%s}: %s" , message.getPhone() , e.getMessage()));
                    sendingMessage = false;
                }
            }
            else sendingMessage = false;
        }).start();
    }

    private String checkPhone()
    {
        String phone = message.getPhone();
        if (phone != null && !phone.isEmpty())
        {
            if (phone.matches("(^(989)|^(\\+989)|^(09)|^(9))(\\d+)") && phone.length() >= 10 && phone.length() <= 13)
            {
                int substring = -1;
                if (phone.startsWith("+98")) substring = 3;
                else if (phone.startsWith("0")) substring = 1;

                if (substring > 0)
                {
                    phone = phone.substring(substring);
                    phone = "98" + phone;
                }
                return phone;
            }
            else setStatus(String.format("invalid phone number {%s}" , phone));
        }
        else setStatus("Phone number is empty");

        return null;
    }

    private interface CompletedMessageSend
    {
        void onCompleted();
    }

    @Override
    protected void onClickBtnImportNumber()
    {
        if (phoneNumberImports == null || phoneNumberImports.size() == 0)
        {
            cancelImportNumber = false;
            timerSendListMessageIsRunning = false;

            setStatus("Import number");
            final JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setFileFilter(new FileNameExtensionFilter("JSON file" , "json"));
            final int dialogResult = chooser.showOpenDialog(null);
            if (dialogResult == JFileChooser.OPEN_DIALOG)
            {
                final File selectedFile = chooser.getSelectedFile();
                importPhones(selectedFile);
            }
        }
        else
        {
            setStatus("Clear import");
            stopImportNumber();
        }
    }

    private void importPhones(final File selectedFile)
    {
        new Thread(() ->
        {
            if (selectedFile != null && selectedFile.exists())
            {
                try
                {
                    final String jsonStr = Files.readString(selectedFile.toPath());

                    try
                    {
                        final JSONArray jsonNumber = new JSONArray(jsonStr);

                        phoneNumberImports = new ArrayList<>();
                        for (final Object item : jsonNumber)
                        {
                            if (item instanceof final JSONObject jsonItem)
                            {
                                final PhoneNumberImport phoneNumberImport = new PhoneNumberImport();
                                phoneNumberImport.setName(jsonItem.getString("name"));
                                phoneNumberImport.setPhone(jsonItem.getString("phone"));
                                setStatus(phoneNumberImport.toString());
                                phoneNumberImports.add(phoneNumberImport);
                            }
                        }
                        if (phoneNumberImports.size() > 0)
                        {
                            setPathPdf(null , selectedFile , null);

                            setStatus("Successfully import number!");
                            setLblNumberOfNumbers(null);
                            SwingUtilities.invokeLater(() -> btnImportNumber.setText("Clear import"));
                        }
                        else throw new IOException("Not found info");
                    }
                    catch (JSONException e)
                    {
                        setStatus("Invalid json file: " + e.getMessage());
                        phoneNumberImports.clear();
                        phoneNumberImports = null;
                    }
                }
                catch (IOException e)
                {
                    setStatus("Error load json number: " + e.getMessage());
                }
            }
        }).start();
    }

    private void stopImportNumber()
    {
        if (phoneNumberImports != null)
        {
            phoneNumberImports.clear();
            phoneNumberImports = null;
        }
        setLblNumberOfNumbers(null);

        cancelImportNumber = true;
        if (timerSendListMessage != null)
        {
            timerSendListMessage.cancel();
            timerSendListMessage = null;
        }
        timerSendListMessageIsRunning = false;

        SwingUtilities.invokeLater(() -> btnImportNumber.setText("Import phones"));
    }

    @Override
    protected void onClickBtnClearLog()
    {
        SwingUtilities.invokeLater(lstLogModel::clear);
    }


    @Override
    protected void onClickBtnConnect()
    {
        final String btnText;

        if (connector.isConnected())
        {
            connector.disconnect();
            btnText = "Disconnecting...";
        }
        else
        {
            connector.connectToWhatsapp(this);
            btnText = "Connecting...";
        }

        setStatus(btnText);
        SwingUtilities.invokeLater(() -> btnConnect.setText(btnText));
    }

    private void setTimer()
    {
        new Thread(() ->
        {
            if (timerSendListMessage != null) timerSendListMessage.cancel();
            timerSendListMessage = new Timer();

            final AtomicInteger sec = new AtomicInteger(0);
            final AtomicInteger min = new AtomicInteger(MAX_MIN_WAIT_SEND_LIST_MESSAGE);
            timerSendListMessage.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (!timerSendListMessageIsRunning)
                    {
                        timerSendListMessage.cancel();
                        return;
                    }

                    if (sec.get() <= 0)
                    {
                        if (min.get() <= 0)
                        {
                            min.set(0);
                            sec.set(0);
                            timerSendListMessageIsRunning = false;
                            timerSendListMessage.cancel();
                        }
                        else
                        {
                            sec.set(59);
                            min.decrementAndGet();
                        }
                    }
                    else sec.decrementAndGet();

                    final String secStr = sec.get() < 10 ? "0" + sec.get() : String.valueOf(sec.get());
                    final String minStr = min.get() < 10 ? "0" + min.get() : String.valueOf(min.get());
                    setLblNumberOfNumbers(String.format("%s:%s" , minStr , secStr));
                }
            } , 1000 , 1000);
        }).start();
    }

    private void setLblNumberOfNumbers(final String time)
    {
        new Thread(() ->
        {
            int counterSend = 0;
            if (phoneNumberImports != null && phoneNumberImports.size() > 0)
            {
                for (final PhoneNumberImport phoneNumberImport : phoneNumberImports)
                    if (phoneNumberImport.isSend()) counterSend++;
            }

            final int finalCounterSend = counterSend;
            SwingUtilities.invokeLater(() ->
            {
                final StringBuilder value = new StringBuilder(String.format("%d / %d" , finalCounterSend , (phoneNumberImports != null ? phoneNumberImports.size() : 0)));
                if (notEmpty(time)) value.append(' ').append(time);
                lblNumberOfPhones.setText(value.toString());
            });
        }).start();
    }

    private boolean notEmpty(final String val)
    {
        return val != null && !val.isEmpty();
    }

    @Override
    public void onReady()
    {
        if (qrCodeViewerController != null) qrCodeViewerController.close();

        setStatus("Connected!");

        SwingUtilities.invokeLater(() -> btnConnect.setText("Disconnect"));
    }

    @Override
    public void onDisconnect()
    {
        SwingUtilities.invokeLater(() -> btnConnect.setText("Connect"));
    }

    @Override
    public void onQrCode(InputStream inputStream)
    {
        setStatus("Qr code");
        if (qrCodeViewerController == null)
            qrCodeViewerController = new QrCodeViewerController(() -> qrCodeViewerController = null);
        qrCodeViewerController.setImage(inputStream);
    }

    private void setStatus(final String status)
    {
        SwingUtilities.invokeLater(() ->
        {
            lblValStatus.setText(status);

            final LocalDateTime now = LocalDateTime.now();
            final String dateTime = now.format(logTimeFormatter);
            lstLogModel.addElement(String.format("%s :: %s" , status , dateTime));
        });
    }
}
