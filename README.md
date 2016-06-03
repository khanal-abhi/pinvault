# PinVault App
![alt-text](statics/img/logo.png "PinVault Logo")

This is an app that will be used to securely save pins/ passwords on an android device. It will us ethe Navigation Drawer for navigation purposes. And it will use SQlite for data persistence.

## Pin Model:
The Pin model will be made up of:
long _id, 
String label,
String pin, and
String notes.

## Pin Persistence:
The pins will persist on a Sqlite Database with the Schema representing the model.
The CRUD operations will be performed using the Helper Class.

## Pin Helper Class:
The following public methods are exposed from the Pin Helper Class:

1. long insertPin(Pin pin)
2. Pin getPin(long _id)
3. List<Pins> getPins(void)
4. boolean removePin(long _id)
5. boolean removeAll(void)

### Views:
1. New Pin: Used to add a new pin. Already implemented. Will let a user add a pin provided the label and the pin have been set at the very least. The note is optional. There will be an option to discard the pin if the pin doesn't need to be saved.
2. Load Pins: Is a recyclerview that lists all the pins and their passwords. A single click will go to the edit view which will let the user edit/display it. A long click will open up a snackbar with the option to confirm the delete as an action.
3. Edit Pin: Is a fragment that lets the user edit an existing pin. The user may choose to persist the edit or discard it.
4. Master Pin: Is a fragment that will set a master pin for the pinvault if there is not one already set.


#### Screenshots:
<img src="img/screenshot1.png" width="200" />
<img src="img/screenshot2.png" width="200" />
<img src="img/screenshot3.png" width="200" />