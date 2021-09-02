/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipodecarnetdigital.controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import prototipodecarnetdigital.view.FrmGenerarCarnet;
import prototipodecarnetdigital.view.FrmNomina;

/**
 *
 * @author BryanPC
 */
public class ImprimirCarnet {

    public void printRecord(JPanel panel) {

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Print Record");
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D graphics2D = (Graphics2D) graphics;
                graphics2D.translate(pageFormat.getImageableX() - 2, pageFormat.getImageableY() - 2);
                graphics2D.scale(2.70, 2.3);

                panel.paint(graphics2D);

                return Printable.PAGE_EXISTS;

            }

        });
        boolean returningresult = printerJob.printDialog();

        if (returningresult) {
            try {

                printerJob.print();

            } catch (PrinterException printerException) {
                JOptionPane.showConfirmDialog(null, "Print error: " + printerException.getMessage());

            }
        }
    }

    public void enviarCorreo(String destinatario) throws MessagingException {
        
        try {
            // se obtiene el objeto Session. La configuración es para
            // una cuenta de gmail.
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.setProperty("mail.smtp.user", "eduinfantilmin2021@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Carnet Virtual");

            File archivoSeleccionado;
            JFileChooser seleccionarArchivo;
            seleccionarArchivo = new JFileChooser();
            seleccionarArchivo.showOpenDialog(null);
            archivoSeleccionado = seleccionarArchivo.getSelectedFile();
            System.out.println("El archivo seleccionado es: " + archivoSeleccionado);
            System.out.println("Path Actual: " + seleccionarArchivo.getCurrentDirectory());

            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(""+archivoSeleccionado)));
            adjunto.setFileName("Universidad de las Fuerzas Armadas - ESPE"+archivoSeleccionado.getName());

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(destinatario));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(destinatario));
            message.setSubject("MENSAJE DE CARNETIZACION");
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect("carnetizaciondigital2021@gmail.com", "2897479852");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

