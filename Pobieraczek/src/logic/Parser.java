package logic;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import media.Medium;

public class Parser {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		final String authUser = "khoffmann";
		final String authPassword = "GA5msBa";
		final String proxyHost = "10.0.0.1";
		final String proxyPort = "8080";

		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);
		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);

		Authenticator.setDefault(new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(authUser, authPassword
						.toCharArray());
			}
		});
		String czas = "121114-godz-1830";
		SimpleDateFormat format = new SimpleDateFormat("dMyy'-godz-'HHmm");
		System.out.println(format.parseObject(czas));
		Date date = format.parse(czas);
		System.out.println(date);
		
	}
	
	
	
	

}
