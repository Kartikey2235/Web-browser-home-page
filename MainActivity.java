package com.example.griview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText websiteName;
    private EditText copyLink;
    private EditText search;
    private ImageButton searchButton;
    private LayoutInflater inflater;
    GridView gridView;
    private ArrayList<String> nameString;
    private ArrayList<String> linkString;
    private int iconString[]={R.drawable.facebook, R.drawable.google,R.drawable.instagram,R.drawable.wikipedia,
            R.drawable.snapchat,R.drawable.twitter,R.drawable.one,R.drawable.two,R.drawable.three,
            R.drawable.four,R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,
            R.drawable.nine,R.drawable.ten};
    private ArrayList<Integer> idString;
    private  DatabaseHandler db;
    GridAddapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= new DatabaseHandler(MainActivity.this);
        gridView = findViewById(R.id.gridview);
        search=findViewById(R.id.editTextTextPersonName);
        searchButton=findViewById(R.id.imageButton);
        nameString=new ArrayList<>();
        linkString=new ArrayList<>();
        idString=new ArrayList<>();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(gridView.getCount()==0){
//                    Log.d("kartik", "onClick: "+gridView.getCount());
//                    add();
//
//                }
//                else{
                createPopupDialog();
            }
        });

  List<Website> detailList = db.getAllDetails();

        for(Website website: detailList){
            nameString.add(website.getName());
            linkString.add(website.getLink());
            idString.add(website.getId());
        }


        if (detailList.size()<6){
            add();
        }

          adapter = new GridAddapter(MainActivity.this, iconString, nameString);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("link",linkString.get(i));
                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int postion=idString.get(i);
                Log.d("main", "onItemLongClick: "+postion);
                button(view,postion);
                return true;
            }
        });
    }

    private  void search(){
        String query=search.getText().toString().trim();
        String url="https://www.google.com/search?q="+query;
        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("link",url);
        startActivity(intent);

    }
    private void saveItem(View view) {
        Website website=new Website();

        String newWebsite= websiteName.getText().toString().trim();
        String newWebsiteLink=copyLink.getText().toString().trim();

        website.setName(newWebsite);
        website.setLink(newWebsiteLink);

        db.addDetails(website);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        },1200);
    }

    private void createPopupDialog() {
        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);

        websiteName=view.findViewById(R.id.websiteName);
        copyLink=view.findViewById(R.id.websiteLink);
        saveButton=view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!websiteName.getText().toString().isEmpty() &&
                        !copyLink.getText().toString().isEmpty()){
                    saveItem(view);
                }else{
                    Snackbar.make(view,"Empty Fields not Allowed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view);
        dialog=builder.create();
        dialog.show();
    }
    public void button(View view, final int i){
        View buttonView=view;
        if (view==null){
            inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            buttonView=inflater.inflate(R.layout.custom_layout,null);
        }
        final ImageButton button=buttonView.findViewById(R.id.closeButton);
        final ImageButton minimise=buttonView.findViewById(R.id.minimiseButton);
        final CardView cardView=buttonView.findViewById(R.id.cardview);

        button.setVisibility(View.VISIBLE);
        minimise.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Website website=db.getDetails(i);
                if(i<=6){
                        Snackbar.make(view, "You can not delete this shortcut", Snackbar.LENGTH_SHORT).show();
                        button.setVisibility(View.INVISIBLE);
                        minimise.setVisibility(View.INVISIBLE);
                }
                else if(i<16){
                if (button.getVisibility()==View.VISIBLE){
                    ViewGroup layout=(ViewGroup) cardView.getParent();
                    if(null!=layout)
                        layout.removeView(cardView);
                    db.deleteContact(website);
                    Log.d("kar", "onClick: "+website.getId());
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
                else if(i>=16){
                    Snackbar.make(view,"You can not add more websites", Snackbar.LENGTH_SHORT).show();
                }
                }
            }
        });
    }
    public void add(){
        db.addDetails(new Website("Facebook", "https://www.facebook.com/"));
        db.addDetails(new Website("Google", "https://www.google.com/"));
        db.addDetails(new Website("Instagram", "https://www.instagram.com/"));
        db.addDetails(new Website("Wikipedia", "https://www.wikipedia.org/"));
        db.addDetails(new Website("Snapchat", "https://www.snapchat.com/"));
        db.addDetails(new Website("Twitter", "https://www.twitter.com/"));
    }
}