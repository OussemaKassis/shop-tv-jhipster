import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AfterEffectsTemplateFormService } from './after-effects-template-form.service';
import { AfterEffectsTemplateService } from '../service/after-effects-template.service';
import { IAfterEffectsTemplate } from '../after-effects-template.model';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IAfterEffectsTemplateAssets } from 'app/entities/after-effects-template-assets/after-effects-template-assets.model';
import { AfterEffectsTemplateAssetsService } from 'app/entities/after-effects-template-assets/service/after-effects-template-assets.service';

import { AfterEffectsTemplateUpdateComponent } from './after-effects-template-update.component';

describe('AfterEffectsTemplate Management Update Component', () => {
  let comp: AfterEffectsTemplateUpdateComponent;
  let fixture: ComponentFixture<AfterEffectsTemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let afterEffectsTemplateFormService: AfterEffectsTemplateFormService;
  let afterEffectsTemplateService: AfterEffectsTemplateService;
  let companyService: CompanyService;
  let categoryService: CategoryService;
  let afterEffectsTemplateAssetsService: AfterEffectsTemplateAssetsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AfterEffectsTemplateUpdateComponent],
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
      .overrideTemplate(AfterEffectsTemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AfterEffectsTemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    afterEffectsTemplateFormService = TestBed.inject(AfterEffectsTemplateFormService);
    afterEffectsTemplateService = TestBed.inject(AfterEffectsTemplateService);
    companyService = TestBed.inject(CompanyService);
    categoryService = TestBed.inject(CategoryService);
    afterEffectsTemplateAssetsService = TestBed.inject(AfterEffectsTemplateAssetsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Company query and add missing value', () => {
      const afterEffectsTemplate: IAfterEffectsTemplate = { id: 456 };
      const company: ICompany = { id: 54099 };
      afterEffectsTemplate.company = company;

      const companyCollection: ICompany[] = [{ id: 21971 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ afterEffectsTemplate });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Category query and add missing value', () => {
      const afterEffectsTemplate: IAfterEffectsTemplate = { id: 456 };
      const category: ICategory = { id: 70451 };
      afterEffectsTemplate.category = category;

      const categoryCollection: ICategory[] = [{ id: 66454 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ afterEffectsTemplate });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AfterEffectsTemplateAssets query and add missing value', () => {
      const afterEffectsTemplate: IAfterEffectsTemplate = { id: 456 };
      const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = { id: 31167 };
      afterEffectsTemplate.afterEffectsTemplateAssets = afterEffectsTemplateAssets;

      const afterEffectsTemplateAssetsCollection: IAfterEffectsTemplateAssets[] = [{ id: 95894 }];
      jest
        .spyOn(afterEffectsTemplateAssetsService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: afterEffectsTemplateAssetsCollection })));
      const additionalAfterEffectsTemplateAssets = [afterEffectsTemplateAssets];
      const expectedCollection: IAfterEffectsTemplateAssets[] = [
        ...additionalAfterEffectsTemplateAssets,
        ...afterEffectsTemplateAssetsCollection,
      ];
      jest
        .spyOn(afterEffectsTemplateAssetsService, 'addAfterEffectsTemplateAssetsToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ afterEffectsTemplate });
      comp.ngOnInit();

      expect(afterEffectsTemplateAssetsService.query).toHaveBeenCalled();
      expect(afterEffectsTemplateAssetsService.addAfterEffectsTemplateAssetsToCollectionIfMissing).toHaveBeenCalledWith(
        afterEffectsTemplateAssetsCollection,
        ...additionalAfterEffectsTemplateAssets.map(expect.objectContaining)
      );
      expect(comp.afterEffectsTemplateAssetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const afterEffectsTemplate: IAfterEffectsTemplate = { id: 456 };
      const company: ICompany = { id: 67980 };
      afterEffectsTemplate.company = company;
      const category: ICategory = { id: 74326 };
      afterEffectsTemplate.category = category;
      const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = { id: 67407 };
      afterEffectsTemplate.afterEffectsTemplateAssets = afterEffectsTemplateAssets;

      activatedRoute.data = of({ afterEffectsTemplate });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.afterEffectsTemplateAssetsSharedCollection).toContain(afterEffectsTemplateAssets);
      expect(comp.afterEffectsTemplate).toEqual(afterEffectsTemplate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAfterEffectsTemplate>>();
      const afterEffectsTemplate = { id: 123 };
      jest.spyOn(afterEffectsTemplateFormService, 'getAfterEffectsTemplate').mockReturnValue(afterEffectsTemplate);
      jest.spyOn(afterEffectsTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ afterEffectsTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: afterEffectsTemplate }));
      saveSubject.complete();

      // THEN
      expect(afterEffectsTemplateFormService.getAfterEffectsTemplate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(afterEffectsTemplateService.update).toHaveBeenCalledWith(expect.objectContaining(afterEffectsTemplate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAfterEffectsTemplate>>();
      const afterEffectsTemplate = { id: 123 };
      jest.spyOn(afterEffectsTemplateFormService, 'getAfterEffectsTemplate').mockReturnValue({ id: null });
      jest.spyOn(afterEffectsTemplateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ afterEffectsTemplate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: afterEffectsTemplate }));
      saveSubject.complete();

      // THEN
      expect(afterEffectsTemplateFormService.getAfterEffectsTemplate).toHaveBeenCalled();
      expect(afterEffectsTemplateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAfterEffectsTemplate>>();
      const afterEffectsTemplate = { id: 123 };
      jest.spyOn(afterEffectsTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ afterEffectsTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(afterEffectsTemplateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCategory', () => {
      it('Should forward to categoryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoryService, 'compareCategory');
        comp.compareCategory(entity, entity2);
        expect(categoryService.compareCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAfterEffectsTemplateAssets', () => {
      it('Should forward to afterEffectsTemplateAssetsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(afterEffectsTemplateAssetsService, 'compareAfterEffectsTemplateAssets');
        comp.compareAfterEffectsTemplateAssets(entity, entity2);
        expect(afterEffectsTemplateAssetsService.compareAfterEffectsTemplateAssets).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
