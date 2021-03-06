package co.khanal.pinvault;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.khanal.pinvault.contracts.PinContract;
import co.khanal.pinvault.helpers.PinHelper;
import co.khanal.pinvault.pojos.Pin;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditPin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditPin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String TAG = "EDIT_PIN";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Pin mPin;

    private EditText label_value, password_value, notes_value;
    private Button save, discard;

    public EditPin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPin.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPin newInstance(String param1, String param2) {
        EditPin fragment = new EditPin();
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
            mPin = getArguments().getParcelable(PinContract.TABLE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_pin, container, false);

        // EditTexts
        label_value = (EditText)view.findViewById(R.id.name_value);
        password_value = (EditText)view.findViewById(R.id.password_value);
        notes_value = (EditText)view.findViewById(R.id.notes_value);

        // Buttons
        save = (Button)view.findViewById(R.id.save);
        discard = (Button)view.findViewById(R.id.discard);

        if(mPin != null){
            label_value.setText(mPin.getLabel());
            password_value.setText(mPin.getPin());
            notes_value.setText(mPin.getNotes());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "Updating pin ...", Snackbar.LENGTH_SHORT).show();
                if(!(label_value.getText().toString()).isEmpty() &&
                        !(password_value.getText().toString()).isEmpty()){
                    mPin.setLabel(label_value.getText().toString());
                    mPin.setPin(password_value.getText().toString());
                    mPin.setNotes(notes_value.getText().toString());
                }
                PinHelper mPinHelper = new PinHelper(getContext(), PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);
                try {
                    mPinHelper.updatePin(mPin);
                    Snackbar.make(getView(), "Pin updated.", Snackbar.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, new LoadPinsFragment())
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frag_container, new LoadPinsFragment())
                        .commit();
            }
        });

        return view;
    }

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
