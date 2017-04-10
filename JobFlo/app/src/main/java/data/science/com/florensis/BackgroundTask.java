package data.science.com.florensis;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import data.science.com.florensis.Models.Database;



public class BackgroundTask extends AsyncTask<String, Data,String>{

    Context ctx;
    DataAdapter dataadapter;
    Activity activity;
    ListView listview;

    BackgroundTask(Context ctx){
        this.ctx =ctx;
        activity = (Activity) ctx;
    }
    @Override
    protected void onPostExecute(String result) {

        if (result.equals("get_info")){
            listview.setAdapter(dataadapter);
        }else{
        Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://datascience.co.ke/AntoTest/florensisV1/init.php";

        String method = params[0];
        Database database = new Database(ctx);
        if(method.equals("add_info")){
            String greenhouse = params[1];
            String bedID = params[2];

            SQLiteDatabase db = database.getWritableDatabase();
            database.addInformations(db,greenhouse,bedID);
            return "One row Inserted";
        }else if (method.equals("get_info")){
            listview = (ListView) activity.findViewById(R.id.display_listview);
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = database.getInformations(db);
            dataadapter = new DataAdapter(ctx, R.layout.activity_view);
            String greenhouse,bedID;
            while (cursor.moveToNext()){
                greenhouse = cursor.getString(cursor.getColumnIndex(DataContract.productEntry.GREENHOUSENAME));
                bedID = cursor.getString(cursor.getColumnIndex(DataContract.productEntry.BEDID));
                Data data = new Data(greenhouse,bedID);
                publishProgress(data);
            }

            return "get_info";
        }else if (method.equals("save")){
            String greenhouse = params[1];
            String bed = params[2];

            try {
                URL url = new URL(reg_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                String data = URLEncoder.encode("greenhouse","UTF-8") +"="+ URLEncoder.encode(greenhouse, "UTF-8")+ "&"+
                        URLEncoder.encode("bed","UTF-8") +"="+ URLEncoder.encode(bed, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                return "Data received Thank you...";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Data... values) {
        dataadapter.add(values[0]);
    }


}
