package com.mylogic.diabbook.ui.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mylogic.diabbook.ui.adapter.GlucoseListAdapter;
import com.mylogic.diabbook.data.FeedReaderContract;
import com.mylogic.diabbook.data.FeedReaderDbHelper;
import com.mylogic.diabbook.data.ModelGlucoseData;
import com.mylogic.diabbook.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.mylogic.diabbook.data.FeedReaderContract.FeedEntry.TABLE_NAME;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        List<ModelGlucoseData> templist=new ArrayList<>();

        //SQL reading
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getContext());
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            templist.add(new ModelGlucoseData(
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME1_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME2_TITLE)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME3_TITLE)),
                                            cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME4_TITLE)),
                                                    cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME5_TITLE))));
                  }

        if(templist.size()==0)
        {
            templist.add(new ModelGlucoseData("","","","","",""));
        }

        GlucoseListAdapter adapter = new GlucoseListAdapter(templist);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(),DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = root.findViewById(R.id.btnShare);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    exportExcelData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        return root;
    }
    private void exportExcelData() throws IOException {
        // Open the database for reading

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        File csvFile = new File(getContext().getExternalFilesDir(null), "data.csv");
        FileWriter writer = new FileWriter(csvFile);

        // Write column headers
        String[] headers = cursor.getColumnNames();
        for (int i = 0; i < headers.length; i++) {
            writer.append(headers[i]);
            if (i < headers.length - 1) {
                writer.append(",");
            }
        }
        writer.append("\n");

        // Write row data
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                writer.append(cursor.getString(i));
                if (i < cursor.getColumnCount() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        }

        // Toast.makeText(getContext(),csvFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
        // Close the writer and cursor
        writer.flush();
        writer.close();
        cursor.close();
// Get the FileProvider authority
        String authority = getContext().getPackageName() + ".fileprovider";

// Create the content Uri using the FileProvider
        Uri contentUri = FileProvider.getUriForFile(getContext(), authority, csvFile);

// Create the intent to share the file
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// Create a chooser dialog for sharing
        Intent chooser = Intent.createChooser(intent, "Share file");

// Verify that the intent will resolve to an activity
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(chooser);
        }

    }
}