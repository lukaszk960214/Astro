package com.example.astroweatherapp.Fragments;

import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.astroweatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PageFragment3 extends Fragment implements Runnable {

    private EditText editText1, editText2;
    private TextView simpleWatch;
    private Button button;
    OnMessageReadListener messageReadListener;

    private int hour;
    private int minute;
    private int second;
    private String timeStr;
    public Boolean flaga;

    boolean run = true;
    Handler handler = new Handler();

    public void toast (CharSequence text) {
        Context context = getActivity().getApplicationContext();
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public PageFragment3() {
    }

    public void timer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(500);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();
                                hour = c.get(Calendar.HOUR_OF_DAY);
                                if (hour > 12){
                                    hour = hour - 12;
                                }
                                minute = c.get(Calendar.MINUTE);
                                second = c.get(Calendar.SECOND);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

                                Date date = c.getTime();
                                timeStr = simpleDateFormat.format(date);
                                simpleWatch.setText(timeStr);
                            }
                        });
                    }
                    catch (Exception ex) {
                        simpleWatch.setText(ex.getMessage());
                        flaga=true;
                    }
                }
            }
        }).start();
    }

    @Override
    public void run() {

    }

    public interface OnMessageReadListener{
        public void onMessageRead(String message);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_3,container,false);

        editText1 = view.findViewById(R.id.longitudeMessage);
        editText2 = view.findViewById(R.id.latitudeMessage);

        button = view.findViewById(R.id.buttonSubmit);

        simpleWatch = view.findViewById(R.id.simpleWatch);
        timer();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText1.getText().length() == 0 || editText2.getText().length() == 0){
                    toast("Please provide data!");
                    editText1.setText("");
                    editText2.setText("");
                }
                else if (String.valueOf(editText1).matches("-?\\d+(\\.\\d+)?") || String.valueOf(editText2).matches("-?\\d+(\\.\\d+)?") || Double.valueOf(String.valueOf(editText1.getText())) > 90.0 || Double.valueOf(String.valueOf(editText1.getText())) < -90.0  || Double.valueOf(String.valueOf(editText2.getText())) > 90.0 || Double.valueOf(String.valueOf(editText2.getText())) < -90.0)
                {
                    toast("Wrong data provided!");
                    editText1.setText("");
                    editText2.setText("");
                }
                else
                {
                    String message = editText1.getText().toString() + "/" + editText2.getText().toString();
                    messageReadListener.onMessageRead(message);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity)context;

        try {
            messageReadListener = (OnMessageReadListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + "must override");
        }
    }
}