package com.example.msystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ExRate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_rate);
        EditText e = findViewById(R.id.edit2);
        TextView info = findViewById(R.id.info);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        HttpPost httpPost = new HttpPost("http://www.cbr.ru/scripts/XML_daily.asp");
        HttpClient client = HttpClientBuilder.create().build();
        String result = "";
        Document document = null;
        try {
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "windows-1251"));
                String line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                result = result.substring(45);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(result));
                document = builder.parse(is);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Button b = findViewById(R.id.b);
        Document finalDocument = document;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NodeList nameList = finalDocument.getElementsByTagName("Name");
                NodeList valueList = finalDocument.getElementsByTagName("Value");
                ArrayList Names = new ArrayList<String>();
                ArrayList Values = new ArrayList<String>();

                for (int i = 0; i < nameList.getLength(); i++) {
                    Names.add(nameList.item(i).getTextContent());
                    //Values.add(valueList.item(i).getTextContent());
                    Double a = Double.parseDouble(e.getText().toString())/Double.parseDouble(valueList.item(i).getTextContent().replace(",", "."));
                    int b = (int) Math.floor(a);
                    //int a = 100/Integer.parseInt(valueList.item(i).getTextContent());
                    Values.add(String.valueOf(b));
                }


                ListView listView = findViewById(R.id.listView);
                ListAdapter adapter = new ListAdapter(ExRate.this, R.layout.rate, Names, Values);
                listView.setAdapter(adapter);
            }
        });
    }


    private class ListAdapter extends ArrayAdapter {

        private int resourceLayout;
        private Context mContext;
        private ArrayList<String> Names;
        private ArrayList<String> Values;


        public ListAdapter(Context context, int resource, ArrayList<String> _Names, ArrayList<String> _Values) {
            super(context, resource, _Names);
            this.resourceLayout = resource;
            this.mContext = context;
            this.Names = _Names;
            this.Values = _Values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(mContext);
                v = vi.inflate(resourceLayout, null);
            }
            TextView Name = v.findViewById(R.id.Name);
            TextView Value = v.findViewById(R.id.Value);

            Name.setText(String.valueOf(Names.get(position)));
            Value.setText(String.valueOf(Values.get(position)));

            return v;
        }
    }

}