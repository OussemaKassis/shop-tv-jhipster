import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPurchaseHistory } from '../purchase-history.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../purchase-history.test-samples';

import { PurchaseHistoryService, RestPurchaseHistory } from './purchase-history.service';

const requireRestSample: RestPurchaseHistory = {
  ...sampleWithRequiredData,
  purchaseDate: sampleWithRequiredData.purchaseDate?.toJSON(),
};

describe('PurchaseHistory Service', () => {
  let service: PurchaseHistoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IPurchaseHistory | IPurchaseHistory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PurchaseHistoryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PurchaseHistory', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const purchaseHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(purchaseHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PurchaseHistory', () => {
      const purchaseHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(purchaseHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PurchaseHistory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PurchaseHistory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PurchaseHistory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPurchaseHistoryToCollectionIfMissing', () => {
      it('should add a PurchaseHistory to an empty array', () => {
        const purchaseHistory: IPurchaseHistory = sampleWithRequiredData;
        expectedResult = service.addPurchaseHistoryToCollectionIfMissing([], purchaseHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purchaseHistory);
      });

      it('should not add a PurchaseHistory to an array that contains it', () => {
        const purchaseHistory: IPurchaseHistory = sampleWithRequiredData;
        const purchaseHistoryCollection: IPurchaseHistory[] = [
          {
            ...purchaseHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPurchaseHistoryToCollectionIfMissing(purchaseHistoryCollection, purchaseHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PurchaseHistory to an array that doesn't contain it", () => {
        const purchaseHistory: IPurchaseHistory = sampleWithRequiredData;
        const purchaseHistoryCollection: IPurchaseHistory[] = [sampleWithPartialData];
        expectedResult = service.addPurchaseHistoryToCollectionIfMissing(purchaseHistoryCollection, purchaseHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purchaseHistory);
      });

      it('should add only unique PurchaseHistory to an array', () => {
        const purchaseHistoryArray: IPurchaseHistory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const purchaseHistoryCollection: IPurchaseHistory[] = [sampleWithRequiredData];
        expectedResult = service.addPurchaseHistoryToCollectionIfMissing(purchaseHistoryCollection, ...purchaseHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const purchaseHistory: IPurchaseHistory = sampleWithRequiredData;
        const purchaseHistory2: IPurchaseHistory = sampleWithPartialData;
        expectedResult = service.addPurchaseHistoryToCollectionIfMissing([], purchaseHistory, purchaseHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purchaseHistory);
        expect(expectedResult).toContain(purchaseHistory2);
      });

      it('should accept null and undefined values', () => {
        const purchaseHistory: IPurchaseHistory = sampleWithRequiredData;
        expectedResult = service.addPurchaseHistoryToCollectionIfMissing([], null, purchaseHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purchaseHistory);
      });

      it('should return initial array if no PurchaseHistory is added', () => {
        const purchaseHistoryCollection: IPurchaseHistory[] = [sampleWithRequiredData];
        expectedResult = service.addPurchaseHistoryToCollectionIfMissing(purchaseHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(purchaseHistoryCollection);
      });
    });

    describe('comparePurchaseHistory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePurchaseHistory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePurchaseHistory(entity1, entity2);
        const compareResult2 = service.comparePurchaseHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePurchaseHistory(entity1, entity2);
        const compareResult2 = service.comparePurchaseHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePurchaseHistory(entity1, entity2);
        const compareResult2 = service.comparePurchaseHistory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
