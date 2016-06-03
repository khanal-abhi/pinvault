package co.khanal.pinvault;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import co.khanal.pinvault.contracts.PinContract;
import co.khanal.pinvault.helpers.PinHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MasterPin extends Fragment {

    EditText password;

    Button save;


    public MasterPin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master_pin, container, false);

        password = (EditText)view.findViewById(R.id.password_value);
        save = (Button)view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPassword = password.getText().toString();
                if(mPassword.isEmpty()){
                    Snackbar.make(getView(), "Password cannot be empty", Snackbar.LENGTH_SHORT).show();
                } else {
                    PinHelper mPinHelper = new PinHelper(getContext(), PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);
                    mPinHelper.setMasterPin(mPassword);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, new LoadPinsFragment())
                            .commit();
                }
            }
        });

        return view;
    }

}
