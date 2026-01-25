package com.example;

import java.util.Objects;

public class HISRecord {

    private String serial_no;
    private String patient_id;
    private String patient_name;
    private String machine_no;
    private int money;

    public HISRecord(String serial_no, String patient_id, String patient_name, String machine_no, int money) {
        this.serial_no = serial_no;
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.machine_no = machine_no;
        setMoney(money);
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money < 0) throw new IllegalArgumentException("money cannot be negative");
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HISRecord hisRecord = (HISRecord) o;
        return Objects.equals(serial_no, hisRecord.serial_no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serial_no);
    }

    @Override
    public String toString() {
        return "門診收入紀錄：\n" +
                "門診號='" + serial_no + '\'' +
                ", 病歷號='" + patient_id + '\'' +
                ", 病患姓名='" + patient_name + '\'' +
                ", 機台號='" + machine_no + '\'' +
                ", 金額=" + money;
    }
}
