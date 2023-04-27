import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { VideoFormService, VideoFormGroup } from './video-form.service';
import { IVideo } from '../video.model';
import { VideoService } from '../service/video.service';
import { IAfterEffectsTemplate } from 'app/entities/after-effects-template/after-effects-template.model';
import { AfterEffectsTemplateService } from 'app/entities/after-effects-template/service/after-effects-template.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';

@Component({
  selector: 'jhi-video-update',
  templateUrl: './video-update.component.html',
})
export class VideoUpdateComponent implements OnInit {
  isSaving = false;
  video: IVideo | null = null;

  afterEffectsTemplatesSharedCollection: IAfterEffectsTemplate[] = [];
  appUsersSharedCollection: IAppUser[] = [];

  editForm: VideoFormGroup = this.videoFormService.createVideoFormGroup();

  constructor(
    protected videoService: VideoService,
    protected videoFormService: VideoFormService,
    protected afterEffectsTemplateService: AfterEffectsTemplateService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAfterEffectsTemplate = (o1: IAfterEffectsTemplate | null, o2: IAfterEffectsTemplate | null): boolean =>
    this.afterEffectsTemplateService.compareAfterEffectsTemplate(o1, o2);

  compareAppUser = (o1: IAppUser | null, o2: IAppUser | null): boolean => this.appUserService.compareAppUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ video }) => {
      this.video = video;
      if (video) {
        this.updateForm(video);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const video = this.videoFormService.getVideo(this.editForm);
    if (video.id !== null) {
      this.subscribeToSaveResponse(this.videoService.update(video));
    } else {
      this.subscribeToSaveResponse(this.videoService.create(video));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideo>>): void {
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

  protected updateForm(video: IVideo): void {
    this.video = video;
    this.videoFormService.resetForm(this.editForm, video);

    this.afterEffectsTemplatesSharedCollection =
      this.afterEffectsTemplateService.addAfterEffectsTemplateToCollectionIfMissing<IAfterEffectsTemplate>(
        this.afterEffectsTemplatesSharedCollection,
        video.afterEffectsTemplate
      );
    this.appUsersSharedCollection = this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(
      this.appUsersSharedCollection,
      video.user
    );
  }

  protected loadRelationshipsOptions(): void {
    this.afterEffectsTemplateService
      .query()
      .pipe(map((res: HttpResponse<IAfterEffectsTemplate[]>) => res.body ?? []))
      .pipe(
        map((afterEffectsTemplates: IAfterEffectsTemplate[]) =>
          this.afterEffectsTemplateService.addAfterEffectsTemplateToCollectionIfMissing<IAfterEffectsTemplate>(
            afterEffectsTemplates,
            this.video?.afterEffectsTemplate
          )
        )
      )
      .subscribe((afterEffectsTemplates: IAfterEffectsTemplate[]) => (this.afterEffectsTemplatesSharedCollection = afterEffectsTemplates));

    this.appUserService
      .query()
      .pipe(map((res: HttpResponse<IAppUser[]>) => res.body ?? []))
      .pipe(map((appUsers: IAppUser[]) => this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(appUsers, this.video?.user)))
      .subscribe((appUsers: IAppUser[]) => (this.appUsersSharedCollection = appUsers));
  }
}
