package com.aribhatt.stickynotes;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class StickyNote {
    final static String FILE_NAME = "sticky.txt";
    static String getSticky(Context context){
        File file = new File(context.getFilesDir().getPath() + "/" + FILE_NAME);
        StringBuffer text = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                text.append(line);
                text.append("\n");
            }
            br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return text.toString();
    }

    static void setSticky(Context context, String text) {
        FileOutputStream fos = null;
        try{
            fos = context.getApplicationContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
