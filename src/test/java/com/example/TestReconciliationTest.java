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
    public void testScenario1_HISAndKIOSKBothExist() {
        // HIS有、KIOSK也有
        HISRecord his1 = new HISRecord("S001", "P001", "陳阿財", "M01", 1000);
        HISRecord his2 = new HISRecord("S002", "P001", "陳阿財", "M02", 456);
        HISRecord his3 = new HISRecord("S003", "P002", "郭銘銘", "M02", 32000);
        hisRepo.save(his1);
        hisRepo.save(his2);
        hisRepo.save(his3);

        KIOSKPoint point1 = new KIOSKPoint("P001", "陳阿財", "M01", "骨科", 1000, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point2 = new KIOSKPoint("P001", "陳阿財", "M02", "心臟內科", 456, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point3 = new KIOSKPoint("P002", "郭銘銘", "M02", "心臟內科", 32000, PaymentWay.CASH, ProcessStatus.SUCCESS);
        pointRepo.save(null, point1);
        pointRepo.save(null, point2);
        pointRepo.save(null, point3);

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
        KIOSKPoint point = new KIOSKPoint("P002", "郭銘銘", "M02", "心臟內科", 5000, PaymentWay.CASH,
                ProcessStatus.CLOSE_FAILED);
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
        HISRecord his = new HISRecord("S003", "P003", "盧大娘", "M02", 1650);
        hisRepo.save(his);

        KIOSKPoint point1 = new KIOSKPoint("P003", "盧大娘", "M02", "復健醫學部", 1650, PaymentWay.CASH, ProcessStatus.SUCCESS);
        KIOSKPoint point2 = new KIOSKPoint("P003", "盧大娘", "M02", "復健醫學部", 1650, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        pointRepo.save(null, point1);
        pointRepo.save(null, point2);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // 驗證結果
        System.out.println("=== 測試場景3: KIOSK有兩筆（其中一筆關帳失敗）、HIS只有一筆 ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testScenario4_MultipleKIOSKCloseFailedNoHIS() {
        // KIOSK有多筆關帳失敗的記錄，沒有入HIS
        KIOSKPoint point1 = new KIOSKPoint("P004", "王小明", "M01", "胃腸肝膽科", 3000, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        KIOSKPoint point2 = new KIOSKPoint("P005", "李大華", "M03", "神經內科", 4500, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        KIOSKPoint point3 = new KIOSKPoint("P006", "張美麗", "M02", "婦產科", 2500, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        pointRepo.save(null, point1);
        pointRepo.save(null, point2);
        pointRepo.save(null, point3);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // 驗證結果
        System.out.println("=== 測試場景4: KIOSK有多筆關帳失敗的記錄，沒有入HIS ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testScenario5_KIOSKCloseFailedButHasHIS() {
        // KIOSK有、狀態為關帳失敗，但其實有入HIS
        HISRecord his = new HISRecord("S004", "P007", "林小美", "M04", 3500);
        hisRepo.save(his);

        KIOSKPoint point = new KIOSKPoint("P007", "林小美", "M04", "皮膚科", 3500, PaymentWay.CASH, ProcessStatus.CLOSE_FAILED);
        pointRepo.save(null, point);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // 驗證結果
        System.out.println("=== 測試場景5: KIOSK有、狀態為關帳失敗，但其實有入HIS ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testScenario6_CARD_CloseFailed_NoHIS() {
        // KIOSK 有 CARD 付款的 CLOSE_FAILED 記錄，但 HIS 沒有
        KIOSKPoint point = new KIOSKPoint("P014", "趙六", "M11", "牙科", 8000, PaymentWay.CARD, ProcessStatus.CLOSE_FAILED);
        pointRepo.save(null, point);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // CARD 記錄不會出現在結果中
        System.out.println("=== 測試場景: KIOSK 有 CARD 付款的 CLOSE_FAILED 記錄，但 HIS 沒有 ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testScenario7_CARD_CloseFailedAndSuccess_HIS_One() {
        // CARD 付款方式 CLOSE_FAILED 一筆、SUCCESS 一筆，HIS 只有一筆（CLOSE_FAILED 的那筆因為關帳失敗，故沒有入
        // HIS）
        HISRecord his = new HISRecord("S005", "P015", "錢七", "M12", 9000);
        hisRepo.save(his);

        KIOSKPoint point1 = new KIOSKPoint("P015", "錢七", "M12", "皮膚科", 9000, PaymentWay.CARD, ProcessStatus.SUCCESS);
        KIOSKPoint point2 = new KIOSKPoint("P015", "錢七", "M12", "皮膚科", 9000, PaymentWay.CARD, ProcessStatus.CLOSE_FAILED);
        pointRepo.save(null, point1);
        pointRepo.save(null, point2);

        List<ReconciliationService.ReconciliationResult> results = reconciliationService.reconcile();
        // HIS 匹配 SUCCESS，CARD 記錄不出現
        System.out.println("=== 測試場景: CARD 付款方式 CLOSE_FAILED 一筆、SUCCESS 一筆，HIS 只有一筆 ===");
        for (ReconciliationService.ReconciliationResult result : results) {
            System.out.println(result);
        }
    }
}