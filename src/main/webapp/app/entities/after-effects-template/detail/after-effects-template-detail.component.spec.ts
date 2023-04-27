import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AfterEffectsTemplateDetailComponent } from './after-effects-template-detail.component';

describe('AfterEffectsTemplate Management Detail Component', () => {
  let comp: AfterEffectsTemplateDetailComponent;
  let fixture: ComponentFixture<AfterEffectsTemplateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AfterEffectsTemplateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ afterEffectsTemplate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AfterEffectsTemplateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AfterEffectsTemplateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load afterEffectsTemplate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.afterEffectsTemplate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
