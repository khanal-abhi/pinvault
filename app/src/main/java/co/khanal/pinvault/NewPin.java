package co.khanal.pinvault;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.khanal.pinvault.contracts.PinContract;
import co.khanal.pinvault.helpers.PinHelper;
import co.khanal.pinvault.pojos.Pin;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewPin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewPin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String TAG = "NEW_PIN";

    EditText pinName;
    EditText pinPassword;
    EditText pinNotes;
    Button save;
    Button discard;

    PinHelper mPinHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewPin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPin.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPin newInstance(String param1, String param2) {
        NewPin fragment = new NewPin();
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

        // setHasOptionsMenu(true);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_new_pin, container, false);

        mPinHelper = new PinHelper(getContext(), PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);

        pinName = (EditText)view.findViewById(R.id.name_value);
        pinPassword = (EditText)view.findViewById(R.id.password_value);
        pinNotes = (EditText)view.findViewById(R.id.notes_value);

        save = (Button)view.findViewById(R.id.save);
        discard = (Button)view.findViewById(R.id.discard);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Saving now", Toast.LENGTH_SHORT).show();

                if(pinName != null){
                    if(pinPassword != null){
                        if(pinNotes != null){
                            if(!pinName.getText().toString().isEmpty() && !pinPassword.getText().toString().isEmpty()){
                                Pin pin = new Pin(pinName.getText().toString(), pinPassword.getText().toString(), pinNotes.getText().toString());
                                try {
                                    mPinHelper.insertPin(pin);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                Snackbar.make(getView(), "Saved pin!", Snackbar.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.frag_container, new LoadPinsFragment())
                                        .commit();

                            } else {
                                Toast.makeText(getContext(), "Name or Pin field empty. Cannot save.", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                    }
                }
                Toast.makeText(getContext(), "Error saving the pin!", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Discarding now", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.new_pin_menu, menu);
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
