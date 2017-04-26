
public class Message2 {
	

	private long size;
	private String message;
	
public Message2() {
		
	}
	
	public Message2(String message) {
		this.size = message.length();
		this.message = message;
	}
	
	public long getSize() {
		return message.length();
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
 
}
