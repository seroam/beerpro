package ch.beerpro.presentation.details.createrating;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.beerpro.R;


public class ShowPlaceFragment extends Fragment {

    private String placeName = "";

    public ShowPlaceFragment() {
        // Required empty public constructor
    }

    public static ShowPlaceFragment newInstance(String placeName) {
        ShowPlaceFragment fragment = new ShowPlaceFragment();
        fragment.placeName = placeName;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_place, container, false);

        TextView t = view.findViewById(R.id.place_name);
        t.setText(this.placeName);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
