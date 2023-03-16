package com.mylogic.diabbook.data;

import android.provider.BaseColumns;

public class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Glucose_Data";
        public static final String COLUMN_NAME1_TITLE = "Date";
        public static final String COLUMN_NAME2_TITLE = "Time";
        public static final String COLUMN_NAME3_TITLE = "Meal_Status";
        public static final String COLUMN_NAME4_TITLE = "Food_Status";
        public static final String COLUMN_NAME5_TITLE = "Glucose_reading";
    }
}
