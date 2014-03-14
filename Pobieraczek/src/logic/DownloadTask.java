package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadTask {

	private final String base = "http://www.tvp.pl/shared/cdn/tokenizer.php?object_id=";
	private String ID;
	private String fileName;
	private String location = "C:\\Documents and Settings\\khoffmann\\Pulpit\\";
	private String extension;
	private String downloadPage;
	private ArrayList<String> downloadLinks = new ArrayList<>();
	private String link = "999999";
	
	public DownloadTask(String ID, String fileName) {
		this.ID = ID;
		this.fileName = fileName;
	}

	public DownloadTask(String ID, String filename, String location){
		this.ID = ID;
		this.fileName = filename;
		this.location = location;
	}
	private String removeBackslashes(String withBackslashes) {
		StringBuffer ready = new StringBuffer();
		for (int i = 0; i < withBackslashes.length(); i++) {
			if (withBackslashes.charAt(i) != '\\') {
				ready.append(withBackslashes.charAt(i));
			}
		}

		return ready.toString();
	}

	private void getDownloadPage() {
		int counter = 5;
		URL url;
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			url = new URL(base + ID);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();

		} catch (IOException e) {
			counter--;
			if (counter > 0) {
				try {
					wait(5000);
					getDownloadPage();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				}

			}
			e.printStackTrace();
		}
		downloadPage = buffer.toString();

	}

	private void getDownloadLinks() {
		Pattern URLPattern = Pattern.compile("\"(http(.*?))\"");

		Matcher matcher = URLPattern.matcher(downloadPage);
		while (matcher.find()) {
			String found = matcher.group(1);
			if ((!found.endsWith("manifest")) && (!found.endsWith("m3u8"))) {
				downloadLinks.add(removeBackslashes(found));
				if (found.endsWith("mp4")) {
					extension = ".mp4";
				} else {
					extension = ".wmv";
				}
			}
			matcher.start();
			matcher.end();
		}
	}

	private void getDefaultLink() {
		
		
		
		if (this.extension.equals(".mp4")) {
			for (String temp : downloadLinks) {
				if (temp.charAt(temp.length() - 5) < link
						.charAt(link.length() - 5)) {
					link = temp;

				}
			}

		}
		else{
			link = downloadLinks.get(0);
		}
		
		
	}

	private void downloadDefaultLink(){
		int counter = 5;
		
		try {
			System.out.println();
			URL url = new URL(link);
			System.out.println(link);
			Files.copy(url.openStream(), Paths.get(location + fileName +extension));
		} catch (IOException e) {
			counter--;
			if (counter > 0) {
				try {
					wait(5000);
					getDownloadPage();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				}

			}
			e.printStackTrace();
		}
	}
	protected void download(){
		this.getDownloadPage();
		this.getDownloadLinks();
		this.getDefaultLink();
		this.downloadDefaultLink();
	}

}
