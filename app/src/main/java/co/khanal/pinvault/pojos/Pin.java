package co.khanal.pinvault.pojos;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import co.khanal.pinvault.contracts.PinContract;

/**
 * Created by abhi on 5/5/16.
 */
public class Pin implements Parcelable{

    private long _id;
    private String label;
    private String pin;
    private String notes;

    public Pin(String label, String pin, String notes) {
        this.label = label;
        this.pin = pin;
        this.notes = notes;
    }

    public Pin(long _id, String label, String pin, String notes) {
        this._id = _id;
        this.label = label;
        this.pin = pin;
        this.notes = notes;
    }

    public Pin() {
    }

    protected Pin(Parcel in) {
        _id = in.readLong();
        label = in.readString();
        pin = in.readString();
        notes = in.readString();
    }

    public static final Creator<Pin> CREATOR = new Creator<Pin>() {
        @Override
        public Pin createFromParcel(Parcel in) {
            Bundle bundle = in.readBundle();
            return new Pin(
                    bundle.getLong(PinContract.ID_COLUMN),
                    bundle.getString(PinContract.LABEL_COLUMN),
                    bundle.getString(PinContract.PIN_COLUMN),
                    bundle.getString(PinContract.NOTES_COLUMN)
            );
        }

        @Override
        public Pin[] newArray(int size) {
            return new Pin[size];
        }
    };

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pin)) return false;

        Pin pin1 = (Pin) o;

        if (!getLabel().equals(pin1.getLabel())) return false;
        if (!getPin().equals(pin1.getPin())) return false;
        return !(getNotes() != null ? !getNotes().equals(pin1.getNotes()) : pin1.getNotes() != null);

    }

    @Override
    public int hashCode() {
        int result = getLabel().hashCode();
        result = 31 * result + getPin().hashCode();
        result = 31 * result + (getNotes() != null ? getNotes().hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putLong(PinContract.ID_COLUMN, get_id());
        bundle.putString(PinContract.LABEL_COLUMN, getLabel());
        bundle.putString(PinContract.PIN_COLUMN, getPin());
        bundle.putString(PinContract.NOTES_COLUMN, getNotes());
        dest.writeBundle(bundle);
    }
}
