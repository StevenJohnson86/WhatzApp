package sjohnsoncf.whatzapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by steven on 10/21/17.
 */

public class TitleAdapter extends BaseAdapter {
    private ArrayList<String> titles;
    private Context ctx;

    public TitleAdapter(Context context, ArrayList<String> convoTitles){
        this.titles = convoTitles;
        this.ctx = context;
    }
    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int i) {
        return titles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
        //not used
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //apparently I'm not using recycled views properly...
//        if(view == null) {
//            view = LayoutInflater.from(ctx).inflate(R.layout.convo_item, viewGroup);
//        }
        View newView = LayoutInflater.from(ctx).inflate(R.layout.convo_item, null);
        TextView newTitle = newView.findViewById(R.id.convoItem_title);
        newTitle.setText(getItem(i).toString());
        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goto convo detail view
                Intent convoDetailIntent = new Intent(ctx, ConvoDetailActivity.class);
                //shouldn't something be passed from this Item to convoDetailActy??
                ctx.startActivity(convoDetailIntent);
            }
        });
        return newView;
    }
}
