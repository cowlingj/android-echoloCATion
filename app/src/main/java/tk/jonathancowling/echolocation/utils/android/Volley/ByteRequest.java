package tk.jonathancowling.echolocation.utils.android.Volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.*;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by jonathan on 19/12/17.
 */

public class ByteRequest extends Request<Byte[]> {

    private Listener<Byte[]> mListener;

    public ByteRequest(int method, String url, Listener<Byte[]> listener, ErrorListener elistener) {
        super(method, url, elistener);
        mListener = listener;
    }

    // default request is a GET
    public ByteRequest(String url, Listener<Byte[]> listener, ErrorListener eListener){
        this(Method.GET, url, listener, eListener);
    }

    @Override
    protected Response<Byte[]> parseNetworkResponse(NetworkResponse response) {
        Byte[] bytes = new Byte[response.data.length];
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = response.data[i];
        }
        return Response.success(bytes, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Byte[] response) {
        if (mListener != null){
            mListener.onResponse(response);
        }
    }

    @Override
    public String getBodyContentType(){
        return "application/octet-stream";
    }
}
