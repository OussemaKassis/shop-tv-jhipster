import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AfterEffectsTemplateAssetsDetailComponent } from './after-effects-template-assets-detail.component';

describe('AfterEffectsTemplateAssets Management Detail Component', () => {
  let comp: AfterEffectsTemplateAssetsDetailComponent;
  let fixture: ComponentFixture<AfterEffectsTemplateAssetsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AfterEffectsTemplateAssetsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ afterEffectsTemplateAssets: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AfterEffectsTemplateAssetsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AfterEffectsTemplateAssetsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load afterEffectsTemplateAssets on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.afterEffectsTemplateAssets).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
