package com.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class IncomeStatisticsUseCase {

    public List<KIOSKRecord> aggregatePointsToRecords(List<KIOSKPoint> points) {
        Map<String, Map<PaymentWay, Integer>> aggregated = new HashMap<>();
        
        for (KIOSKPoint point : points) {
            if (point.getProcess_status() == ProcessStatus.SUCCESS || point.getProcess_status() == ProcessStatus.CLOSE_FAILED) {
                String machineNo = point.getMachine_no();
                PaymentWay paymentWay = point.getPayment_way();
                int money = point.getMoney();
                
                aggregated.computeIfAbsent(machineNo, k -> new HashMap<>())
                         .merge(paymentWay, money, Integer::sum);
            }
        }
        
        List<KIOSKRecord> records = new ArrayList<>();
        for (Map.Entry<String, Map<PaymentWay, Integer>> machineEntry : aggregated.entrySet()) {
            String machineNo = machineEntry.getKey();
            for (Map.Entry<PaymentWay, Integer> paymentEntry : machineEntry.getValue().entrySet()) {
                PaymentWay paymentWay = paymentEntry.getKey();
                int totalMoney = paymentEntry.getValue();
                records.add(new KIOSKRecord(machineNo, totalMoney, paymentWay));
            }
        }
        
        return records;
    }
}
