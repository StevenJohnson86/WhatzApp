package sjohnsoncf.whatzapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Convo;

/**
 * Created by steven on 10/21/17.
 */

public class TitleAdapter extends BaseAdapter {
    private ArrayList<Convo> convos;
    private Context ctx;

    public TitleAdapter(Context context, ArrayList<Convo> convoTitles){
        this.convos = convoTitles;
        this.ctx = context;
    }
    @Override
    public int getCount() {
        return convos.size();
    }

    @Override
    public Object getItem(int i) {
        return convos.get(i).getmTitle();
    }

    @Override
    public long getItemId(int i) {
//        Log.d("TitleAdapter", "getItemId: LN.41:  " + convos.get(i).getmIdHash());
        return Long.valueOf(convos.get(i).getmIdHash());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int id = i;
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
//                String hashId = String.valueOf(getItemId(id));
                Convo.mCurrentConvoHash = String.valueOf(getItemId(id));
                Intent convoDetailIntent = new Intent(ctx, ConvoDetailActivity.class);
//                convoDetailIntent.set
                //shouldn't something be passed from this Item to convoDetailActy??
                ctx.startActivity(convoDetailIntent);
            }
        });
        return newView;
    }
}
