package tn.softtodo.shoptv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.softtodo.shoptv.web.rest.TestUtil;

class PlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plan.class);
        Plan plan1 = new Plan();
        plan1.setId(1L);
        Plan plan2 = new Plan();
        plan2.setId(plan1.getId());
        assertThat(plan1).isEqualTo(plan2);
        plan2.setId(2L);
        assertThat(plan1).isNotEqualTo(plan2);
        plan1.setId(null);
        assertThat(plan1).isNotEqualTo(plan2);
    }
}
