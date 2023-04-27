package tn.softtodo.shoptv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.softtodo.shoptv.web.rest.TestUtil;

class PlanOptionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanOptions.class);
        PlanOptions planOptions1 = new PlanOptions();
        planOptions1.setId(1L);
        PlanOptions planOptions2 = new PlanOptions();
        planOptions2.setId(planOptions1.getId());
        assertThat(planOptions1).isEqualTo(planOptions2);
        planOptions2.setId(2L);
        assertThat(planOptions1).isNotEqualTo(planOptions2);
        planOptions1.setId(null);
        assertThat(planOptions1).isNotEqualTo(planOptions2);
    }
}
