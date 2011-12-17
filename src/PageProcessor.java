
/*
 * Class which is joining all the modules and shows how they work together.
 */
public class PageProcessor {
	public static void main(String[] args) throws Exception {
		// TEST variables.
		String basePage = "http://kwejk.pl/strona/";
		int testPageNumberStart = 3710;
		int testPageNumberEnd = 3715;
		
		// Loop over the TEST set (few Kwejk pages).
		for(int i = testPageNumberStart; i < testPageNumberEnd; ++i){
			// Downloading the web page.
			PicDownloader picDownloader = new PicDownloader();
			try{
				picDownloader.downloadPage(basePage+i);
			}
			catch(Exception e){
				System.out.print(e.getMessage());
			}
		
			// Extracting pages with pics on this web page.
			PicExtracter picExtracter = new PicExtracter();
			picExtracter.extractPage(picDownloader.getPage());
		
			// Retrieving images from those web pages.
			PicRetriever picRetriever = new PicRetriever();
			picRetriever.retrievePics(picExtracter.getPicList());
		
			// TEST - Printing the on the console.
			System.out.println(picRetriever.gerDirectPicList());
		
			// Clearing the objects before the next iteration.
			picRetriever.clearDirectPicList();
			picExtracter.clearPicList();
			picDownloader.clearPage();
		}
	}
}
