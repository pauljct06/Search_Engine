/**
 * Returns body content from a webaddress
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebsiteProcessor {
	private String webaddress;
	private String content;
	private Pattern pattern = Pattern.compile("<body.*>(.+?)</body>");
	private Matcher matcher;
	
	public WebsiteProcessor() {
		this("");
	}
	
	public WebsiteProcessor(String webaddress) {
		this.webaddress = webaddress;
	}
	
	public void process() throws IOException {
		String webContent = this.getHTMLFromAddress(this.webaddress);
		//System.out.println(webContent);
		String bodyContent = this.getBodyFromHTML(webContent);
		//System.out.println(bodyContent);
		this.content = bodyContent;
	}
	
	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}
	
	public String getWebaddress() {
		return this.webaddress;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getHTMLFromAddress(String webpage) throws IOException {
		URL url = new URL(this.webaddress);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
		    baos.write(buf, 0, len);
		}
		
		String body = new String(baos.toByteArray(), encoding);
		return body.replace("\n", "").replace("\r", "");
	}
	
	public String getBodyFromHTML(String webContent) {
		matcher = this.pattern.matcher(webContent);
		
		if (!matcher.find()) {
			System.out.println("NOTHING FOUND FOR " + this.webaddress);
			System.out.println(webContent);
			return "";
		}
		
		return matcher.group(0).replaceAll("\\<.*?>","");
	}
}
