import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INotificationMySuffix, defaultValue } from 'app/shared/model/notification-my-suffix.model';

export const ACTION_TYPES = {
  SEARCH_NOTIFICATIONS: 'notification/SEARCH_NOTIFICATIONS',
  FETCH_NOTIFICATION_LIST: 'notification/FETCH_NOTIFICATION_LIST',
  FETCH_NOTIFICATION: 'notification/FETCH_NOTIFICATION',
  CREATE_NOTIFICATION: 'notification/CREATE_NOTIFICATION',
  UPDATE_NOTIFICATION: 'notification/UPDATE_NOTIFICATION',
  DELETE_NOTIFICATION: 'notification/DELETE_NOTIFICATION',
  RESET: 'notification/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INotificationMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NotificationMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: NotificationMySuffixState = initialState, action): NotificationMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_NOTIFICATIONS):
    case REQUEST(ACTION_TYPES.FETCH_NOTIFICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NOTIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NOTIFICATION):
    case REQUEST(ACTION_TYPES.UPDATE_NOTIFICATION):
    case REQUEST(ACTION_TYPES.DELETE_NOTIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_NOTIFICATIONS):
    case FAILURE(ACTION_TYPES.FETCH_NOTIFICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NOTIFICATION):
    case FAILURE(ACTION_TYPES.CREATE_NOTIFICATION):
    case FAILURE(ACTION_TYPES.UPDATE_NOTIFICATION):
    case FAILURE(ACTION_TYPES.DELETE_NOTIFICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_NOTIFICATIONS):
    case SUCCESS(ACTION_TYPES.FETCH_NOTIFICATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NOTIFICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NOTIFICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_NOTIFICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NOTIFICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/notifications';
const apiSearchUrl = 'api/_search/notifications';

// Actions

export const getSearchEntities: ICrudSearchAction<INotificationMySuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_NOTIFICATIONS,
  payload: axios.get<INotificationMySuffix>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<INotificationMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NOTIFICATION_LIST,
  payload: axios.get<INotificationMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INotificationMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NOTIFICATION,
    payload: axios.get<INotificationMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INotificationMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NOTIFICATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INotificationMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NOTIFICATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INotificationMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NOTIFICATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
