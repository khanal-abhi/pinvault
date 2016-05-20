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

    public Pin(int _id, String label, String pin, String notes) {
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
}
