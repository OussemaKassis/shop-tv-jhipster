package tn.softtodo.shoptv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.softtodo.shoptv.web.rest.TestUtil;

class AfterEffectsTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AfterEffectsTemplate.class);
        AfterEffectsTemplate afterEffectsTemplate1 = new AfterEffectsTemplate();
        afterEffectsTemplate1.setId(1L);
        AfterEffectsTemplate afterEffectsTemplate2 = new AfterEffectsTemplate();
        afterEffectsTemplate2.setId(afterEffectsTemplate1.getId());
        assertThat(afterEffectsTemplate1).isEqualTo(afterEffectsTemplate2);
        afterEffectsTemplate2.setId(2L);
        assertThat(afterEffectsTemplate1).isNotEqualTo(afterEffectsTemplate2);
        afterEffectsTemplate1.setId(null);
        assertThat(afterEffectsTemplate1).isNotEqualTo(afterEffectsTemplate2);
    }
}
