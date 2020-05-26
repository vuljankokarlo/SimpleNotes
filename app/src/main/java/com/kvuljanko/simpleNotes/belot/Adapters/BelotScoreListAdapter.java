package com.kvuljanko.simpleNotes.belot.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kvuljanko.simpleNotes.belot.DataClass.BelotScore;
import com.kvuljanko.simpleNotes.belot.R;

import java.util.List;

public class BelotScoreListAdapter extends ArrayAdapter<BelotScore> {

    private int resourceLayout;
    private Context mContext;
    private List<BelotScore> listOfScores;

    public BelotScoreListAdapter(Context context, int resource, List<BelotScore> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        listOfScores = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/typeFont.ttf");

        TextView score1 = v.findViewById(R.id.belotScoreListItem1);
        TextView score2 = v.findViewById(R.id.belotScoreListItem2);

        score1.setTypeface(typeface);
        score2.setTypeface(typeface);

        if (listOfScores.get(position).getScore1() == -1 || listOfScores.get(position).getScore2() == -1)
        {
            score1.setText("");
            score2.setText("");
        }
        else
        {
            score1.setText(String.valueOf(listOfScores.get(position).getTotalScore1()));
            score2.setText(String.valueOf(listOfScores.get(position).getTotalScore2()));
        }
        return v;
    }

}