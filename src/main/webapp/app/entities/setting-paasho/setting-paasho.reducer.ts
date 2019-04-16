import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISettingPaasho, defaultValue } from 'app/shared/model/setting-paasho.model';

export const ACTION_TYPES = {
  SEARCH_SETTINGS: 'setting/SEARCH_SETTINGS',
  FETCH_SETTING_LIST: 'setting/FETCH_SETTING_LIST',
  FETCH_SETTING: 'setting/FETCH_SETTING',
  CREATE_SETTING: 'setting/CREATE_SETTING',
  UPDATE_SETTING: 'setting/UPDATE_SETTING',
  DELETE_SETTING: 'setting/DELETE_SETTING',
  RESET: 'setting/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISettingPaasho>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SettingPaashoState = Readonly<typeof initialState>;

// Reducer

export default (state: SettingPaashoState = initialState, action): SettingPaashoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SETTINGS):
    case REQUEST(ACTION_TYPES.FETCH_SETTING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SETTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SETTING):
    case REQUEST(ACTION_TYPES.UPDATE_SETTING):
    case REQUEST(ACTION_TYPES.DELETE_SETTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SETTINGS):
    case FAILURE(ACTION_TYPES.FETCH_SETTING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SETTING):
    case FAILURE(ACTION_TYPES.CREATE_SETTING):
    case FAILURE(ACTION_TYPES.UPDATE_SETTING):
    case FAILURE(ACTION_TYPES.DELETE_SETTING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SETTINGS):
    case SUCCESS(ACTION_TYPES.FETCH_SETTING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SETTING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SETTING):
    case SUCCESS(ACTION_TYPES.UPDATE_SETTING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SETTING):
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

const apiUrl = 'api/settings';
const apiSearchUrl = 'api/_search/settings';

// Actions

export const getSearchEntities: ICrudSearchAction<ISettingPaasho> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SETTINGS,
  payload: axios.get<ISettingPaasho>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ISettingPaasho> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SETTING_LIST,
  payload: axios.get<ISettingPaasho>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISettingPaasho> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SETTING,
    payload: axios.get<ISettingPaasho>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISettingPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SETTING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISettingPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SETTING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISettingPaasho> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SETTING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
