import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ClientFormService, ClientFormGroup } from './client-form.service';
import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';
import { IPlan } from 'app/entities/plan/plan.model';
import { PlanService } from 'app/entities/plan/service/plan.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { Job } from 'app/entities/enumerations/job.model';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  client: IClient | null = null;
  jobValues = Object.keys(Job);

  plansSharedCollection: IPlan[] = [];
  addressesSharedCollection: IAddress[] = [];
  appUsersSharedCollection: IAppUser[] = [];

  editForm: ClientFormGroup = this.clientFormService.createClientFormGroup();

  constructor(
    protected clientService: ClientService,
    protected clientFormService: ClientFormService,
    protected planService: PlanService,
    protected addressService: AddressService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlan = (o1: IPlan | null, o2: IPlan | null): boolean => this.planService.comparePlan(o1, o2);

  compareAddress = (o1: IAddress | null, o2: IAddress | null): boolean => this.addressService.compareAddress(o1, o2);

  compareAppUser = (o1: IAppUser | null, o2: IAppUser | null): boolean => this.appUserService.compareAppUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      if (client) {
        this.updateForm(client);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.clientFormService.getClient(this.editForm);
    if (client.id !== null) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  protected updateForm(client: IClient): void {
    this.client = client;
    this.clientFormService.resetForm(this.editForm, client);

    this.plansSharedCollection = this.planService.addPlanToCollectionIfMissing<IPlan>(this.plansSharedCollection, client.plan);
    this.addressesSharedCollection = this.addressService.addAddressToCollectionIfMissing<IAddress>(
      this.addressesSharedCollection,
      client.address
    );
    this.appUsersSharedCollection = this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(
      this.appUsersSharedCollection,
      client.appUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.planService
      .query()
      .pipe(map((res: HttpResponse<IPlan[]>) => res.body ?? []))
      .pipe(map((plans: IPlan[]) => this.planService.addPlanToCollectionIfMissing<IPlan>(plans, this.client?.plan)))
      .subscribe((plans: IPlan[]) => (this.plansSharedCollection = plans));

    this.addressService
      .query()
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing<IAddress>(addresses, this.client?.address)))
      .subscribe((addresses: IAddress[]) => (this.addressesSharedCollection = addresses));

    this.appUserService
      .query()
      .pipe(map((res: HttpResponse<IAppUser[]>) => res.body ?? []))
      .pipe(map((appUsers: IAppUser[]) => this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(appUsers, this.client?.appUser)))
      .subscribe((appUsers: IAppUser[]) => (this.appUsersSharedCollection = appUsers));
  }
}
