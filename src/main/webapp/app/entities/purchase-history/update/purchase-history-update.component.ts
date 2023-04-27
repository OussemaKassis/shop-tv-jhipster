import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PurchaseHistoryFormService, PurchaseHistoryFormGroup } from './purchase-history-form.service';
import { IPurchaseHistory } from '../purchase-history.model';
import { PurchaseHistoryService } from '../service/purchase-history.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

@Component({
  selector: 'jhi-purchase-history-update',
  templateUrl: './purchase-history-update.component.html',
})
export class PurchaseHistoryUpdateComponent implements OnInit {
  isSaving = false;
  purchaseHistory: IPurchaseHistory | null = null;

  clientsSharedCollection: IClient[] = [];

  editForm: PurchaseHistoryFormGroup = this.purchaseHistoryFormService.createPurchaseHistoryFormGroup();

  constructor(
    protected purchaseHistoryService: PurchaseHistoryService,
    protected purchaseHistoryFormService: PurchaseHistoryFormService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseHistory }) => {
      this.purchaseHistory = purchaseHistory;
      if (purchaseHistory) {
        this.updateForm(purchaseHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchaseHistory = this.purchaseHistoryFormService.getPurchaseHistory(this.editForm);
    if (purchaseHistory.id !== null) {
      this.subscribeToSaveResponse(this.purchaseHistoryService.update(purchaseHistory));
    } else {
      this.subscribeToSaveResponse(this.purchaseHistoryService.create(purchaseHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseHistory>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(purchaseHistory: IPurchaseHistory): void {
    this.purchaseHistory = purchaseHistory;
    this.purchaseHistoryFormService.resetForm(this.editForm, purchaseHistory);

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(
      this.clientsSharedCollection,
      purchaseHistory.client
    );
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.purchaseHistory?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }
}
