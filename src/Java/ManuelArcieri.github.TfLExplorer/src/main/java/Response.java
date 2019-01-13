import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;

public class Response
{
    public final HttpURLConnection httpURLConnection;
    public final int responseCode;
    public final String responseMessage;
    public final String contentType;
    public final long contentLength;
    public final Date date;

    public Response(HttpURLConnection connection) throws IOException
    {
        httpURLConnection = connection;
        responseCode = connection.getResponseCode();
        responseMessage = connection.getResponseMessage();
        contentType = connection.getContentType();
        contentLength = connection.getContentLengthLong();
        date = new Date(connection.getDate());
    }
}
