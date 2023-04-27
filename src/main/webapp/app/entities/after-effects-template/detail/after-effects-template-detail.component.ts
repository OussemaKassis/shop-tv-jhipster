import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAfterEffectsTemplate } from '../after-effects-template.model';

@Component({
  selector: 'jhi-after-effects-template-detail',
  templateUrl: './after-effects-template-detail.component.html',
})
export class AfterEffectsTemplateDetailComponent implements OnInit {
  afterEffectsTemplate: IAfterEffectsTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afterEffectsTemplate }) => {
      this.afterEffectsTemplate = afterEffectsTemplate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
