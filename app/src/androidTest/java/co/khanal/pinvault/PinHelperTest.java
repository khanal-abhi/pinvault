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
            mPinHelper.insertPin(pin1);
            List<Pin> pins = mPinHelper.getPins();
            assertEquals(1, pins.size());
            _id = pins.get(0).get_id();

        } catch (SQLException e){
            assertTrue("faced an exception trying to add the pin.", false);
        }
    }

    public void testCanGetPin() throws Exception {
        Pin pin = mPinHelper.getPin(_id);
        assertEquals(pin1, pin);
    }
}