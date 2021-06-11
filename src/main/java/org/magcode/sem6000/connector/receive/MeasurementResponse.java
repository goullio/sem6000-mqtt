package org.magcode.sem6000.connector.receive;

import java.math.BigInteger;

public class MeasurementResponse extends SemResponse {
	private int voltage;
	private int frequency;
	private float power;
	private float current;
	private float powerFactor;
	private boolean powerOn = false;

	public MeasurementResponse(byte[] data, String id) {
		this.responseType = ResponseType.measure;
		if (data[0] == (byte) 0x01) {
			powerOn = true;
		}
		byte[] cv = new byte[] { data[1], data[2], data[3] };
		this.power = (float) new BigInteger(cv).intValue() / 1000;
		this.voltage = data[4] & 0xFF;
		cv = new byte[] { data[5], data[6] };
		this.current = (float) new BigInteger(cv).intValue() / 1000;
		this.frequency = data[7] & 0xFF;
		if(powerOn && voltage > 0 && current > 0){
			this.powerFactor = power/voltage/current;
		} else {
			this.powerFactor = 1;
		}
		setId(id);
	}

	public int getVoltage() {
		return voltage;
	}

	public int getFrequency() { return frequency; }

	public float getPower() {
		return power;
	}

	public float getCurrent() { return current; }

	public float getPowerFactor() { return powerFactor; }

	public String toString() {
		return "[" + this.getId() + "] Measure PowerOn: " + this.powerOn + " Voltage: " + this.voltage + " Power: "
				+ this.power;
	}

	public boolean isPowerOn() {
		return powerOn;
	}
}