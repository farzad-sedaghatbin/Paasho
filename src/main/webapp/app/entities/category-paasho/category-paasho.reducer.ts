import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICategoryPaasho, defaultValue } from 'app/shared/model/category-paasho.model';

export const ACTION_TYPES = {
  SEARCH_CATEGORIES: 'category/SEARCH_CATEGORIES',
  FETCH_CATEGORY_LIST: 'category/FETCH_CATEGORY_LIST',
  FETCH_CATEGORY: 'category/FETCH_CATEGORY',
  CREATE_CATEGORY: 'category/CREATE_CATEGORY',
  UPDATE_CATEGORY: 'category/UPDATE_CATEGORY',
  DELETE_CATEGORY: 'category/DELETE_CATEGORY',
  RESET: 'category/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICategoryPaasho>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CategoryPaashoState = Readonly<typeof initialState>;

// Reducer

export default (state: CategoryPaashoState = initialState, action): CategoryPaashoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CATEGORIES):
    case REQUEST(ACTION_TYPES.FETCH_CATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_CATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_CATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CATEGORIES):
    case FAILURE(ACTION_TYPES.FETCH_CATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_CATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_CATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_CATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CATEGORIES):
    case SUCCESS(ACTION_TYPES.FETCH_CATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_CATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CATEGORY):
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

const apiUrl = 'api/categories';
const apiSearchUrl = 'api/_search/categories';

// Actions

export const getSearchEntities: ICrudSearchAction<ICategoryPaasho> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CATEGORIES,
  payload: axios.get<ICategoryPaasho>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ICategoryPaasho> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CATEGORY_LIST,
  payload: axios.get<ICategoryPaasho>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICategoryPaasho> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CATEGORY,
    payload: axios.get<ICategoryPaasho>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICategoryPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICategoryPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICategoryPaasho> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CATEGORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
