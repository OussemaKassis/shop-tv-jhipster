import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAfterEffectsTemplate } from '../after-effects-template.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../after-effects-template.test-samples';

import { AfterEffectsTemplateService, RestAfterEffectsTemplate } from './after-effects-template.service';

const requireRestSample: RestAfterEffectsTemplate = {
  ...sampleWithRequiredData,
  templateRating: sampleWithRequiredData.templateRating?.toJSON(),
};

describe('AfterEffectsTemplate Service', () => {
  let service: AfterEffectsTemplateService;
  let httpMock: HttpTestingController;
  let expectedResult: IAfterEffectsTemplate | IAfterEffectsTemplate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AfterEffectsTemplateService);
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

    it('should create a AfterEffectsTemplate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const afterEffectsTemplate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(afterEffectsTemplate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AfterEffectsTemplate', () => {
      const afterEffectsTemplate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(afterEffectsTemplate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AfterEffectsTemplate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AfterEffectsTemplate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AfterEffectsTemplate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAfterEffectsTemplateToCollectionIfMissing', () => {
      it('should add a AfterEffectsTemplate to an empty array', () => {
        const afterEffectsTemplate: IAfterEffectsTemplate = sampleWithRequiredData;
        expectedResult = service.addAfterEffectsTemplateToCollectionIfMissing([], afterEffectsTemplate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(afterEffectsTemplate);
      });

      it('should not add a AfterEffectsTemplate to an array that contains it', () => {
        const afterEffectsTemplate: IAfterEffectsTemplate = sampleWithRequiredData;
        const afterEffectsTemplateCollection: IAfterEffectsTemplate[] = [
          {
            ...afterEffectsTemplate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAfterEffectsTemplateToCollectionIfMissing(afterEffectsTemplateCollection, afterEffectsTemplate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AfterEffectsTemplate to an array that doesn't contain it", () => {
        const afterEffectsTemplate: IAfterEffectsTemplate = sampleWithRequiredData;
        const afterEffectsTemplateCollection: IAfterEffectsTemplate[] = [sampleWithPartialData];
        expectedResult = service.addAfterEffectsTemplateToCollectionIfMissing(afterEffectsTemplateCollection, afterEffectsTemplate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(afterEffectsTemplate);
      });

      it('should add only unique AfterEffectsTemplate to an array', () => {
        const afterEffectsTemplateArray: IAfterEffectsTemplate[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const afterEffectsTemplateCollection: IAfterEffectsTemplate[] = [sampleWithRequiredData];
        expectedResult = service.addAfterEffectsTemplateToCollectionIfMissing(afterEffectsTemplateCollection, ...afterEffectsTemplateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const afterEffectsTemplate: IAfterEffectsTemplate = sampleWithRequiredData;
        const afterEffectsTemplate2: IAfterEffectsTemplate = sampleWithPartialData;
        expectedResult = service.addAfterEffectsTemplateToCollectionIfMissing([], afterEffectsTemplate, afterEffectsTemplate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(afterEffectsTemplate);
        expect(expectedResult).toContain(afterEffectsTemplate2);
      });

      it('should accept null and undefined values', () => {
        const afterEffectsTemplate: IAfterEffectsTemplate = sampleWithRequiredData;
        expectedResult = service.addAfterEffectsTemplateToCollectionIfMissing([], null, afterEffectsTemplate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(afterEffectsTemplate);
      });

      it('should return initial array if no AfterEffectsTemplate is added', () => {
        const afterEffectsTemplateCollection: IAfterEffectsTemplate[] = [sampleWithRequiredData];
        expectedResult = service.addAfterEffectsTemplateToCollectionIfMissing(afterEffectsTemplateCollection, undefined, null);
        expect(expectedResult).toEqual(afterEffectsTemplateCollection);
      });
    });

    describe('compareAfterEffectsTemplate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAfterEffectsTemplate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAfterEffectsTemplate(entity1, entity2);
        const compareResult2 = service.compareAfterEffectsTemplate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAfterEffectsTemplate(entity1, entity2);
        const compareResult2 = service.compareAfterEffectsTemplate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAfterEffectsTemplate(entity1, entity2);
        const compareResult2 = service.compareAfterEffectsTemplate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
