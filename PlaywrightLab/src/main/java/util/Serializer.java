package util;

import com.google.gson.Gson;

import java.io.*;

public class Serializer {
    public static void serialize(Object obj, OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        ( new Gson()).toJson(obj, obj.getClass(), outputStreamWriter);
        outputStreamWriter.close();
    }

    public static <T> T deserialize(Class<T> clazz, InputStream inputStream) {
        return (new Gson()).fromJson(new InputStreamReader(inputStream), clazz);
    }
}
