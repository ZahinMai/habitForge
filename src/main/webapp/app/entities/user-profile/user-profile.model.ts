import { IUser } from 'app/entities/user/user.model';
import { ActivityLevel } from 'app/entities/enumerations/activity-level.model';
import { Diet } from 'app/entities/enumerations/diet.model';

export interface IUserProfile {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  userStreak?: number | null;
  activityLevel?: ActivityLevel | null;
  diet?: Diet | null;
  cooks?: boolean | null;
  drives?: boolean | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewUserProfile = Omit<IUserProfile, 'id'> & { id: null };
