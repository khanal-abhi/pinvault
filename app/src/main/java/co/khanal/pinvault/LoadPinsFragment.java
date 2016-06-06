package co.khanal.pinvault;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.khanal.pinvault.contracts.PinContract;
import co.khanal.pinvault.helpers.PinHelper;
import co.khanal.pinvault.interfaces.OnLoadDifferentFragmentListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoadPinsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadPinsFragment extends Fragment implements OnLoadDifferentFragmentListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String TAG = "LOAD_PINS";

    private FloatingActionButton fab;

    private RecyclerView pinRecyclerView;
    PinHelper mPinHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoadPinsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoadPinsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoadPinsFragment newInstance(String param1, String param2) {
        LoadPinsFragment fragment = new LoadPinsFragment();
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

        mPinHelper = new PinHelper(getContext(), PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pin_list, container, false);
        fab = (FloatingActionButton)view.findViewById(R.id.new_pin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frag_container, new NewPin())
                        .commit();
            }
        });
        try {
            Log.d(getClass().getSimpleName(), "Pins: " + mPinHelper.getPins().size());
            pinRecyclerView = (RecyclerView)view.findViewById(R.id.list);
            pinRecyclerView.setAdapter(new PinRecyclerView(getContext(), mPinHelper.getPins(), R.layout.fragment_pin, this));
            pinRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            pinRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } catch (Exception e){
            e.printStackTrace();
        }

        PinHelper mPinHelper = new PinHelper(getContext(), PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);
        if(mPinHelper.getMasterPin() == null){
            getFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, new MasterPin())
                    .commit();
        }

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

    @Override
    public void onLoadDifferentFragment(Fragment fragment, String Tag) {
        getFragmentManager().beginTransaction()
                .replace(R.id.frag_container, fragment, Tag)
                .commit();
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
