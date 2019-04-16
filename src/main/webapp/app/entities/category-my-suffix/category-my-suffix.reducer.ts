import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICategoryMySuffix, defaultValue } from 'app/shared/model/category-my-suffix.model';

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
  entities: [] as ReadonlyArray<ICategoryMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CategoryMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: CategoryMySuffixState = initialState, action): CategoryMySuffixState => {
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

export const getSearchEntities: ICrudSearchAction<ICategoryMySuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CATEGORIES,
  payload: axios.get<ICategoryMySuffix>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ICategoryMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CATEGORY_LIST,
  payload: axios.get<ICategoryMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICategoryMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CATEGORY,
    payload: axios.get<ICategoryMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICategoryMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICategoryMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICategoryMySuffix> = id => async dispatch => {
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
