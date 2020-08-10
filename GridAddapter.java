package com.example.griview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class GridAddapter extends BaseAdapter {
    private int icons[];
    private ArrayList<String> letters;
    private Context context;
    private LayoutInflater inflater;

    public GridAddapter( Context context, int icons[], ArrayList<String> letters) {
        this.context = context;
        this.icons = icons;
        this.letters = letters;
    }

    @Override
    public int getCount() {
        return letters.size();
    }

    @Override
    public Object getItem(int i) {
        return letters.get(i);
    }

    public long getItemId(int i) {
        return letters.indexOf(i);
    }

    @Override
    public View getView(final int i, View convertview, ViewGroup viewGroup) {
        View gridView=convertview;

        if (convertview==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView=inflater.inflate(R.layout.custom_layout,null);
        }
        ImageView icon=(ImageView) gridView.findViewById(R.id.icons);
        final TextView letter=gridView.findViewById(R.id.letters);
        final ImageButton button=gridView.findViewById(R.id.closeButton);
        final ImageButton minimise=gridView.findViewById(R.id.minimiseButton);
        final CardView cardView=gridView.findViewById(R.id.cardview);

        icon.setImageResource(icons[i]);
        letter.setText(letters.get(i));
        button.setVisibility(View.INVISIBLE);
        minimise.setVisibility(View.INVISIBLE);

        //button.setOnClickListener(new View.OnClickListener() {
           // @Override
        //    public void onClick(View view) {

          //      ViewGroup layout=(ViewGroup) cardView.getParent();
            //    if(null!=layout)
              //      layout.removeView(cardView);
                //button.setVisibility(View.INVISIBLE);
            //minimise.setVisibility(View.INVISIBLE);
            //}
        //});

        minimise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.INVISIBLE);
                minimise.setVisibility(View.INVISIBLE);
            }
        });
        return gridView;
    }
    public int getItemPosition(int i){
        return letters.indexOf(i);
    }
}