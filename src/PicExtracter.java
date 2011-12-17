import java.util.LinkedList;
import java.util.regex.*;

/*
 * Class extracts links to "pages with one picture" from a given page "with many pictures".
 * The extracted pages still contain a pic, ads and lots of useless elements. Pics should be
 * further retrieved.
 */
public class PicExtracter {
	// List of URL addresses to pages with one pic on each. 
	private LinkedList<String> picsList;
	
	// Constructor initializes the list addresses.
	public PicExtracter() {
		this.picsList = new LinkedList<String>();
	}
	
	// Removing old list of addresses and preparing for next usage (presumably for next web page 
	// with many pictures). 
	public void clearPicList(){
		picsList = new LinkedList<String>();
	}
	
	// Method takes a web page, which each line of source code is stored as next element of lined list.
	// We will use it as a dequeue data structure.
	// Method uses "extract" method to get the links to pages related to pics. 
	public void extractPage(LinkedList<String> page){
		// Tmp page used as dequeue.
		LinkedList<String> tmpPage = new LinkedList<String>(page);
		// Iterating over the whole page.
		while(!tmpPage.isEmpty()){
			String line = tmpPage.removeFirst();
			// Extracting the links to pics (if applicable).
			extract(line);
		}
	}
	
	// Checking if a given souce code line contain link to page related to picture. If yes
	// then extracting the link and storing it in the picsList. If not doing nothing.
	// Finding pattern regular expression in a given pageBody (basically it's a line of source code).
	public void extract(String pageBody){
		// Pattern to match. Pattern picks rows that refer to pictures on a certain 
		// page (they are always in .../obrazek/ directory).
		Pattern p = Pattern.compile("<a href=\"/obrazek/[^\"]*\" ");
		// Create a matcher with pageBody.
		Matcher m = p.matcher(pageBody);
		
		// Indicator whether there are still some pattern matchings in the string. 
        boolean result = m.find();
        // Offside which helps to get exactly the part of the address starting from /obrazek.
        // Offside should be removed in the future and replaced with regex expression
        // which will convert <a href="/obrazek/fooo.htm" in to "/obrazek/fooo.htm".
        int shortURLOffside = 9;
        // Iterating until there are no matches for the pattern. Adding addresses to picsList.
        while(result) {
        	// Taking first group that matches the pattern and subtracting exactly the needed part of string. 
        	String picAddress = m.group().substring(shortURLOffside,m.group().length()-2); 
        	picsList.add("http://kwejk.pl" + picAddress);
        	// Looking for next occurrence of the pattern.
            result = m.find();
        }
	}
	
	// Getter for picsList.
	public LinkedList<String> getPicList(){
		return picsList;
	}
	
	// TEST showing how the class works.
    public static void main(String[] args) 
    throws Exception {
    	PicExtracter pe = new PicExtracter();
    	String testBody = "<a href=\"/obrazek/art\" " +
		  " two cats in the yard" +
		  "<a href=\"/obrazek/sfg\" s";
    	
    	pe.extract(testBody);
    }
    
}
