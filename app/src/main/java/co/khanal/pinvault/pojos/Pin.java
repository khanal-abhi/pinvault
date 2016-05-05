package co.khanal.pinvault.pojos;

/**
 * Created by abhi on 5/5/16.
 */
public class Pin {

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
}
