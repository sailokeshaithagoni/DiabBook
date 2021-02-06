package com.mylogic.diabbook.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mylogic.diabbook.MainActivity;
import com.mylogic.diabbook.data.FeedReaderContract;
import com.mylogic.diabbook.data.FeedReaderDbHelper;
import com.mylogic.diabbook.data.ModelGlucoseData;
import com.mylogic.diabbook.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RadioGroup radioFoodGroup;
    private RadioButton radioFoodButton;
    private EditText editTextNumberGlucose;
    private Button btnAdd;
    private List<ModelGlucoseData> listModelGlucoseData;
    private SQLiteDatabase db;
    public int counter;
    private Notification notification;
    public NotificationManager notificationManager;
    private static final String TAG = HomeFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnAdd=root.findViewById(R.id.btnAdd);
        radioFoodGroup = (RadioGroup) root.findViewById(R.id.radioGroup);
        editTextNumberGlucose=root.findViewById(R.id.editTextNumberGlucose);
        listModelGlucoseData=new ArrayList<>();

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getContext());
        db= dbHelper.getWritableDatabase();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("HH:mm");
                // you can get seconds by adding  "...:ss" to it
                date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
                String localTime = date.format(currentLocalTime);

                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
                String formattedDate = df.format(currentLocalTime);

                int selectedId = radioFoodGroup.getCheckedRadioButtonId();
                radioFoodButton=(RadioButton)radioFoodGroup.findViewById(selectedId);
                ContentValues values = new ContentValues();
                if(radioFoodButton!=null)
                {
                    if(editTextNumberGlucose.getText().length()==0)
                    {
                        Toast.makeText(getContext(), "Please enter glucose reading", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME1_TITLE, formattedDate);
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME2_TITLE, localTime);
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME3_TITLE, radioFoodButton.getText().toString());

                        if(radioFoodButton.getText().length()==11)
                        {
                            new CountDownTimer(7200000,1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    //counttime.setText(String.valueOf(counter));
                                    counter++;
                                }
                                @Override
                                public void onFinish() {
                                    createNotification();
                                }
                            }.start();
                        }

                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME4_TITLE, editTextNumberGlucose.getText().toString());

                        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
                        Toast.makeText(getContext(),"Glucose reading added successfully",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Please select either before food or after food", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    public void createNotification()
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext(), "notify_001");
        Intent ii = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        //bigText.bigText("Something");
        bigText.setBigContentTitle("Time to measure your blood glucose level");
        //bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_blood_round);
        mBuilder.setContentTitle("Time to measure your blood glucose level");
        //mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        notificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        notificationManager.notify(0, mBuilder.build());
    }
}