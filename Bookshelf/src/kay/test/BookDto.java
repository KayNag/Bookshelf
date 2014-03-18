package kay.test;

public class BookDto {

	private String id;
	private String title;
	private String thumbnail;
	private boolean newItem;
	
	public BookDto() {
		super();
	}
	
	public BookDto(String id, String title, String thumbnail, boolean newItem) {
		super();
		this.id = id;
		this.title = title;
		this.thumbnail = thumbnail;
		this.newItem = newItem;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public boolean isNewItem() {
		return newItem;
	}
	public void setNewItem(boolean newItem) {
		this.newItem = newItem;
	}
	
	
}
