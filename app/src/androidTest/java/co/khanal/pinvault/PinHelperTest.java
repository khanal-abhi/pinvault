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

    public void testCanGetAndRemovePin() throws Exception {
        try {
            // insert the pin
            _id = mPinHelper.insertPin(pin2);
            assertNotSame("Should not be 0", 0, _id);

            // get the pin
            Pin pin = mPinHelper.getPin(_id);
            assertNotNull(pin);
            assertEquals(pin2, pin);

            // remove the pin
            mPinHelper.removePin(_id);

            // get all the pins
            List<Pin> pins = mPinHelper.getPins();

            // verify it is empty
            assertEquals(0, pins.size());


        } catch (SQLException e){

            assertTrue(e.getMessage(), false);
        }
    }


}
