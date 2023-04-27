import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';

@Component({
  selector: 'jhi-after-effects-template-assets-detail',
  templateUrl: './after-effects-template-assets-detail.component.html',
})
export class AfterEffectsTemplateAssetsDetailComponent implements OnInit {
  afterEffectsTemplateAssets: IAfterEffectsTemplateAssets | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afterEffectsTemplateAssets }) => {
      this.afterEffectsTemplateAssets = afterEffectsTemplateAssets;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
