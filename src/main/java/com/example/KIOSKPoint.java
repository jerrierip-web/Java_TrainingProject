package com.example;

public class KIOSKPoint {

    private String patient_id;
    private String patient_name;
    private String machine_no;
    private String division;
    private int money;
    private PaymentWay payment_way;
    private ProcessStatus process_status;

    public KIOSKPoint(String patient_id, String patient_name, String machine_no,
            String division, int money, PaymentWay payment_way, ProcessStatus process_status) {
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.machine_no = machine_no;
        this.division = division;
        setMoney(money);
        this.payment_way = payment_way;
        this.process_status = process_status;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money < 0) {
            throw new IllegalArgumentException("money cannot be negative");
        }
        this.money = money;
    }

    public PaymentWay getPayment_way() {
        return payment_way;
    }

    public void setPayment_way(PaymentWay payment_way) {
        this.payment_way = payment_way;
    }

    public ProcessStatus getProcess_status() {
        return process_status;
    }

    public void setProcess_status(ProcessStatus process_status) {
        this.process_status = process_status;
    }

    @Override
    public String toString() {
        return "KIOSKPoint{" +
                "patient_id='" + patient_id + '\'' +
                ", patient_name='" + patient_name + '\'' +
                ", machine_no='" + machine_no + '\'' +
                ", division='" + division + '\'' +
                ", money=" + money +
                ", payment_way=" + payment_way +
                ", process_status=" + process_status +
                '}';
    }

    public static void main(String[] args) {
        KIOSKPoint p = new KIOSKPoint("P001", "王小明", "M01", "內科", 150, PaymentWay.CASH, ProcessStatus.SUCCESS);
        System.out.println(p);
    }

}
