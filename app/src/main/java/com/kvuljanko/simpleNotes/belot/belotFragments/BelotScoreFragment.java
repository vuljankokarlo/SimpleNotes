package com.kvuljanko.simpleNotes.belot.belotFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kvuljanko.simpleNotes.belot.Adapters.BelotScoreListAdapter;
import com.kvuljanko.simpleNotes.belot.DataClass.BelotGame;
import com.kvuljanko.simpleNotes.belot.DataClass.BelotScore;
import com.kvuljanko.simpleNotes.belot.DataClass.ConfigData;
import com.kvuljanko.simpleNotes.belot.OnFragmentInteractionListener;
import com.kvuljanko.simpleNotes.belot.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BelotScoreFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<BelotScore> listOfBelotScores = new ArrayList<>();
    BelotScoreListAdapter belotScoreListAdapter;
    private BelotGame belotGame = new BelotGame();
    private File file;
    private BelotScore returnScore;
    private int suma1 = 0;
    private int suma2 = 0;
    private int poz = 0;
    private int game13_1 = 0;
    private int game13_2 = 0;

    public TextView belotDealerName;

    private ConfigData configData;

    public BelotScoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_belot_score, container, false);

        file = getContext().getFileStreamPath("belotScore");
        if(file == null || !file.exists()) {
            file = new File(getContext().getFilesDir(), "belotScore");
        }

        file = getContext().getFileStreamPath("cfg");
        if(file == null || !file.exists()) {
            file = new File(getContext().getFilesDir(), "cfg");
        }

        belotGame.setListOfBelotScores(listOfBelotScores);

        loadGame();
        loadCfg();

        ArrayList<String> listOfDealerNames = new ArrayList<>();
        listOfDealerNames.add(configData.getBelotName1());
        listOfDealerNames.add(configData.getBelotName2());
        listOfDealerNames.add(configData.getBelotName3());
        listOfDealerNames.add(configData.getBelotName4());

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/typeFont.ttf");

        ListView belotScoreListView = view.findViewById(R.id.belotScoreList);
        Button addNew = view.findViewById(R.id.belotNewScore);

        TextView total1 = view.findViewById(R.id.belotTotal1);
        TextView total2 = view.findViewById(R.id.belotTotal2);
        TextView score1 = view.findViewById(R.id.belotScore1);
        TextView score2 = view.findViewById(R.id.belotScore2);
        TextView mi = view.findViewById(R.id.mi);
        TextView vi = view.findViewById(R.id.vi);
        TextView ukupno = view.findViewById(R.id.ukupno);
        TextView belotDealer = view.findViewById(R.id.belotDealer);
        belotDealerName = view.findViewById(R.id.belotDealerName);

        total1.setTypeface(typeface);
        total2.setTypeface(typeface);
        score1.setTypeface(typeface);
        score2.setTypeface(typeface);
        mi.setTypeface(typeface);
        vi.setTypeface(typeface);
        ukupno.setTypeface(typeface);
        belotDealer.setTypeface(typeface);
        belotDealerName.setTypeface(typeface);
        
        

        belotScoreListAdapter = new BelotScoreListAdapter(getContext(), R.layout.belot_score_list_item, listOfBelotScores);
        belotScoreListView.setAdapter(belotScoreListAdapter);

        populateList();

        if (getArguments() != null) {
            game13_1 = 0;
            game13_2 = 0;
            returnScore = (BelotScore) getArguments().getSerializable("returnGame");
            poz = getArguments().getInt("pozicija");
            if (listOfBelotScores.get(listOfBelotScores.size()-1).getScore1() >= 0) {
                game13_1 = returnScore.getTotalScore1();
                game13_2 = returnScore.getTotalScore2();
            } else {
                if (poz != -1) {
                    listOfBelotScores.get(poz).setScore1(returnScore.getScore1());
                    listOfBelotScores.get(poz).setScore2(returnScore.getScore2());
                    listOfBelotScores.get(poz).setAdditional1(returnScore.getAdditional1());
                    listOfBelotScores.get(poz).setAdditional2(returnScore.getAdditional2());
                } else {
                    for (BelotScore belotScore : listOfBelotScores) {
                        if (belotScore.getScore1() < 0) {
                            belotScore.setScore1(returnScore.getScore1());
                            belotScore.setScore2(returnScore.getScore2());
                            belotScore.setAdditional1(returnScore.getAdditional1());
                            belotScore.setAdditional2(returnScore.getAdditional2());
                            break;
                        }
                    }
                    if (belotGame.getDealer() < 3) {
                        belotGame.setDealer(belotGame.getDealer()+1);
                    } else {
                        belotGame.setDealer(0);
                    }
                    belotDealerName.setText(listOfDealerNames.get(belotGame.getDealer()));
                }
            }
            calculateResult();
            saveGame();
        }
        if (belotGame.getTotal1() > 0) {
            total1.setText(String.valueOf(belotGame.getTotal1()));
        } else {
            total1.setText("0");
        }
        if (belotGame.getTotal2() > 0) {
            total2.setText(String.valueOf(belotGame.getTotal2()));
        } else {
            total2.setText("0");
        }

        int summOfScores1 = 0;
        int summOfScores2 = 0;
        for (BelotScore belotScore: listOfBelotScores) {
            if (belotScore.getTotalScore1() >= 0 && belotScore.getTotalScore2() >= 0) {
                summOfScores1 += belotScore.getTotalScore1();
                summOfScores2 += belotScore.getTotalScore2();
            }
        }

        if (summOfScores1 > 0 || summOfScores2 > 0) {
            score1.setText(String.valueOf(summOfScores1));
            score2.setText(String.valueOf(summOfScores2));
        } else {
            score1.setText("0");
            score2.setText("0");
        }

        if (summOfScores1 == 0 && summOfScores2 == 0 && belotGame.getTotal1() == 0 && belotGame.getTotal2() == 0) {
            delaerSelect();
        } else {
            belotDealerName.setText(listOfDealerNames.get(belotGame.getDealer()));
        }
        
        belotScoreListAdapter.notifyDataSetChanged();

        belotScoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listOfBelotScores.get(position).getScore1() != -1 || listOfBelotScores.get(position).getScore2() != -1)
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", listOfBelotScores.get(position));
                    bundle.putInt("pozicija", position);
                    BelotAddScoreFragment belotAddScoreFragment= new BelotAddScoreFragment();
                    belotAddScoreFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in, R.anim.fragment_out)
                            .replace(R.id.fragmentContainer, belotAddScoreFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BelotAddScoreFragment belotAddScoreFragment= new BelotAddScoreFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in, R.anim.fragment_out)
                        .replace(R.id.fragmentContainer, belotAddScoreFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void loadGame(){
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("belotScore");
            ObjectInputStream is = new ObjectInputStream(fis);
            belotGame = (BelotGame) is.readObject();
            is.close();
            fis.close();
            listOfBelotScores = belotGame.getListOfBelotScores();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveGame() {
        FileOutputStream fos = null;
        try {
            fos = getContext().openFileOutput("belotScore", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(belotGame);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateResult() {
        suma1 = game13_1;
        suma2 = game13_2;
        for (BelotScore belotScore : listOfBelotScores) {
            if (belotScore.getTotalScore1() >= 0 && belotScore.getTotalScore2() >= 0) {
                suma1 += belotScore.getTotalScore1();
                suma2 += belotScore.getTotalScore2();
            }
        }
        if (suma1 > 1000 && suma2 > 1000) {
            if (suma1 > suma2) {
                belotGame.setTotal1(belotGame.getTotal1() + 1);
                belotGame.getListOfBelotScores().clear();
                populateList();
                belotScoreListAdapter.notifyDataSetChanged();
                newGameAlert();
                if (belotGame.getFirstDealer() < 3) {
                    belotGame.setDealer(belotGame.getFirstDealer() + 1);
                    belotGame.setFirstDealer(belotGame.getFirstDealer() + 1);
                } else {
                    belotGame.setDealer(0);
                    belotGame.setFirstDealer(0);
                }
            } else {
                belotGame.setTotal2(belotGame.getTotal2() + 1);
                belotGame.getListOfBelotScores().clear();
                populateList();
                belotScoreListAdapter.notifyDataSetChanged();
                newGameAlert();
                if (belotGame.getFirstDealer() < 3) {
                    belotGame.setDealer(belotGame.getFirstDealer() + 1);
                    belotGame.setFirstDealer(belotGame.getFirstDealer() + 1);
                } else {
                    belotGame.setDealer(0);
                    belotGame.setFirstDealer(0);
                }
            }
        } else {
            if (suma1 > 1000) {
                belotGame.setTotal1(belotGame.getTotal1() + 1);
                belotGame.getListOfBelotScores().clear();
                populateList();
                belotScoreListAdapter.notifyDataSetChanged();
                newGameAlert();
                if (belotGame.getFirstDealer() < 3) {
                    belotGame.setDealer(belotGame.getFirstDealer() + 1);
                    belotGame.setFirstDealer(belotGame.getFirstDealer() + 1);
                } else {
                    belotGame.setDealer(0);
                    belotGame.setFirstDealer(0);
                }
            }
            if (suma2 > 1000){
                belotGame.setTotal2(belotGame.getTotal2() + 1);
                belotGame.getListOfBelotScores().clear();
                populateList();
                belotScoreListAdapter.notifyDataSetChanged();
                newGameAlert();
                if (belotGame.getFirstDealer() < 3) {
                    belotGame.setDealer(belotGame.getFirstDealer() + 1);
                    belotGame.setFirstDealer(belotGame.getFirstDealer() + 1);
                } else {
                    belotGame.setDealer(0);
                    belotGame.setFirstDealer(0);
                }
            }
        }
    }

    public void populateList() {
        if (listOfBelotScores == null){
            listOfBelotScores = new ArrayList<>();
        }
        while (listOfBelotScores.size() != 12)
        {
            listOfBelotScores.add(new BelotScore(-1,-1,-1,-1));
        }
    }

    public void newGameAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        final EditText et = new EditText(getContext());
        et.setKeyListener(null);
        et.setText("MI: " + suma1 + "\n" + "VI: " + suma2);
        alertDialogBuilder.setView(et);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void delaerSelect() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose dealer");
        builder.setItems(new CharSequence[]
                        {configData.getBelotName1(), configData.getBelotName2(), configData.getBelotName3(), configData.getBelotName4()},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                belotDealerName.setText(configData.getBelotName1());
                                belotGame.setDealer(0);
                                belotGame.setFirstDealer(0);
                                saveGame();
                                break;
                            case 1:
                                belotDealerName.setText(configData.getBelotName2());
                                belotGame.setDealer(1);
                                belotGame.setFirstDealer(1);
                                saveGame();
                                break;
                            case 2:
                                belotDealerName.setText(configData.getBelotName3());
                                belotGame.setDealer(2);
                                belotGame.setFirstDealer(2);
                                saveGame();
                                break;
                            case 3:
                                belotDealerName.setText(configData.getBelotName4());
                                belotGame.setDealer(3);
                                belotGame.setFirstDealer(3);
                                saveGame();
                                break;
                        }
                    }
                });
        builder.setCancelable(false);
        builder.create().show();
    }

    public void loadCfg(){
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("cfg");
            ObjectInputStream is = new ObjectInputStream(fis);
            configData = (ConfigData) is.readObject();
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
