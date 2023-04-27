import { IAfterEffectsTemplate } from 'app/entities/after-effects-template/after-effects-template.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';

export interface IVideo {
  id: number;
  videoName?: string | null;
  videoDuration?: string | null;
  videoSize?: string | null;
  videoCategory?: string | null;
  videoRating?: string | null;
  videoStatus?: string | null;
  videoPublicUrl?: string | null;
  videoPath?: string | null;
  videoVisibleFor?: string | null;
  afterEffectsTemplate?: Pick<IAfterEffectsTemplate, 'id'> | null;
  user?: Pick<IAppUser, 'id'> | null;
}

export type NewVideo = Omit<IVideo, 'id'> & { id: null };
