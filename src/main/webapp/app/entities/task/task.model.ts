import dayjs from 'dayjs/esm';
import { IUserProfile } from 'app/entities/user-profile/user-profile.model';

export interface ITask {
  id: number;
  name?: string | null;
  description?: string | null;
  startDate?: dayjs.Dayjs | null;
  streak?: number | null;
  targetStreak?: number | null;
  userProfile?: Pick<IUserProfile, 'id'> | null;
}

export type NewTask = Omit<ITask, 'id'> & { id: null };
