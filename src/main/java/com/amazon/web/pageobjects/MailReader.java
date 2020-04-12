package com.amazon.web.pageobjects;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MailReader {

	  String hostName = "smtp.gmail.com";
	  String username = "soniyarani723@gmail.com"; 
	  String password = "Soniya@12";
	  
	int unreadMsgCount;
	String emailSubject;
	Message emailMessage;

	public String mailReader(String userType) {

		Properties prop = System.getProperties();
		prop.setProperty("mail.store.protocol", "imaps");
		Folder inbox = null;
		Store store = null;
		String otp = null;
		try {
			Session session = Session.getInstance(prop, null);
			store = session.getStore();
			store.connect(hostName, username, password);
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			ArrayUtils.reverse(messages);
			for (int i = 0; i < messages.length; i++) {
				Message msg = messages[i];

				// Getting mail subject
				String strMailSubject = (String) msg.getSubject();
				if (strMailSubject.contains(Common.NEW_USER_AUTHENTICATION_MAIL_SUBJECT) && userType.equals(Common.NEW_USER)) {
					String content = getText(true, msg).toString();
					Document html = Jsoup.parse(content);
					otp = html.body().getElementsByClass("otp").text();
					break;
				} else if (strMailSubject.contains(Common.EXISTING_USER_AUTHENTICATION_MAIL_SUBJECT)
						&& userType.equals(Common.EXISTING_USER)) {
					String content = getText(true, msg).toString();
					Document html = Jsoup.parse(content);
					otp = html.body().getElementsByClass("otp").text();
					break;
				}

			}
			return otp;
		} catch (MessagingException messagingException) {
			messagingException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			if (inbox != null) {
				try {
					inbox.close(true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return otp;
	}

// This function is used to get the text based on the content type	
	public String getText(boolean textIsHtml, Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String content = (String) p.getContent();
			textIsHtml = p.isMimeType("text/html");
			return content;
		}
		if (p.isMimeType("multipart/alternative")) {
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(textIsHtml, bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String content = getText(textIsHtml, bp);
					if (content != null)
						return content;
				} else {
					return getText(textIsHtml, bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String content = getText(textIsHtml, mp.getBodyPart(i));
				if (content != null)
					return content;
			}
		}
		return null;
	}

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
