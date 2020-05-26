package com.kvuljanko.simpleNotes.belot.belotFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kvuljanko.simpleNotes.belot.DataClass.BelotScore;
import com.kvuljanko.simpleNotes.belot.OnFragmentInteractionListener;
import com.kvuljanko.simpleNotes.belot.R;

public class BelotAddScoreFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BelotScore belotScore;
    private int igrac = 1;
    private int isIgra = 1;
    private int poz = -1;

    public BelotAddScoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_belot_add_score, container, false);

        final TextView score1 = view.findViewById(R.id.addScore1);
        final TextView score2 = view.findViewById(R.id.addScore2);
        final TextView addIgra = view.findViewById(R.id.addIgra);
        final TextView addZvanje = view.findViewById(R.id.addZvanja);
        TextView t0 = view.findViewById(R.id.add0);
        TextView t1 = view.findViewById(R.id.add1);
        TextView t2 = view.findViewById(R.id.add2);
        TextView t3 = view.findViewById(R.id.add3);
        TextView t4 = view.findViewById(R.id.add4);
        TextView t5 = view.findViewById(R.id.add5);
        TextView t6 = view.findViewById(R.id.add6);
        TextView t7 = view.findViewById(R.id.add7);
        TextView t8 = view.findViewById(R.id.add8);
        TextView t9 = view.findViewById(R.id.add9);
        TextView delete = view.findViewById(R.id.delete);
        TextView deleteAll = view.findViewById(R.id.deleteAll);
        TextView save = view.findViewById(R.id.OK);
        TextView cancel = view.findViewById(R.id.CANCEL);

        if (getArguments() != null) {
            belotScore = (BelotScore) getArguments().getSerializable("data");
            poz = getArguments().getInt("pozicija");
            score1.setText(String.valueOf(belotScore.getTotalScore1()));
            score2.setText(String.valueOf(belotScore.getTotalScore2()));
        } else {
            belotScore = new BelotScore(0, 0,0,0);
            score1.setText(String.valueOf(belotScore.getTotalScore1()));
            score2.setText(String.valueOf(belotScore.getTotalScore1()));
        }

        score1.setBackgroundColor(getResources().getColor(R.color.selected));
        addIgra.setBackgroundColor(getResources().getColor(R.color.selected));

        score1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score1.setBackgroundColor(getResources().getColor(R.color.selected));
                score2.setBackgroundColor(getResources().getColor(R.color.notSelected));
                igrac = 1;
            }
        });

        score2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score2.setBackgroundColor(getResources().getColor(R.color.selected));
                score1.setBackgroundColor(getResources().getColor(R.color.notSelected));
                igrac = 2;
            }
        });

        addIgra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIgra.setBackgroundColor(getResources().getColor(R.color.selected));
                addZvanje.setBackgroundColor(getResources().getColor(R.color.notSelected));
                isIgra = 1;
            }
        });

        addZvanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addZvanje.setBackgroundColor(getResources().getColor(R.color.selected));
                addIgra.setBackgroundColor(getResources().getColor(R.color.notSelected));
                isIgra = 0;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (belotScore.getScore1() == 0 && belotScore.getScore2() == 0) {
                    Toast.makeText(getContext(), "Unesite rezultat!", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("returnGame", belotScore);
                    bundle.putInt("pozicija", poz);
                    BelotScoreFragment belotScoreFragment= new BelotScoreFragment();
                    belotScoreFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in, R.anim.fragment_out)
                            .replace(R.id.fragmentContainer, belotScoreFragment)
                            .addToBackStack(null)
                            .commit();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BelotScoreFragment belotScoreFragment= new BelotScoreFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in, R.anim.fragment_out)
                        .replace(R.id.fragmentContainer, belotScoreFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score1.setText("0");
                score2.setText("0");
                belotScore.setScore1(0);
                belotScore.setScore2(0);
                belotScore.setAdditional1(0);
                belotScore.setAdditional2(0);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = 0;
                if (isIgra == 1) {
                    if (igrac == 1) {
                        if (belotScore.getScore1() != 0) {
                            temp = belotScore.getScore1();
                            temp = temp / 10;
                            belotScore.setScore1(temp);
                            score1.setText(String.valueOf(belotScore.getTotalScore1()));
                            if (162 - belotScore.getScore1() <= 0) {
                                belotScore.setScore2(0);
                                score2.setText(String.valueOf(belotScore.getTotalScore2()));
                            } else {
                                belotScore.setScore2(162 - belotScore.getScore1());
                                score2.setText(String.valueOf(belotScore.getTotalScore2()));
                            }
                        }
                    }
                    if (igrac == 2) {
                        if (belotScore.getScore2() != 0) {
                            temp = belotScore.getScore2();
                            temp = temp / 10;
                            belotScore.setScore2(temp);
                            score2.setText(String.valueOf(belotScore.getTotalScore2()));
                            if (162 - belotScore.getScore2() <= 0) {
                                belotScore.setScore1(0);
                                score1.setText(String.valueOf(belotScore.getTotalScore1()));
                            } else {
                                belotScore.setScore1(162 - belotScore.getScore2());
                                score1.setText(String.valueOf(belotScore.getTotalScore1()));
                            }
                        }
                    }
                }
                if (isIgra == 0) {
                    if (igrac == 1) {
                        if (belotScore.getAdditional1() != 0) {
                            temp = belotScore.getAdditional1();
                            temp = temp / 10;
                            belotScore.setAdditional1(temp);
                            score1.setText(String.valueOf(belotScore.getTotalScore1()));
                        }
                    }
                    if (igrac == 2) {
                        if (belotScore.getAdditional2() != 0) {
                            temp = belotScore.getAdditional2();
                            temp = temp / 10;
                            belotScore.setAdditional2(temp);
                            score2.setText(String.valueOf(belotScore.getTotalScore2()));
                        }
                    }
                }
            }
        });

        t0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "0");
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "1");
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "2");
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "3");
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "4");
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "5");
            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "6");
            }
        });

        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "7");
            }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "8");
            }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(score1, score2, "9");
            }
        });

        return view;
    }

    private void addNumber(TextView score1, TextView score2, String s) {
        String temp;
        if (isIgra == 1) {
            if (igrac == 1) {
                temp = String.valueOf(belotScore.getScore1());
                temp += s;
                belotScore.setScore1(Integer.parseInt(temp));
                score1.setText(String.valueOf(belotScore.getTotalScore1()));
                if (162 - belotScore.getScore1() <= 0) {
                    belotScore.setScore2(0);
                    score2.setText(String.valueOf(belotScore.getTotalScore2()));
                } else {
                    belotScore.setScore2(162 - belotScore.getScore1());
                    score2.setText(String.valueOf(belotScore.getTotalScore2()));
                }
            }
            if (igrac == 2) {
                temp = String.valueOf(belotScore.getScore2());
                temp += s;
                belotScore.setScore2(Integer.parseInt(temp));
                score2.setText(String.valueOf(belotScore.getTotalScore2()));
                if (162 - belotScore.getScore2() <= 0) {
                    belotScore.setScore1(0);
                    score1.setText(String.valueOf(belotScore.getTotalScore1()));
                } else {
                    belotScore.setScore1(162 - belotScore.getScore2());
                    score1.setText(String.valueOf(belotScore.getTotalScore1()));
                }
            }
        } else if (isIgra == 0) {
            if (igrac == 1) {
                temp = String.valueOf(belotScore.getAdditional1());
                temp += s;
                belotScore.setAdditional1(Integer.parseInt(temp));
                score1.setText(String.valueOf(belotScore.getTotalScore1()));
            }
            if (igrac == 2) {
                temp = String.valueOf(belotScore.getAdditional2());
                temp += s;
                belotScore.setAdditional2(Integer.parseInt(temp));
                score2.setText(String.valueOf(belotScore.getTotalScore2()));
            }
        }
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
}
