package co.khanal.pinvault;

import android.content.Context;
import android.test.AndroidTestCase;

import java.sql.SQLException;
import java.util.List;

import co.khanal.pinvault.contracts.PinContract;
import co.khanal.pinvault.helpers.PinHelper;
import co.khanal.pinvault.pojos.Pin;

/**
 * Created by abhi on 5/17/16.
 */
public class PinHelperTest extends AndroidTestCase {

    Context mContext;
    PinHelper mPinHelper;
    Pin pin1, pin2, pin3;

    long _id;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mContext = getContext();
        mPinHelper = new PinHelper(mContext, PinContract.DATABASE_NAME, null, PinContract.DB_VERSION);
        mPinHelper.removeAll();

        pin1 = new Pin("Hello", "World", "Peeps");
        pin2 = new Pin("Foo", "Bar", "Baz");
        pin3 = new Pin("One", "Two", "Three");

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        mPinHelper.removeAll();
        mPinHelper.close();
        pin1 = null;
        pin2 = null;
        pin3 = null;
    }

    public void testHasNoPins() throws Exception {
        assertEquals(0, mPinHelper.getPins().size());
    }

    public void testCanAddPin() throws Exception {
        try {
            _id = mPinHelper.insertPin(pin1);
            assertNotSame("Should not be 0", 0, _id);
        } catch (SQLException e){
            assertTrue("faced an exception trying to add the pin.", false);
        }
    }

    public void testCanGetPin() throws Exception {
        try {
            _id = mPinHelper.insertPin(pin1);
            Pin pin = mPinHelper.getPin(_id);
            assertEquals(pin1, pin);
        } catch (Exception e){
            assertTrue(e.getMessage(), false);
        }
    }

    public void testCanGetPins() throws Exception {
        try {
            mPinHelper.insertPin(pin1);
            mPinHelper.insertPin(pin2);
            mPinHelper.insertPin(pin3);

            List<Pin> pins = mPinHelper.getPins();

            assertTrue(pins.contains(pin1));
            assertTrue(pins.contains(pin2));
            assertTrue(pins.contains(pin3));
        } catch (Exception e){
            assertTrue(e.getMessage(), false);
        }
    }

    public void testCanRemovePin() throws Exception {
        try {
            _id = mPinHelper.insertPin(pin1);
            assertTrue(mPinHelper.removePin(_id));
            Pin pin = mPinHelper.getPin(_id);
            assertNull(pin);
        } catch (Exception e) {
            assertTrue(e.getMessage(), false);
        }
    }

    public void testCanRemoveAll() throws Exception{
        try {
            mPinHelper.insertPin(pin1);
            mPinHelper.insertPin(pin2);
            mPinHelper.insertPin(pin3);

            List<Pin> pins = mPinHelper.getPins();

            assertTrue(pins.contains(pin1));
            assertTrue(pins.contains(pin2));
            assertTrue(pins.contains(pin3));

            assertTrue(mPinHelper.removeAll());
            pins = mPinHelper.getPins();
            assertEquals(0, pins.size());

        } catch (Exception e){
            assertTrue(e.getMessage(), false);
        }
    }

    public void testCanUpdatePin() throws Exception{
        try {
            _id = mPinHelper.insertPin(pin1);
            Pin newPin = mPinHelper.getPin(_id);
            newPin.setNotes(pin2.getNotes());
            _id = mPinHelper.updatePin(newPin);
            Pin pin = mPinHelper.getPin(_id);
            assertEquals(newPin.getNotes(), pin.getNotes());
        } catch (Exception e){
            assertTrue(e.getMessage(), false);
        }
    }

    public void testCanUpdatePin2() throws Exception{
        try {
            _id = mPinHelper.insertPin(pin1);
            Pin newPin = mPinHelper.getPin(_id);
            newPin.setNotes(pin2.getNotes());
            _id = mPinHelper.updatePin(newPin);
            Pin pin = mPinHelper.getPin(_id);
            assertNotSame(pin.getNotes(), pin1.getNotes());
        } catch (Exception e){
            assertTrue(e.getMessage(), false);
        }
    }




}
