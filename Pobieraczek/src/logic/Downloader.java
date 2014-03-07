package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {

	private final String base = "http://www.tvp.pl/shared/cdn/tokenizer.php?object_id=";
	public String getBase() {
		return base;
	}

	public String getID() {
		return ID;
	}

	private final String ID;

	

	public Downloader(String ID) {
		this.ID = ID;
	}

	private String getDlPage() throws Exception {
		URL DlPage = new URL(getBase() + getID());
		StringBuffer buffer = new StringBuffer();
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				DlPage.openStream()));
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		reader.close();
		return buffer.toString();
	}

	private String[] getDlAdresses(String DLPage) {
		ArrayList<String> lista = new ArrayList<>();
		Pattern URLPattern = Pattern.compile("\"(http(.*?))\"");
		Matcher matcher = URLPattern.matcher(DLPage);
		while (matcher.find()) {
			String found = matcher.group(1);
			if (found.contains("mp4")) {
				lista.add(found);
			}
			matcher.start();
			matcher.end();
		}
		String[] found = new String[lista.size()];

		for (String temp : lista) {
			found[lista.indexOf(temp)] = temp;

		}
		return found;
	}

	private  String getDlURLString(String[] in) {
		String DlURL = in[0];
		for (String temp : in) {
			if(temp.charAt(temp.length() - 5)< DlURL.charAt(DlURL.length() - 5)){
				DlURL=temp;
			}
		}
		return DlURL;
	}

	private String removeBackSlash(String in) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			if (in.charAt(i) != '\\') {
				buffer.append(in.charAt(i));
			}
		}
		return buffer.toString();
	}
	
	private void download (String name)throws Exception{
		String DLPage = this.getDlPage();
		String[] adresses = getDlAdresses(DLPage);
		String adress = getDlURLString(adresses);
		URL link = new URL(removeBackSlash(adress));
		Path dest = Paths.get("C:\\Documents and Settings\\khoffmann\\Pulpit\\" + name);
		Files.copy(link.openStream(), dest);
		
	}
	
	public static void main(String[] args) throws Exception {
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
		// Path dest =
		// Paths.get("C:\\Documents and Settings\\khoffmann\\Pulpit");
		// URL video = new
		// URL("http://195.245.213.192/token/video/vod/14141022/20140305/1542978670/473c334b-270d-4c76-ae0e-9a16d2dcf6d7/video-1.mp4");

		// Files.copy(video.openStream(), Paths.get(dest+"\\test.mp4"));
		Downloader test = new Downloader(args[0]);
		test.download(args[1]);
		System.exit(0);

		
	

	}

}
