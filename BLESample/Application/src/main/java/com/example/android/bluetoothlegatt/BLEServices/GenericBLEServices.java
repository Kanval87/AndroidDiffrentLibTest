package com.example.android.bluetoothlegatt.BLEServices;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import com.example.android.bluetoothlegatt.BluetoothLeService;

public class GenericBLEServices {
    protected BluetoothGattService bluetoothGattService;
    protected BluetoothDevice bluetoothDevice;
    protected BluetoothLeService bluetoothLeService;
    protected BluetoothGattCharacteristic dataBluetoothGattCharacteristic;
    protected BluetoothGattCharacteristic configBluetoothGattCharacteristic;
    protected BluetoothGattCharacteristic periodBluetoothGattCharacteristic;
    protected boolean isRegistered;
    public boolean isConfigured;
    public boolean isEnabled;
    protected static final int GATT_TIMEOUT = 250; // milliseconds


    public GenericBLEServices(BluetoothGattService bluetoothGattService, BluetoothDevice bluetoothDevice, BluetoothLeService bluetoothLeService) {
        this.bluetoothGattService = bluetoothGattService;
        this.bluetoothDevice = bluetoothDevice;
        this.bluetoothLeService = bluetoothLeService;
        dataBluetoothGattCharacteristic = null;
        configBluetoothGattCharacteristic = null;
        periodBluetoothGattCharacteristic = null;
    }

    public void configureService() {
        int error = this.bluetoothLeService.setCharacteristicNotification(this.dataBluetoothGattCharacteristic, true);
        if (error != 0) {
            if (this.dataBluetoothGattCharacteristic != null)
                printError("Sensor notification enable failed: ", this.dataBluetoothGattCharacteristic, error);
        }
        this.isConfigured = true;
    }

    public void deConfigureService() {
        int error = this.bluetoothLeService.setCharacteristicNotification(this.dataBluetoothGattCharacteristic, false);
        if (error != 0) {
            if (this.dataBluetoothGattCharacteristic != null)
                printError("Sensor notification disable failed: ", this.dataBluetoothGattCharacteristic, error);
        }
        this.isConfigured = false;
    }

    public void enableService() {
        int error = bluetoothLeService.writeCharacteristic(this.configBluetoothGattCharacteristic, (byte) 0x01);
        if (error != 0) {
            if (this.configBluetoothGattCharacteristic != null)
                printError("Sensor enable failed: ", this.configBluetoothGattCharacteristic, error);
        }
        //this.periodWasUpdated(1000);
        this.isEnabled = true;
    }

    public void disableService() {
        int error = bluetoothLeService.writeCharacteristic(this.configBluetoothGattCharacteristic, (byte) 0x00);
        if (error != 0) {
            if (this.configBluetoothGattCharacteristic != null)
                printError("Sensor disable failed: ", this.configBluetoothGattCharacteristic, error);
        }
        this.isConfigured = false;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public void setIsConfigured(boolean isConfigured) {
        this.isConfigured = isConfigured;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void printError(String msg, BluetoothGattCharacteristic c, int error) {
        try {
            Log.d("GenericBluetoothProfile", msg + c.getUuid().toString() + " Error: " + error);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
