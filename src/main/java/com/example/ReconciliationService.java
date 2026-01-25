package com.example;

import java.util.List;
import java.util.ArrayList;

public class ReconciliationService {

    private final HISRecordRepository hisRecordRepository;
    private final KIOSKPointRepository kioskPointRepository;

    public ReconciliationService(HISRecordRepository hisRecordRepository, KIOSKPointRepository kioskPointRepository) {
        this.hisRecordRepository = hisRecordRepository;
        this.kioskPointRepository = kioskPointRepository;
    }

    public List<ReconciliationResult> reconcile() {
        List<HISRecord> hisRecords = hisRecordRepository.findAll();
        List<KIOSKPoint> kioskPoints = kioskPointRepository.findAll();

        List<ReconciliationResult> results = new ArrayList<>();

        // 檢查HISRecord是否有匹配的KIOSKPoint
        for (HISRecord hisRecord : hisRecords) {
            boolean matched = false;
            for (KIOSKPoint point : kioskPoints) {
                if (matches(hisRecord, point)) {
                    matched = true;
                    break;
                }
            }
            results.add(new ReconciliationResult(hisRecord, null, matched));
        }

        // 檢查KIOSKPoint是否有匹配的HISRecord（僅限CASH付款方式）
        for (KIOSKPoint point : kioskPoints) {
            if (point.getPayment_way() == PaymentWay.CASH) {
                boolean matched = false;
                for (HISRecord hisRecord : hisRecords) {
                    if (matches(hisRecord, point)) {
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    results.add(new ReconciliationResult(null, point, false));
                }
            }
        }

        return results;
    }

    private boolean matches(HISRecord hisRecord, KIOSKPoint point) {
        return hisRecord.getPatient_id().equals(point.getPatient_id()) &&
               hisRecord.getMachine_no().equals(point.getMachine_no()) &&
               hisRecord.getMoney() == point.getMoney() &&
               point.getProcess_status() == ProcessStatus.SUCCESS;
    }

    public static class ReconciliationResult {
        private HISRecord hisRecord;
        private KIOSKPoint kioskPoint;
        private boolean matched;

        public ReconciliationResult(HISRecord hisRecord, KIOSKPoint kioskPoint, boolean matched) {
            this.hisRecord = hisRecord;
            this.kioskPoint = kioskPoint;
            this.matched = matched;
        }

        @Override
        public String toString() {
            if (hisRecord != null) {
                return hisRecord + ", 是否符合=" + matched;
            } else if (kioskPoint != null) {
                return kioskPoint + ", 是否符合=" + matched;
            } else {
                return "無效結果";
            }
        }
    }
}