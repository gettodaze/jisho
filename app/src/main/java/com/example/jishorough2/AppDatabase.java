package com.example.jishorough2;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Entry.class}, version=1)
@TypeConverters({JSONParse.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract  EntryDao entryDao();
    private static AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static synchronized AppDatabase getDBInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "entryDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(dbCallback)
                    .build();

        }
        return INSTANCE;
    }

    private static AppDatabase.Callback dbCallback = new AppDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private EntryDao entryDao;

        private PopulateDbAsyncTask(AppDatabase db){
            entryDao = db.entryDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<String[]> wordReadingList = new ArrayList<>();
            wordReadingList.add(new String[]{"例", "れい"});
            Entry e = new Entry(wordReadingList, "definition");
            e.setListName("favorites");
            entryDao.insert(e);
            return null;
        }
    }
}
