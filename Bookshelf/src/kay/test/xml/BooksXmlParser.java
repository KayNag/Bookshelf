package kay.test.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kay.test.BookDto;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class BooksXmlParser {

	private static String FEED_TAG = "ZREADER";
	private static String BOOK_TAG = "BOOK";
	private static String ID_TAG = "ID";
	private static String TITLE_TAG = "TITLE";
	private static String THUMBNAIL_TAG = "THUMBNAIL";
	private static String NEW_TAG = "NEW";
	
	// We don't use namespaces
	private static final String ns = null;
	
	public List<BookDto> parse(InputStream in) throws XmlPullParserException, IOException {
	   
		try {
	        XmlPullParser parser = Xml.newPullParser();
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(in, null);
	        parser.nextTag();
	
	        return readFeed(parser);
		} finally {
            in.close();
        }
	}
	
   protected List<BookDto> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

       List<BookDto> list = new ArrayList<BookDto>();
	   
	    parser.require(XmlPullParser.START_TAG, ns, FEED_TAG);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name.equals(BOOK_TAG)) {
	            BookDto book = readBook(parser); 
	            if (book != null) {
	            	list.add(book);
	            }
	        } else {
	            skip(parser);
	        }
	    }  
	    return list;
	}
   
   	protected BookDto readBook(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, BOOK_TAG);
		
		String id = null;
		String title = null;
		String thumbnail = null;
		boolean newItem = false;
		
		while (parser.next() != XmlPullParser.END_TAG) {
		    if (parser.getEventType() != XmlPullParser.START_TAG) {
		        continue;
		    }
		    String name = parser.getName();
		    if (name.equals(ID_TAG)) {
		    	id = readInnerText(parser, ID_TAG);
		    	
		    } else if (name.equals(TITLE_TAG)) {
		    	title = readInnerText(parser, TITLE_TAG);
		    	
		    } else if (name.equals(THUMBNAIL_TAG)) {
		    	thumbnail = readInnerText(parser, THUMBNAIL_TAG);
		    	
		    } else if (name.equals(NEW_TAG)) {
		    	newItem = Boolean.valueOf(readInnerText(parser, NEW_TAG));
		    	
		    } else {
		        skip(parser);
		    }
		}
	    return new BookDto(id, title, thumbnail, newItem);
   	}
   
   	
   	
   	private String readInnerText(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
   	    parser.require(XmlPullParser.START_TAG, ns, tagName);
   	    String title = readText(parser);
   	    parser.require(XmlPullParser.END_TAG, ns, tagName);
   	    return title;
   	}
   	
   	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
   	    String result = "";
   	    if (parser.next() == XmlPullParser.TEXT) {
   	        result = parser.getText();
   	        parser.nextTag();
   	    }
   	    return result;
   	}
   	
   	protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
   	    if (parser.getEventType() != XmlPullParser.START_TAG) {
   	        throw new IllegalStateException();
   	    }
   	    int depth = 1;
   	    while (depth != 0) {
   	        switch (parser.next()) {
   	        case XmlPullParser.END_TAG:
   	            depth--;
   	            break;
   	        case XmlPullParser.START_TAG:
   	            depth++;
   	            break;
   	        }
   	    }
   	 }
}
