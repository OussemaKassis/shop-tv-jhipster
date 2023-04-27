import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVideo, NewVideo } from '../video.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVideo for edit and NewVideoFormGroupInput for create.
 */
type VideoFormGroupInput = IVideo | PartialWithRequiredKeyOf<NewVideo>;

type VideoFormDefaults = Pick<NewVideo, 'id'>;

type VideoFormGroupContent = {
  id: FormControl<IVideo['id'] | NewVideo['id']>;
  videoName: FormControl<IVideo['videoName']>;
  videoDuration: FormControl<IVideo['videoDuration']>;
  videoSize: FormControl<IVideo['videoSize']>;
  videoCategory: FormControl<IVideo['videoCategory']>;
  videoRating: FormControl<IVideo['videoRating']>;
  videoStatus: FormControl<IVideo['videoStatus']>;
  videoPublicUrl: FormControl<IVideo['videoPublicUrl']>;
  videoPath: FormControl<IVideo['videoPath']>;
  videoVisibleFor: FormControl<IVideo['videoVisibleFor']>;
  afterEffectsTemplate: FormControl<IVideo['afterEffectsTemplate']>;
  user: FormControl<IVideo['user']>;
};

export type VideoFormGroup = FormGroup<VideoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VideoFormService {
  createVideoFormGroup(video: VideoFormGroupInput = { id: null }): VideoFormGroup {
    const videoRawValue = {
      ...this.getFormDefaults(),
      ...video,
    };
    return new FormGroup<VideoFormGroupContent>({
      id: new FormControl(
        { value: videoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      videoName: new FormControl(videoRawValue.videoName),
      videoDuration: new FormControl(videoRawValue.videoDuration),
      videoSize: new FormControl(videoRawValue.videoSize),
      videoCategory: new FormControl(videoRawValue.videoCategory),
      videoRating: new FormControl(videoRawValue.videoRating),
      videoStatus: new FormControl(videoRawValue.videoStatus),
      videoPublicUrl: new FormControl(videoRawValue.videoPublicUrl),
      videoPath: new FormControl(videoRawValue.videoPath),
      videoVisibleFor: new FormControl(videoRawValue.videoVisibleFor),
      afterEffectsTemplate: new FormControl(videoRawValue.afterEffectsTemplate),
      user: new FormControl(videoRawValue.user),
    });
  }

  getVideo(form: VideoFormGroup): IVideo | NewVideo {
    return form.getRawValue() as IVideo | NewVideo;
  }

  resetForm(form: VideoFormGroup, video: VideoFormGroupInput): void {
    const videoRawValue = { ...this.getFormDefaults(), ...video };
    form.reset(
      {
        ...videoRawValue,
        id: { value: videoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VideoFormDefaults {
    return {
      id: null,
    };
  }
}
