package com.example;

import java.util.List;

public class TestReconciliation {

    public static void main(String[] args) {
        HISRecordRepository hisRepo = new HISRecordRepository();
        KIOSKPointRepository pointRepo = new KIOSKPointRepository();
        ReconciliationService reconciliationService = new ReconciliationService(hisRepo, pointRepo);

        // 添加測試數據
        HISRecord his1 = new HISRecord("S001", "P001", "陳阿財", "M02", 456);
        HISRecord his2 = new HISRecord("S002", "P001", "陳阿財", "M01", 1000);
        HISRecord his3 = new HISRecord("S003", "P002", "郭銘銘", "M02", 32000);
        HISRecord his4 = new HISRecord("S004", "P003", "盧大娘", "M02", 1650);
        //HISRecord his5 = new HISRecord("S005", "P003", "盧大娘", "M02", 1650);
        //HISRecord his6 = new HISRecord("S006", "P004", "王小明", "M01", 570);

        hisRepo.save(his1);
        hisRepo.save(his2);
        hisRepo.save(his3);
        hisRepo.save(his4);
        //hisRepo.save(his5);
        //hisRepo.save(his6);

        KIOSKPoint point1 = new KIOSKPoint("P001", "陳阿財", "M02", "耳鼻喉頭頸科", 456, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point2 = new KIOSKPoint("P001", "陳阿財", "M01", "骨科", 1000, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point3 = new KIOSKPoint("P002", "郭銘銘", "M02", "心臟內科", 32000, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point4 = new KIOSKPoint("P003", "盧大娘", "M02", "復健醫學部", 1650, PaymentWay.CARD, ProcessStatus.SUCCESS);
        KIOSKPoint point5 = new KIOSKPoint("P003", "盧大娘", "M02", "口腔顎面", 1650, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        KIOSKPoint point6 = new KIOSKPoint("P003", "盧大娘", "M02", "口腔顎面", 1650, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point7 = new KIOSKPoint("P004", "王小明", "M01", "胃腸肝膽科", 570, PaymentWay.CARD, ProcessStatus.SUCCESS);
        pointRepo.save(null, point1);
        pointRepo.save(null, point2);
        pointRepo.save(null, point3);
        pointRepo.save(null, point4);
        pointRepo.save(null, point5);
        pointRepo.save(null, point6);
        pointRepo.save(null, point7);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        System.out.println("對帳結果:");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }
}