package com.satwick.instaclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {

    public static final String TAG = ProfileTab.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText profileName, profileAge, profileBio;
    private Button saveInfo;

    public ProfileTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        profileName = view.findViewById(R.id.profileName);
        profileAge = view.findViewById(R.id.profileAge);
        profileBio = view.findViewById(R.id.profileBio);
        saveInfo = view.findViewById(R.id.saveInfo);

        profileName.setText("");
        profileAge.setText("");
        profileBio.setText("");

        final ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser.get("profileName") != null) {
            profileName.setText(parseUser.get("profileName").toString());
        }

        if (parseUser.get("profileAge") != null) {
            profileAge.setText(parseUser.get("profileAge").toString());
        }

        if (parseUser.get("profileBio") != null) {
            profileBio.setText(parseUser.get("profileBio").toString());
        }


        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUser.put("profileName", profileName.getText().toString());
                parseUser.put("profileAge", profileAge.getText().toString());
                parseUser.put("profileBio", profileBio.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating");
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(getContext(),"Info Saved and Updated",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });
        return view;
    }
}