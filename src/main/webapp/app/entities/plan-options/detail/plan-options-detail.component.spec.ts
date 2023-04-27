import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlanOptionsDetailComponent } from './plan-options-detail.component';

describe('PlanOptions Management Detail Component', () => {
  let comp: PlanOptionsDetailComponent;
  let fixture: ComponentFixture<PlanOptionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlanOptionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ planOptions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlanOptionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlanOptionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load planOptions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.planOptions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
