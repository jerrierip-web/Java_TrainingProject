package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestReconciliationTest {

    private HISRecordRepository hisRepo;
    private KIOSKPointRepository pointRepo;
    private ReconciliationService reconciliationService;

    @BeforeEach
    public void setUp() {
        hisRepo = new HISRecordRepository();
        pointRepo = new KIOSKPointRepository();
        reconciliationService = new ReconciliationService(hisRepo, pointRepo);
    }

    @Test
    public void testOriginalScenario() {
        // 添加測試數據
        HISRecord his1 = new HISRecord("S001", "P001", "陳阿財", "M02", 456);
        HISRecord his2 = new HISRecord("S002", "P001", "陳阿財", "M01", 1000);
        HISRecord his3 = new HISRecord("S003", "P002", "郭銘銘", "M02", 32000);
        HISRecord his4 = new HISRecord("S004", "P003", "盧大娘", "M02", 1650);

        hisRepo.save(his1);
        hisRepo.save(his2);
        hisRepo.save(his3);
        hisRepo.save(his4);

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
        // 這裡可以添加斷言來驗證結果
        System.out.println("對帳結果:");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testScenario1_HISAndKIOSKBothExist() {
        // HIS有、KIOSK也有
        HISRecord his = new HISRecord("S001", "P001", "陳阿財", "M01", 1000);
        hisRepo.save(his);

        KIOSKPoint point = new KIOSKPoint("P001", "陳阿財", "M01", "骨科", 1000, PaymentWay.CASH, ProcessStatus.SUCCESS);
        pointRepo.save(null, point);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // 驗證結果
        System.out.println("=== 測試場景1: HIS有、KIOSK也有 ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testScenario2_KIOSKCloseFailedHISNotExist() {
        // KIOSK有（close_failed）、但因為關帳失敗而使HIS沒有
        KIOSKPoint point = new KIOSKPoint("P002", "郭銘銘", "M02", "心臟內科", 5000, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        pointRepo.save(null, point);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // 驗證結果
        System.out.println("=== 測試場景2: KIOSK有（close_failed）、但因為關帳失敗而使HIS沒有 ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testScenario3_KIOSKTwoPointsOneFailedHISOne() {
        // KIOSK有兩筆（其中一筆關帳失敗）、HIS只有一筆
        HISRecord his = new HISRecord("S003", "P003", "盧大娘", "M02", 2000);
        hisRepo.save(his);

        KIOSKPoint point1 = new KIOSKPoint("P003", "盧大娘", "M02", "復健醫學部", 2000, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point2 = new KIOSKPoint("P003", "盧大娘", "M02", "復健醫學部", 2000, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        pointRepo.save(null, point1);
        pointRepo.save(null, point2);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // 驗證結果
        System.out.println("=== 測試場景3: KIOSK有兩筆（其中一筆關帳失敗）、HIS只有一筆 ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }
}