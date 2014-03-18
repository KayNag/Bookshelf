package kay.test.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import kay.test.BookDto;
import kay.test.adapter.BooksAdapter;
import kay.test.view.BookshelfView;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadBooksTask extends AsyncTask<String, Void, List<BookDto>>{

	private Context context; 
	private BookshelfView view;
	
	public DownloadBooksTask(Context context, BookshelfView view) {
		super();
		this.context = context;
		this.view = view;
	}
	
	@Override
	protected void onPreExecute() { 
		super.onPreExecute();
	}
	
	@Override
	protected List<BookDto> doInBackground(String... params) {
		if ((params == null) || (params.length != 1)) {
			throw new IllegalArgumentException("The URL is expected as a parameter.");
		}
		 
		InputStream is = null;
		try {
			is = download(params[0]);
			if (is != null) {
				return (new BooksXmlParser()).parse(is);
			}
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<BookDto> result) {
		super.onPostExecute(result);

		if (result != null) {
			view.setAdapter(new BooksAdapter(context, result));
		} else {
			(Toast.makeText(context, "An error occured.", Toast.LENGTH_LONG)).show();
		}
	}
	
	protected InputStream download(String urlString) throws IOException {
	    HttpURLConnection conn = (HttpURLConnection) (new URL(urlString)).openConnection();
	    conn.setReadTimeout(10000);
	    conn.setConnectTimeout(15000);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);
	    
	    conn.connect();
	    return conn.getInputStream();      
	}
}
