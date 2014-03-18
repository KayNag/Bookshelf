package kay.test.view;


import kay.test.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

public class BookshelfView extends GridView {

	private Bitmap background;
	
	public BookshelfView(Context context) {
		super(context);
		init();
	}
	
	public BookshelfView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BookshelfView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	protected void init() {
		background = BitmapFactory.decodeResource(getResources(), R.drawable.shelfcell_bgr);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
	    int top = getChildCount() > 0 ? getChildAt(0).getTop() : 0;
	    for (int y = top; y < getHeight(); y += background.getHeight()){
	        for (int x = 0; x < getWidth(); x += background.getWidth()){
	            canvas.drawBitmap(background, x, y, null);
	        }
	    }
	    super.dispatchDraw(canvas);
	}
}
