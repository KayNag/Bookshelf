package kay.test.adapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import kay.test.BookDto;
import kay.test.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BooksAdapter extends BaseAdapter {
	
	private Context context;
	private final List<BookDto> books;
	
	public BooksAdapter(Context context, List<BookDto> books) {
		this.context = context;
		this.books = books;
	} 
	
	@Override
	public int getCount() {
		return books.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
		View gridView;
	 
		if (convertView == null) {
			
			gridView = new View(context);
	 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.item, null);
	  
			BookDto book = books.get(position);
			if (book != null) {
			
				TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
				textView.setText(book.getTitle());

				try {
					Bitmap icon = BitmapFactory.decodeStream((new URL(book.getThumbnail())).openConnection() .getInputStream());
					
					ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_cover);
					imageView.setImageBitmap(icon);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

				ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_new);
				imageView.setVisibility(book.isNewItem() ? View.VISIBLE : View.INVISIBLE);

			} else {
				//TODO error
			}
	 	 
		} else {
			gridView = (View) convertView;
		}
		return gridView;
	}
	

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
