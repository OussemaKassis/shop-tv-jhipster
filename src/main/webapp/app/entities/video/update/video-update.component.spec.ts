import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VideoFormService } from './video-form.service';
import { VideoService } from '../service/video.service';
import { IVideo } from '../video.model';
import { IAfterEffectsTemplate } from 'app/entities/after-effects-template/after-effects-template.model';
import { AfterEffectsTemplateService } from 'app/entities/after-effects-template/service/after-effects-template.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';

import { VideoUpdateComponent } from './video-update.component';

describe('Video Management Update Component', () => {
  let comp: VideoUpdateComponent;
  let fixture: ComponentFixture<VideoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let videoFormService: VideoFormService;
  let videoService: VideoService;
  let afterEffectsTemplateService: AfterEffectsTemplateService;
  let appUserService: AppUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VideoUpdateComponent],
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
      .overrideTemplate(VideoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VideoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    videoFormService = TestBed.inject(VideoFormService);
    videoService = TestBed.inject(VideoService);
    afterEffectsTemplateService = TestBed.inject(AfterEffectsTemplateService);
    appUserService = TestBed.inject(AppUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AfterEffectsTemplate query and add missing value', () => {
      const video: IVideo = { id: 456 };
      const afterEffectsTemplate: IAfterEffectsTemplate = { id: 82649 };
      video.afterEffectsTemplate = afterEffectsTemplate;

      const afterEffectsTemplateCollection: IAfterEffectsTemplate[] = [{ id: 47242 }];
      jest.spyOn(afterEffectsTemplateService, 'query').mockReturnValue(of(new HttpResponse({ body: afterEffectsTemplateCollection })));
      const additionalAfterEffectsTemplates = [afterEffectsTemplate];
      const expectedCollection: IAfterEffectsTemplate[] = [...additionalAfterEffectsTemplates, ...afterEffectsTemplateCollection];
      jest.spyOn(afterEffectsTemplateService, 'addAfterEffectsTemplateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ video });
      comp.ngOnInit();

      expect(afterEffectsTemplateService.query).toHaveBeenCalled();
      expect(afterEffectsTemplateService.addAfterEffectsTemplateToCollectionIfMissing).toHaveBeenCalledWith(
        afterEffectsTemplateCollection,
        ...additionalAfterEffectsTemplates.map(expect.objectContaining)
      );
      expect(comp.afterEffectsTemplatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AppUser query and add missing value', () => {
      const video: IVideo = { id: 456 };
      const user: IAppUser = { id: 8238 };
      video.user = user;

      const appUserCollection: IAppUser[] = [{ id: 74493 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [user];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ video });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining)
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const video: IVideo = { id: 456 };
      const afterEffectsTemplate: IAfterEffectsTemplate = { id: 64576 };
      video.afterEffectsTemplate = afterEffectsTemplate;
      const user: IAppUser = { id: 82404 };
      video.user = user;

      activatedRoute.data = of({ video });
      comp.ngOnInit();

      expect(comp.afterEffectsTemplatesSharedCollection).toContain(afterEffectsTemplate);
      expect(comp.appUsersSharedCollection).toContain(user);
      expect(comp.video).toEqual(video);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideo>>();
      const video = { id: 123 };
      jest.spyOn(videoFormService, 'getVideo').mockReturnValue(video);
      jest.spyOn(videoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ video });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: video }));
      saveSubject.complete();

      // THEN
      expect(videoFormService.getVideo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(videoService.update).toHaveBeenCalledWith(expect.objectContaining(video));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideo>>();
      const video = { id: 123 };
      jest.spyOn(videoFormService, 'getVideo').mockReturnValue({ id: null });
      jest.spyOn(videoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ video: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: video }));
      saveSubject.complete();

      // THEN
      expect(videoFormService.getVideo).toHaveBeenCalled();
      expect(videoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideo>>();
      const video = { id: 123 };
      jest.spyOn(videoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ video });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(videoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAfterEffectsTemplate', () => {
      it('Should forward to afterEffectsTemplateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(afterEffectsTemplateService, 'compareAfterEffectsTemplate');
        comp.compareAfterEffectsTemplate(entity, entity2);
        expect(afterEffectsTemplateService.compareAfterEffectsTemplate).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAppUser', () => {
      it('Should forward to appUserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(appUserService, 'compareAppUser');
        comp.compareAppUser(entity, entity2);
        expect(appUserService.compareAppUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
