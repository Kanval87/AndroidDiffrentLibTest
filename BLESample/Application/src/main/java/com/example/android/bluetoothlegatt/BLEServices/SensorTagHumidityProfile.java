package com.example.android.bluetoothlegatt.BLEServices;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import com.example.android.bluetoothlegatt.BluetoothLeService;
import com.example.android.bluetoothlegatt.SensorTagGatt;
import com.example.android.bluetoothlegatt.utils.Point3D;
import com.example.android.bluetoothlegatt.utils.Sensor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorTagHumidityProfile extends GenericBLEServices {


    private static final String TAG = SensorTagHumidityProfile.class.getSimpleName();

    public SensorTagHumidityProfile(BluetoothGattService bluetoothGattService, BluetoothDevice bluetoothDevice, BluetoothLeService bluetoothLeService) {
        super(bluetoothGattService, bluetoothDevice, bluetoothLeService);

        List<BluetoothGattCharacteristic> characteristics = this.bluetoothGattService.getCharacteristics();

        for (BluetoothGattCharacteristic c : characteristics) {
            if (c.getUuid().toString().equals(SensorTagGatt.UUID_HUM_DATA.toString())) {
                this.dataBluetoothGattCharacteristic = c;
            }
            if (c.getUuid().toString().equals(SensorTagGatt.UUID_HUM_CONF.toString())) {
                this.configBluetoothGattCharacteristic = c;
            }
            if (c.getUuid().toString().equals(SensorTagGatt.UUID_HUM_PERI.toString())) {
                this.periodBluetoothGattCharacteristic = c;
            }
        }

    }

    @Override
    public void didUpdateValueForCharacteristic(BluetoothGattCharacteristic c) {
        byte[] value = c.getValue();
        if (c.equals(this.dataBluetoothGattCharacteristic)) {
            Point3D v = Sensor.HUMIDITY.convert(value);
            Log.d(TAG, String.format("%.1f %%rH", v.x));
        }
    }

    @Override
    public Map<String, String> getMQTTMap() {
        Point3D v = Sensor.HUMIDITY.convert(this.dataBluetoothGattCharacteristic.getValue());
        Map<String, String> map = new HashMap<String, String>();
        map.put("humidity", String.format("%.2f", v.x));
        return map;
    }

    public static boolean isCorrectService(BluetoothGattService s) {
        if ((s.getUuid().toString().compareTo(SensorTagGatt.UUID_HUM_SERV.toString())) == 0) {//service.getUuid().toString().compareTo(SensorTagGatt.UUID_HUM_DATA.toString())) {
            Log.d("Test", "Match !");
            return true;
        } else return false;
    }
}
