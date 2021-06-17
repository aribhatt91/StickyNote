package com.aribhatt.stickynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/*
* https://www.youtube.com/watch?v=qfzdZoEiZFQ
* */
public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private FloatingActionButton save;
    private Button incrSize, descrSize, bold, underline, italic;
    private TextView size;
    float currentSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        size = findViewById(R.id.sizeTxt);
        save = findViewById(R.id.save);
        incrSize = findViewById(R.id.incrSize);
        descrSize = findViewById(R.id.decrSize);
        bold = findViewById(R.id.bold);
        underline = findViewById(R.id.underline);
        italic = findViewById(R.id.italic);

        currentSize = editText.getTextSize();

        size.setText(currentSize + "");

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveText(view);
            }
        });

        bold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*
                * TODO: Remove highlight from italic button if exists
                *
                * */
                italic.setBackgroundColor(getResources().getColor(R.color.purple_500));

                if(editText.getTypeface().isBold()) {
                    editText.setTypeface(Typeface.DEFAULT);
                    bold.setBackgroundColor(getResources().getColor(R.color.purple_500));
                }else {
                    editText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    //Highlight bold button
                    bold.setBackgroundColor(getResources().getColor(R.color.teal_700));
                }
            }
        });
        italic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bold.setBackgroundColor(getResources().getColor(R.color.purple_500));

                if(editText.getTypeface().isItalic()) {
                    editText.setTypeface(Typeface.DEFAULT);
                    italic.setBackgroundColor(getResources().getColor(R.color.purple_500));
                }else {
                    editText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    //Highlight bold button
                    italic.setBackgroundColor(getResources().getColor(R.color.teal_700));
                }

            }
        });
        underline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                /*if(editText.getPaintFlags() == 0) {
                    editText.setTypeface(Typeface.DEFAULT);
                    italic.setBackgroundColor(getResources().getColor(R.color.purple_500));
                }else {
                    editText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    //Highlight bold button
                    italic.setBackgroundColor(getResources().getColor(R.color.teal_700));
                }*/
            }
        });
        incrSize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                currentSize++;
                editText.setTextSize(currentSize);
                size.setText(currentSize+"");
            }
        });
        descrSize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                currentSize--;
                editText.setTextSize(currentSize);
                size.setText(currentSize+"");
            }
        });
    }

    public void saveText(View view) {
        Log.d("STICKY", "saving...");
        StickyNote.setSticky(this, editText.getText().toString());
        updateWidget();
        Toast.makeText(this, "Note has been saved", Toast.LENGTH_SHORT).show();
    }

    private void updateWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget);
        ComponentName widget = new ComponentName(this, AppWidget.class);
        remoteViews.setTextViewText(R.id.widgetText, editText.getText().toString());
        appWidgetManager.updateAppWidget(widget, remoteViews);
    }
}