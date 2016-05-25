package co.khanal.pinvault;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.khanal.pinvault.pojos.Pin;

/**
 * Created by abhi on 5/23/16.
 */
public class PinRecyclerView extends RecyclerView.Adapter<PinRecyclerView.ViewHolder>{

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
            holder.label.setText(pin.getLabel());
            holder.password.setText(pin.getPin());
            holder.itemView.setTag(pin);
        } else {
            new NullPointerException("holder is null?");
        }
    }

    @Override
    public int getItemCount() {
        return pins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView label;
        public TextView password;

        public ViewHolder(View itemView) {
            super(itemView);
            label = (TextView)itemView.findViewById(R.id.label);
            password = (TextView)itemView.findViewById(R.id.password);
        }
    }
}
