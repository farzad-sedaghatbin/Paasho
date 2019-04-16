import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRatingMySuffix, defaultValue } from 'app/shared/model/rating-my-suffix.model';

export const ACTION_TYPES = {
  SEARCH_RATINGS: 'rating/SEARCH_RATINGS',
  FETCH_RATING_LIST: 'rating/FETCH_RATING_LIST',
  FETCH_RATING: 'rating/FETCH_RATING',
  CREATE_RATING: 'rating/CREATE_RATING',
  UPDATE_RATING: 'rating/UPDATE_RATING',
  DELETE_RATING: 'rating/DELETE_RATING',
  RESET: 'rating/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRatingMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RatingMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: RatingMySuffixState = initialState, action): RatingMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_RATINGS):
    case REQUEST(ACTION_TYPES.FETCH_RATING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RATING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RATING):
    case REQUEST(ACTION_TYPES.UPDATE_RATING):
    case REQUEST(ACTION_TYPES.DELETE_RATING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_RATINGS):
    case FAILURE(ACTION_TYPES.FETCH_RATING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RATING):
    case FAILURE(ACTION_TYPES.CREATE_RATING):
    case FAILURE(ACTION_TYPES.UPDATE_RATING):
    case FAILURE(ACTION_TYPES.DELETE_RATING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_RATINGS):
    case SUCCESS(ACTION_TYPES.FETCH_RATING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RATING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RATING):
    case SUCCESS(ACTION_TYPES.UPDATE_RATING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RATING):
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

const apiUrl = 'api/ratings';
const apiSearchUrl = 'api/_search/ratings';

// Actions

export const getSearchEntities: ICrudSearchAction<IRatingMySuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_RATINGS,
  payload: axios.get<IRatingMySuffix>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IRatingMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RATING_LIST,
  payload: axios.get<IRatingMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRatingMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RATING,
    payload: axios.get<IRatingMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRatingMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RATING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRatingMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RATING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRatingMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RATING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
