import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

/*
 * Class responsible for downloading a given web page and storing it as a linked list.  
 */
public class PicDownloader {
	// Variable containing web page source - each line in each element.
	LinkedList<String> page;

	// Constructor initializes the list.
	public PicDownloader(){
		this.page = new LinkedList<String>();
	}
	
	// Getter for the page source.
	public LinkedList<String> getPage(){
		return page;
	}
	
	// Clearing the list before the next usage (presumably next web page).
	public void clearPage(){
		this.page = new LinkedList<String>();
	}
	
	// For a given page address we store source as a list of strings in "page" field.
	public void downloadPage(String pageAddress) throws Exception  {
		// Creating URL from a string and connecting to a site.
        URL site = new URL(pageAddress);
        URLConnection siteConnection = site.openConnection();
        // Creating a buffer for reading.
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                siteConnection.getInputStream()));
        // Variable for a single line.
        String inputLine;
        // Iterating over the whole page.
        while ((inputLine = in.readLine()) != null){
        	// Adding line of code to the list.
        	page.add(inputLine);
        }
        // Closing the connection with a web site.
        in.close();
	}
	
}
