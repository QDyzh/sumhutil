package top.sumhzehn.poi;

/**
 * SumhZehn
 * 2021/9/16 21:10
 **/
public class POIFileException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public POIFileException(String msg) {
        super(msg);
    }

    public POIFileException(String msg, Throwable e) {
        super(msg, e);
    }
}
