package co.khanal.pinvault;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.khanal.pinvault.contracts.PinContract;
import co.khanal.pinvault.helpers.PinHelper;
import co.khanal.pinvault.interfaces.OnLoadDifferentFragmentListener;
import co.khanal.pinvault.pojos.Pin;

/**
 * Created by abhi on 5/23/16.
 */
public class PinRecyclerView extends RecyclerView.Adapter<PinRecyclerView.ViewHolder> {

    private List<Pin> pins;
    private int layout;
    private Context mContext;
    private OnLoadDifferentFragmentListener fragChangeListener;

    public PinRecyclerView(Context mcontext, List<Pin> pins, int layout, OnLoadDifferentFragmentListener listener){
        this.mContext = mcontext;
        this.pins = pins;
        this.layout = layout;
        this.fragChangeListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(mContext, view, fragChangeListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pin pin = pins.get(position);
        if(holder != null){
            holder.setPin(pin);
        } else {
            new NullPointerException("holder is null?");
        }
    }

    @Override
    public int getItemCount() {
        return pins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView label;
        public TextView password;
        private long _id;
        private Context mContext;
        private OnLoadDifferentFragmentListener fragChangeListener;

        public ViewHolder(Context mContext, View itemView, OnLoadDifferentFragmentListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.mContext = mContext;
            this.label = (TextView)itemView.findViewById(R.id.label);
            this.password = (TextView)itemView.findViewById(R.id.password);
            this.fragChangeListener = listener;
        }

        public void setPin(Pin pin){
            label.setText(pin.getLabel());
            password.setText(pin.getPin());
            _id = pin.get_id();
        }

        @Override
        public void onClick(View v) {
            Log.d(getClass().getSimpleName(), "Click!");
            PinHelper pinHelper = new PinHelper(mContext, PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);
            Pin pin;
            try {
                pin = pinHelper.getPin(_id);
                EditPin editPin = new EditPin();
                Bundle bundle = new Bundle();
                bundle.putParcelable(PinContract.TABLE_NAME, pin);
                editPin.setArguments(bundle);
                fragChangeListener.onLoadDifferentFragment(editPin, editPin.TAG);

            } catch (Exception e){
                Log.e(getClass().getSimpleName(), e.getMessage());
            }

        }

        @Override
        public boolean onLongClick(View v) {
            Snackbar.make(v.getRootView(), "Delete data?", Snackbar.LENGTH_SHORT).setAction("DELETE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PinHelper pinHelper = new PinHelper(mContext, PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);
                    try {
                        pinHelper.removePin(_id);
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), e.getMessage());
                    }
                }
            }).show();
            return true;
        }
    }
}
