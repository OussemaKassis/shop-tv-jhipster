import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanOptions } from '../plan-options.model';

@Component({
  selector: 'jhi-plan-options-detail',
  templateUrl: './plan-options-detail.component.html',
})
export class PlanOptionsDetailComponent implements OnInit {
  planOptions: IPlanOptions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planOptions }) => {
      this.planOptions = planOptions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
