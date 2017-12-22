package tk.jonathancowling.echolocation.utils.net;

/**
 * Created by jonathan on 20/12/17.
 */

class NetworkException extends Exception {
    public NetworkException(){ super(); }

    public NetworkException(String msg){ super(msg); }

    public NetworkException(Throwable cause){ super(cause); }

    public NetworkException(String msg, Throwable cause){ super(msg, cause); }
}
