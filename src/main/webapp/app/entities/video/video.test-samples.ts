import { IVideo, NewVideo } from './video.model';

export const sampleWithRequiredData: IVideo = {
  id: 25330,
};

export const sampleWithPartialData: IVideo = {
  id: 47349,
  videoSize: 'bi-directional Cotton',
  videoPublicUrl: 'optimal Mexico Garden',
  videoVisibleFor: 'Bacon',
};

export const sampleWithFullData: IVideo = {
  id: 89580,
  videoName: 'application Car 1080p',
  videoDuration: 'modular compressing bypass',
  videoSize: 'Cheese hack Jewelery',
  videoCategory: 'Security Michigan',
  videoRating: 'Savings multi-byte',
  videoStatus: 'Buckinghamshire SSL',
  videoPublicUrl: 'Generic',
  videoPath: 'Fantastic De-engineered Austria',
  videoVisibleFor: 'Fresh Connecticut azure',
};

export const sampleWithNewData: NewVideo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
