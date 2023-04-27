package tn.softtodo.shoptv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.softtodo.shoptv.web.rest.TestUtil;

class AfterEffectsTemplateAssetsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AfterEffectsTemplateAssets.class);
        AfterEffectsTemplateAssets afterEffectsTemplateAssets1 = new AfterEffectsTemplateAssets();
        afterEffectsTemplateAssets1.setId(1L);
        AfterEffectsTemplateAssets afterEffectsTemplateAssets2 = new AfterEffectsTemplateAssets();
        afterEffectsTemplateAssets2.setId(afterEffectsTemplateAssets1.getId());
        assertThat(afterEffectsTemplateAssets1).isEqualTo(afterEffectsTemplateAssets2);
        afterEffectsTemplateAssets2.setId(2L);
        assertThat(afterEffectsTemplateAssets1).isNotEqualTo(afterEffectsTemplateAssets2);
        afterEffectsTemplateAssets1.setId(null);
        assertThat(afterEffectsTemplateAssets1).isNotEqualTo(afterEffectsTemplateAssets2);
    }
}
