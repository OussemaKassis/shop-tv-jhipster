import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AfterEffectsTemplateFormService, AfterEffectsTemplateFormGroup } from './after-effects-template-form.service';
import { IAfterEffectsTemplate } from '../after-effects-template.model';
import { AfterEffectsTemplateService } from '../service/after-effects-template.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IAfterEffectsTemplateAssets } from 'app/entities/after-effects-template-assets/after-effects-template-assets.model';
import { AfterEffectsTemplateAssetsService } from 'app/entities/after-effects-template-assets/service/after-effects-template-assets.service';

@Component({
  selector: 'jhi-after-effects-template-update',
  templateUrl: './after-effects-template-update.component.html',
})
export class AfterEffectsTemplateUpdateComponent implements OnInit {
  isSaving = false;
  afterEffectsTemplate: IAfterEffectsTemplate | null = null;

  companiesSharedCollection: ICompany[] = [];
  categoriesSharedCollection: ICategory[] = [];
  afterEffectsTemplateAssetsSharedCollection: IAfterEffectsTemplateAssets[] = [];

  editForm: AfterEffectsTemplateFormGroup = this.afterEffectsTemplateFormService.createAfterEffectsTemplateFormGroup();

  constructor(
    protected afterEffectsTemplateService: AfterEffectsTemplateService,
    protected afterEffectsTemplateFormService: AfterEffectsTemplateFormService,
    protected companyService: CompanyService,
    protected categoryService: CategoryService,
    protected afterEffectsTemplateAssetsService: AfterEffectsTemplateAssetsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  compareAfterEffectsTemplateAssets = (o1: IAfterEffectsTemplateAssets | null, o2: IAfterEffectsTemplateAssets | null): boolean =>
    this.afterEffectsTemplateAssetsService.compareAfterEffectsTemplateAssets(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afterEffectsTemplate }) => {
      this.afterEffectsTemplate = afterEffectsTemplate;
      if (afterEffectsTemplate) {
        this.updateForm(afterEffectsTemplate);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const afterEffectsTemplate = this.afterEffectsTemplateFormService.getAfterEffectsTemplate(this.editForm);
    if (afterEffectsTemplate.id !== null) {
      this.subscribeToSaveResponse(this.afterEffectsTemplateService.update(afterEffectsTemplate));
    } else {
      this.subscribeToSaveResponse(this.afterEffectsTemplateService.create(afterEffectsTemplate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAfterEffectsTemplate>>): void {
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

  protected updateForm(afterEffectsTemplate: IAfterEffectsTemplate): void {
    this.afterEffectsTemplate = afterEffectsTemplate;
    this.afterEffectsTemplateFormService.resetForm(this.editForm, afterEffectsTemplate);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      afterEffectsTemplate.company
    );
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      afterEffectsTemplate.category
    );
    this.afterEffectsTemplateAssetsSharedCollection =
      this.afterEffectsTemplateAssetsService.addAfterEffectsTemplateAssetsToCollectionIfMissing<IAfterEffectsTemplateAssets>(
        this.afterEffectsTemplateAssetsSharedCollection,
        afterEffectsTemplate.afterEffectsTemplateAssets
      );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) =>
          this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.afterEffectsTemplate?.company)
        )
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing<ICategory>(categories, this.afterEffectsTemplate?.category)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.afterEffectsTemplateAssetsService
      .query()
      .pipe(map((res: HttpResponse<IAfterEffectsTemplateAssets[]>) => res.body ?? []))
      .pipe(
        map((afterEffectsTemplateAssets: IAfterEffectsTemplateAssets[]) =>
          this.afterEffectsTemplateAssetsService.addAfterEffectsTemplateAssetsToCollectionIfMissing<IAfterEffectsTemplateAssets>(
            afterEffectsTemplateAssets,
            this.afterEffectsTemplate?.afterEffectsTemplateAssets
          )
        )
      )
      .subscribe(
        (afterEffectsTemplateAssets: IAfterEffectsTemplateAssets[]) =>
          (this.afterEffectsTemplateAssetsSharedCollection = afterEffectsTemplateAssets)
      );
  }
}
