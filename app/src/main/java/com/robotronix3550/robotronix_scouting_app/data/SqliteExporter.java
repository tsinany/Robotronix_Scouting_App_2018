package com.robotronix3550.robotronix_scouting_app.data;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;

/**
 * Can export an sqlite databse into a csv file.
 *
 * The file has on the top dbVersion and on top of each table data the name of the table
 *
 * Inspired by
 * https://stackoverflow.com/questions/31367270/exporting-sqlite-database-to-csv-file-in-android
 * and some other SO threads as well.
 *
 */
public class SqliteExporter {
    private static final String TAG = SqliteExporter.class.getSimpleName();

    public static final String DB_SCOUTING_DB_VERSION_KEY = "dbVersion";
    public static final String DB_SCOUTING_TABLE_NAME = "table";

    public static String export(SQLiteDatabase db) throws IOException{
        if( !FileUtils.isExternalStorageWritable() ){
            throw new IOException("Cannot write to external storage");
        }
        // File scoutingDir = FileUtils.createDirIfNotExist(FileUtils.getAppDir() + "/scouting");
        File DocumentDir = FileUtils.getDocumentDir("Scouting");
        String fileName = FileUtils.createScoutingFileName();
        File scoutingFile = new File(DocumentDir, fileName);
        boolean success = scoutingFile.createNewFile();
        if(!success){
            boolean writable = scoutingFile.canWrite();
            if(!writable ) {
                throw new IOException("Failed to create the scouting file");
            }
        }

        List<String> tables = getTablesOnDataBase(db);
        Log.d(TAG, "Started to fill the scouting file in " + scoutingFile.getAbsolutePath());
        long starTime = System.currentTimeMillis();
        writeCsv(scoutingFile, db, tables);
        long endTime = System.currentTimeMillis();
        Log.d(TAG, "Creating scouting took " + (endTime - starTime) + "ms.");

        return scoutingFile.getAbsolutePath();
    }

    /**
     * Get all the table names we have in db
     *
     * @param db
     * @return
     */
    public static List<String> getTablesOnDataBase(SQLiteDatabase db){
        String[] column = null;  // return all columns
        String selection = null; // return all rows
        String[] selectionArgs = null; // not used
        String sortOrder = null;  // unordered
        Cursor c = null;
        List<String> tables = new ArrayList<>();
        try{
            // c = db.rawQuery("SELECT name FROM scouts WHERE type='table'", null);
            c = db.query(ScoutEntry.TABLE_NAME, column, selection, selectionArgs, null, null, sortOrder);
            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {
                    tables.add(c.getString(0));
                    c.moveToNext();

                }
            }
        }
        catch(Exception throwable){
            Log.e(TAG, "Could not get the table names from db", throwable);
        }
        finally{
            if(c!=null)
                c.close();
        }
        return tables;
    }

    private static void writeCsv(File scoutingFile, SQLiteDatabase db, List<String> tables){
        String[] column = null;  // return all columns
        String selection = null; // return all rows
        String[] selectionArgs = null; // not used
        String sortOrder = null;  // unordered
        int columns;
        String[] columnArr;

        CSVWriter csvWrite = null;
        Cursor curCSV = null;
        try {
            csvWrite = new CSVWriter(new FileWriter(scoutingFile));
            // writeSingleValue(csvWrite, DB_SCOUTING_DB_VERSION_KEY + "=" + db.getVersion());

            // Write column names
            //
            curCSV = db.query(ScoutEntry.TABLE_NAME, column, selection, selectionArgs, null, null, sortOrder);
            columns = curCSV.getColumnCount();
            csvWrite.writeNext(curCSV.getColumnNames());

            // Write each row values
            //for(String table: tables){
            while(curCSV.moveToNext()) {
                //selection = ScoutEntry._ID + "=" + table;
                // selectionArgs = new String[] {table};
                // curCSV = db.query(ScoutEntry.TABLE_NAME, column, selection, selectionArgs, null, null, sortOrder);
                columnArr = new String[columns];
                // get column values of that row
                for( int i = 0; i < columns; i++){
                    columnArr[i] = curCSV.getString(i);
                    // Log.d(TAG, "filling column arr with " + columnArr[i]);
                }
                csvWrite.writeNext(columnArr);
                // Log.d(TAG, "filling csv file with row: " + curCSV.getPosition() + " with " + columnArr);
            }
        }
        catch(Exception sqlEx) {
            Log.e(TAG, sqlEx.getMessage(), sqlEx);
        }finally {
            if(csvWrite != null){
                try {
                    csvWrite.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( curCSV != null ){
                curCSV.close();
            }
        }
    }

    private static void writeSingleValue(CSVWriter writer, String value){
        writer.writeNext(new String[]{value});
    }
}
