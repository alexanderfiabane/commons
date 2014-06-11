/*
 * Copyright (C) 2013 Marcius da Silva da Fonseca.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA
 */
package br.msf.commons.mail;

import br.msf.commons.util.ArrayUtils;
import br.msf.commons.util.DateUtils;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Class that sends e-mails.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class EmailSender {

    /**
     * The javamail configs.
     */
    private Properties javamailProperties;

    /**
     * Sets the javamail configs.
     *
     * @param javamailProperties The javamail config entries.
     */
    public void setJavamailProperties(final Properties javamailProperties) {
        this.javamailProperties = javamailProperties;
    }

    /**
     * Sends an e-mail to someone.
     *
     * @param email  The email to be sent.
     * @param params The params to process the message content.
     * @throws Exception If anything goes wrong.
     */
    public void send(final Email email, final Map<String, Object> params) throws Exception {

        // create some properties and get the default Session
        Session session = Session.getInstance(javamailProperties);
        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(email.getFrom()));
        msg.setRecipients(Message.RecipientType.TO, getInternetAddresses(email.getTo()));

        if (!ArrayUtils.isEmptyOrNull(email.getCc())) {
            msg.setRecipients(Message.RecipientType.CC, getInternetAddresses(email.getCc()));
        }
        msg.setSubject(email.getSubject());

        // create and fill the message part
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(email.getContent(params), email.getContentType());

        // create the Multipart and its parts to it
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(bodyPart);

        // add the Multipart to the message
        msg.setContent(mp);
        msg.setSentDate(DateUtils.now());

        // send the message
        Transport.send(msg);
    }

    /**
     * Converts an e-mail address to the format that the javamail API understands.
     *
     * @param adresses The e-mail address, like "foobar@domail.com"
     * @return The e-mail addresses converted.
     * @throws Exception If anything goes wrong.
     */
    private InternetAddress[] getInternetAddresses(final String[] adresses) throws Exception {
        if (ArrayUtils.isEmptyOrNull(adresses)) {
            return new InternetAddress[]{};
        }
        InternetAddress[] iAddresses = new InternetAddress[adresses.length];
        for (int i = 0; i < adresses.length; i++) {
            iAddresses[i] = new InternetAddress(adresses[i]);
        }
        return iAddresses;
    }
}
