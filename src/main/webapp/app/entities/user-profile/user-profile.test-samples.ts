import { ActivityLevel } from 'app/entities/enumerations/activity-level.model';
import { Diet } from 'app/entities/enumerations/diet.model';

import { IUserProfile, NewUserProfile } from './user-profile.model';

export const sampleWithRequiredData: IUserProfile = {
  id: 10373,
  userStreak: 55443,
};

export const sampleWithPartialData: IUserProfile = {
  id: 65606,
  firstName: 'Agustin',
  lastName: 'Lueilwitz',
  userStreak: 87530,
  activityLevel: ActivityLevel['LOW'],
};

export const sampleWithFullData: IUserProfile = {
  id: 93499,
  firstName: 'Bobby',
  lastName: 'Keeling',
  userStreak: 46367,
  activityLevel: ActivityLevel['LOW'],
  diet: Diet['VEGETARIAN'],
  cooks: false,
  drives: false,
};

export const sampleWithNewData: NewUserProfile = {
  userStreak: 3089,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
