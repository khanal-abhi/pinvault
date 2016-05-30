package co.khanal.pinvault;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.khanal.pinvault.pojos.Pin;

/**
 * Created by abhi on 5/23/16.
 */
public class PinRecyclerView extends RecyclerView.Adapter<PinRecyclerView.ViewHolder> {

    private List<Pin> pins;
    private int layout;

    public PinRecyclerView(List<Pin> pins, int layout){
        this.pins = pins;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
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

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            label = (TextView)itemView.findViewById(R.id.label);
            password = (TextView)itemView.findViewById(R.id.password);
        }

        public void setPin(Pin pin){
            label.setText(pin.getLabel());
            password.setText(pin.getPin());
            _id = pin.get_id();
        }

        @Override
        public void onClick(View v) {
            Log.d(getClass().getSimpleName(), "Click!");
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(getClass().getSimpleName(), "Long Click");
            return true;
        }
    }
}
