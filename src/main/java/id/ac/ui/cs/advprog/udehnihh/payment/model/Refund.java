package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.RefundStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Refund {
    private Transaction transaction;
    private String reasonForRefund;
    private RefundStatus status;

    public Refund(Transaction transaction, String reasonForRefund) {
        this.transaction = transaction;
        this.reasonForRefund = reasonForRefund;
        this.status = RefundStatus.ON_HOLD;
    }
}
