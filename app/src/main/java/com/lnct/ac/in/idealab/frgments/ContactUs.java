package com.lnct.ac.in.idealab.frgments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.lnct.ac.in.idealab.Constants;
import com.lnct.ac.in.idealab.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactUs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUs extends Fragment {

    TextView lnctlink;
    CardView mailus;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactUs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactUs.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactUs newInstance(String param1, String param2) {
        ContactUs fragment = new ContactUs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contactus_view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        lnctlink = contactus_view.findViewById(R.id.lnctlink);
        mailus = contactus_view.findViewById(R.id.mailuslink);

        lnctlink.setOnClickListener(view -> {
            String url = "https://play.google.com/store/apps/details?id=com.lnct.ac.in.idealab";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        mailus.setOnClickListener(view -> {
            mailus.startAnimation(buttonClick);
            Uri uri = Uri.parse("mailto:" + Constants.idaelab_mail);
//
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent," Select an app to move with"));
        });

        return contactus_view;
    }
}