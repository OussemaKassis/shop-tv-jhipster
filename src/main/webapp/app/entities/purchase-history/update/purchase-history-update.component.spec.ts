import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PurchaseHistoryFormService } from './purchase-history-form.service';
import { PurchaseHistoryService } from '../service/purchase-history.service';
import { IPurchaseHistory } from '../purchase-history.model';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

import { PurchaseHistoryUpdateComponent } from './purchase-history-update.component';

describe('PurchaseHistory Management Update Component', () => {
  let comp: PurchaseHistoryUpdateComponent;
  let fixture: ComponentFixture<PurchaseHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let purchaseHistoryFormService: PurchaseHistoryFormService;
  let purchaseHistoryService: PurchaseHistoryService;
  let clientService: ClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PurchaseHistoryUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PurchaseHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PurchaseHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    purchaseHistoryFormService = TestBed.inject(PurchaseHistoryFormService);
    purchaseHistoryService = TestBed.inject(PurchaseHistoryService);
    clientService = TestBed.inject(ClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Client query and add missing value', () => {
      const purchaseHistory: IPurchaseHistory = { id: 456 };
      const client: IClient = { id: 78095 };
      purchaseHistory.client = client;

      const clientCollection: IClient[] = [{ id: 79294 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseHistory });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(
        clientCollection,
        ...additionalClients.map(expect.objectContaining)
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const purchaseHistory: IPurchaseHistory = { id: 456 };
      const client: IClient = { id: 96566 };
      purchaseHistory.client = client;

      activatedRoute.data = of({ purchaseHistory });
      comp.ngOnInit();

      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.purchaseHistory).toEqual(purchaseHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPurchaseHistory>>();
      const purchaseHistory = { id: 123 };
      jest.spyOn(purchaseHistoryFormService, 'getPurchaseHistory').mockReturnValue(purchaseHistory);
      jest.spyOn(purchaseHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purchaseHistory }));
      saveSubject.complete();

      // THEN
      expect(purchaseHistoryFormService.getPurchaseHistory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(purchaseHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(purchaseHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPurchaseHistory>>();
      const purchaseHistory = { id: 123 };
      jest.spyOn(purchaseHistoryFormService, 'getPurchaseHistory').mockReturnValue({ id: null });
      jest.spyOn(purchaseHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purchaseHistory }));
      saveSubject.complete();

      // THEN
      expect(purchaseHistoryFormService.getPurchaseHistory).toHaveBeenCalled();
      expect(purchaseHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPurchaseHistory>>();
      const purchaseHistory = { id: 123 };
      jest.spyOn(purchaseHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(purchaseHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareClient', () => {
      it('Should forward to clientService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clientService, 'compareClient');
        comp.compareClient(entity, entity2);
        expect(clientService.compareClient).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
