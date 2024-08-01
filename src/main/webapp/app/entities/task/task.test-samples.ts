import dayjs from 'dayjs/esm';

import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: 37978,
  name: 'CSS Account Up-sized',
  streak: 5478,
  targetStreak: 30677,
};

export const sampleWithPartialData: ITask = {
  id: 69422,
  name: 'Research structure back-end',
  description: 'Planner programming virtual',
  startDate: dayjs('2024-08-01'),
  streak: 1181,
  targetStreak: 98513,
};

export const sampleWithFullData: ITask = {
  id: 72019,
  name: 'Cambridgeshire invoice Concrete',
  description: 'Decentralized Oklahoma Account',
  startDate: dayjs('2024-08-01'),
  streak: 73907,
  targetStreak: 98846,
};

export const sampleWithNewData: NewTask = {
  name: 'streamline Clothing',
  streak: 59867,
  targetStreak: 63277,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
