package tk.jonathancowling.echolocation.utils.net;

/**
 * Created by jonathan on 20/12/17.
 */

public class HttpException extends NetworkException {
    public HttpException(){ super(); }

    public HttpException(String msg){ super(msg); }

    public HttpException(Throwable cause){ super(cause); }

    public HttpException(String msg, Throwable cause){ super(msg, cause); }
}