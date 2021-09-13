package top.sumhzehn.util.map;

public class MapException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public MapException(String msg) {
		 super(msg);
	}
	
	public MapException(String msg, Throwable e) {
		super(msg, e);
	}
}
