package com.fookart.app.io;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import com.fookart.app.provider.ProductContract;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by ashu on 26/7/14.
 */
public abstract class JSONHandler {

    protected static Context mContext;

    public JSONHandler(Context context) { mContext = context; }

    public abstract ArrayList<ContentProviderOperation> parse(String json) throws IOException;

    public final void parseAndApply(String json) throws IOException {
        try{
            final ContentResolver resolver = mContext.getContentResolver();
            ArrayList<ContentProviderOperation> batch = parse(json);
            resolver.applyBatch(ProductContract.CONTENT_AUTHOURTY, batch);
        } catch (RemoteException e) {
            throw new RuntimeException("problem applying batch operation");
        } catch (OperationApplicationException e) {
            throw new RuntimeException("problem applying batch operation");
        }
    }

    public static String parseResource(Context context, int resource) throws IOException {
        InputStream is = context.getResources().openRawResource(resource);
        Writer writer = new StringWriter();
        char buffer[] = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }
}
