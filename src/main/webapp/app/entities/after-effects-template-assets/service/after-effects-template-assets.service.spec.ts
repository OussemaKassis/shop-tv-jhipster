import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../after-effects-template-assets.test-samples';

import { AfterEffectsTemplateAssetsService } from './after-effects-template-assets.service';

const requireRestSample: IAfterEffectsTemplateAssets = {
  ...sampleWithRequiredData,
};

describe('AfterEffectsTemplateAssets Service', () => {
  let service: AfterEffectsTemplateAssetsService;
  let httpMock: HttpTestingController;
  let expectedResult: IAfterEffectsTemplateAssets | IAfterEffectsTemplateAssets[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AfterEffectsTemplateAssetsService);
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

    it('should create a AfterEffectsTemplateAssets', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const afterEffectsTemplateAssets = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(afterEffectsTemplateAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AfterEffectsTemplateAssets', () => {
      const afterEffectsTemplateAssets = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(afterEffectsTemplateAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AfterEffectsTemplateAssets', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AfterEffectsTemplateAssets', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AfterEffectsTemplateAssets', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAfterEffectsTemplateAssetsToCollectionIfMissing', () => {
      it('should add a AfterEffectsTemplateAssets to an empty array', () => {
        const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = sampleWithRequiredData;
        expectedResult = service.addAfterEffectsTemplateAssetsToCollectionIfMissing([], afterEffectsTemplateAssets);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(afterEffectsTemplateAssets);
      });

      it('should not add a AfterEffectsTemplateAssets to an array that contains it', () => {
        const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = sampleWithRequiredData;
        const afterEffectsTemplateAssetsCollection: IAfterEffectsTemplateAssets[] = [
          {
            ...afterEffectsTemplateAssets,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAfterEffectsTemplateAssetsToCollectionIfMissing(
          afterEffectsTemplateAssetsCollection,
          afterEffectsTemplateAssets
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AfterEffectsTemplateAssets to an array that doesn't contain it", () => {
        const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = sampleWithRequiredData;
        const afterEffectsTemplateAssetsCollection: IAfterEffectsTemplateAssets[] = [sampleWithPartialData];
        expectedResult = service.addAfterEffectsTemplateAssetsToCollectionIfMissing(
          afterEffectsTemplateAssetsCollection,
          afterEffectsTemplateAssets
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(afterEffectsTemplateAssets);
      });

      it('should add only unique AfterEffectsTemplateAssets to an array', () => {
        const afterEffectsTemplateAssetsArray: IAfterEffectsTemplateAssets[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const afterEffectsTemplateAssetsCollection: IAfterEffectsTemplateAssets[] = [sampleWithRequiredData];
        expectedResult = service.addAfterEffectsTemplateAssetsToCollectionIfMissing(
          afterEffectsTemplateAssetsCollection,
          ...afterEffectsTemplateAssetsArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = sampleWithRequiredData;
        const afterEffectsTemplateAssets2: IAfterEffectsTemplateAssets = sampleWithPartialData;
        expectedResult = service.addAfterEffectsTemplateAssetsToCollectionIfMissing(
          [],
          afterEffectsTemplateAssets,
          afterEffectsTemplateAssets2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(afterEffectsTemplateAssets);
        expect(expectedResult).toContain(afterEffectsTemplateAssets2);
      });

      it('should accept null and undefined values', () => {
        const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = sampleWithRequiredData;
        expectedResult = service.addAfterEffectsTemplateAssetsToCollectionIfMissing([], null, afterEffectsTemplateAssets, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(afterEffectsTemplateAssets);
      });

      it('should return initial array if no AfterEffectsTemplateAssets is added', () => {
        const afterEffectsTemplateAssetsCollection: IAfterEffectsTemplateAssets[] = [sampleWithRequiredData];
        expectedResult = service.addAfterEffectsTemplateAssetsToCollectionIfMissing(afterEffectsTemplateAssetsCollection, undefined, null);
        expect(expectedResult).toEqual(afterEffectsTemplateAssetsCollection);
      });
    });

    describe('compareAfterEffectsTemplateAssets', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAfterEffectsTemplateAssets(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAfterEffectsTemplateAssets(entity1, entity2);
        const compareResult2 = service.compareAfterEffectsTemplateAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAfterEffectsTemplateAssets(entity1, entity2);
        const compareResult2 = service.compareAfterEffectsTemplateAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAfterEffectsTemplateAssets(entity1, entity2);
        const compareResult2 = service.compareAfterEffectsTemplateAssets(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
