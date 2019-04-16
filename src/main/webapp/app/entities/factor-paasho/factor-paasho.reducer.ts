import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFactorPaasho, defaultValue } from 'app/shared/model/factor-paasho.model';

export const ACTION_TYPES = {
  SEARCH_FACTORS: 'factor/SEARCH_FACTORS',
  FETCH_FACTOR_LIST: 'factor/FETCH_FACTOR_LIST',
  FETCH_FACTOR: 'factor/FETCH_FACTOR',
  CREATE_FACTOR: 'factor/CREATE_FACTOR',
  UPDATE_FACTOR: 'factor/UPDATE_FACTOR',
  DELETE_FACTOR: 'factor/DELETE_FACTOR',
  RESET: 'factor/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFactorPaasho>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FactorPaashoState = Readonly<typeof initialState>;

// Reducer

export default (state: FactorPaashoState = initialState, action): FactorPaashoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FACTORS):
    case REQUEST(ACTION_TYPES.FETCH_FACTOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FACTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FACTOR):
    case REQUEST(ACTION_TYPES.UPDATE_FACTOR):
    case REQUEST(ACTION_TYPES.DELETE_FACTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_FACTORS):
    case FAILURE(ACTION_TYPES.FETCH_FACTOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FACTOR):
    case FAILURE(ACTION_TYPES.CREATE_FACTOR):
    case FAILURE(ACTION_TYPES.UPDATE_FACTOR):
    case FAILURE(ACTION_TYPES.DELETE_FACTOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FACTORS):
    case SUCCESS(ACTION_TYPES.FETCH_FACTOR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FACTOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FACTOR):
    case SUCCESS(ACTION_TYPES.UPDATE_FACTOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FACTOR):
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

const apiUrl = 'api/factors';
const apiSearchUrl = 'api/_search/factors';

// Actions

export const getSearchEntities: ICrudSearchAction<IFactorPaasho> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FACTORS,
  payload: axios.get<IFactorPaasho>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IFactorPaasho> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FACTOR_LIST,
  payload: axios.get<IFactorPaasho>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFactorPaasho> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FACTOR,
    payload: axios.get<IFactorPaasho>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFactorPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FACTOR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFactorPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FACTOR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFactorPaasho> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FACTOR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
