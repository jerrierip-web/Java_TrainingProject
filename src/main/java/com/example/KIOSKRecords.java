package com.example;

public class KIOSKRecords {

    private String machine_no;
    private int money;
    private PaymentWay payment_way;

    public KIOSKRecords(String machine_no, int money, PaymentWay payment_way) {
        this.machine_no = machine_no;
        setMoney(money);
        this.payment_way = payment_way;
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
        if (money < 0) {
            throw new IllegalArgumentException("money cannot be negative");
        }
        this.money = money;
    }

    public PaymentWay getPayment_way() {
        return payment_way;
    }

    @Override
    public String toString() {
        return "KIOSKRecords{" +
                "machine_no='" + machine_no + '\'' +
                ", money=" + money +
                ", payment_way=" + payment_way +
                '}';
    }
    public void setPayment_way(PaymentWay payment_way) {
        this.payment_way = payment_way;
    }

}
