import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Class retrieves a direct link to an image from a web page with only one picture.
 */
public class PicRetriever {
	// List of direct links to pictures.
	LinkedList<String> directPicList;
	
	// Constructor initializes the list.
	public PicRetriever() {
		this.directPicList = new LinkedList<String>();
	}
	
	// Clearing the list before the next usage (presumably another web page).
	public void clearDirectPicList(){
		this.directPicList = new LinkedList<String>();
	}
	
	// Getting the direct links to images.
	public LinkedList<String> gerDirectPicList(){
		return directPicList;
	}
	
	// Retrieving the links to pics from all a set of web pages.
	public void retrievePics(LinkedList<String> picList) throws Exception{
		// Tmp variable to iterate through the links of pages with embedded pics. 
		LinkedList<String> tmpPicList = picList;
		String picPageLink;
		// Iterating over all the pics on a page (meaning in a list).
		while (!tmpPicList.isEmpty()){
			// Take the first address in the dequeue and retrieve a pic from there.
			picPageLink = tmpPicList.removeFirst();
			retrievePic(picPageLink);
		}
	}
	
	// Retrieving a single pic address from a given page.
	public void retrievePic(String pageAddress){
		// Downloader will download the page where pic is embeded (together with ads, etc).
		PicDownloader picPage = new PicDownloader();
		try {
			picPage.downloadPage(pageAddress);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Tmp variable to treat lines of a page as dequeue.
		LinkedList<String> tmpPage = new LinkedList<String>(picPage.getPage());
		// Iterating over all of the lines in a page.
		while(!tmpPage.isEmpty()){
			String line = tmpPage.removeFirst();
			// Retrieving an image (if applicable).
			retrieve(line);
		}
		
	}
	
	public void retrieve(String line){
		// Pattern to match. Pattern picks rows that refer to pictures on a certain 
		// page (they are always in .../obrazek/ directory).
		Pattern p = Pattern.compile("class=\"mOUrl\" target=\"_self\"><img alt=\"");

		// Create a matcher with pageBody.
		Matcher m = p.matcher(line);
		
		// Indicator whether there are still some pattern matchings in the string. 
        boolean result = m.find();

        // Iterating until there are no matches for the pattern. Adding addresses to picsList.
        // There should be only one pic per page here, so while might be replaced with if statement.
        while(result) {
        	// Counting the offsets that say where the link starts and finishes.
        	String startPattern = "src=\"";
        	int offsetStart = line.indexOf(startPattern);
        	offsetStart += startPattern.length();
        	String endPattern = "\" title";
        	int offsetEnd = line.indexOf(endPattern);
        	// Getting the link and adding it to the list.
        	directPicList.add(line.substring(offsetStart, offsetEnd));
        	// Looking for next occurrence of the pattern. Shouldn't find any - one pic per page.
            result = m.find();
        }
	}
	
	// TEST showing how the class works.
    public static void main(String[] args) 
    throws Exception {
    	
    	PicRetriever pirRetriever = new PicRetriever();
    	pirRetriever.retrievePic("http://kwejk.pl/obrazek/736419/fuck,it.html");
    	
    }
	
}
