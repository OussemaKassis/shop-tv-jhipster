package tn.softtodo.shoptv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.softtodo.shoptv.web.rest.TestUtil;

class PurchaseHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseHistory.class);
        PurchaseHistory purchaseHistory1 = new PurchaseHistory();
        purchaseHistory1.setId(1L);
        PurchaseHistory purchaseHistory2 = new PurchaseHistory();
        purchaseHistory2.setId(purchaseHistory1.getId());
        assertThat(purchaseHistory1).isEqualTo(purchaseHistory2);
        purchaseHistory2.setId(2L);
        assertThat(purchaseHistory1).isNotEqualTo(purchaseHistory2);
        purchaseHistory1.setId(null);
        assertThat(purchaseHistory1).isNotEqualTo(purchaseHistory2);
    }
}
