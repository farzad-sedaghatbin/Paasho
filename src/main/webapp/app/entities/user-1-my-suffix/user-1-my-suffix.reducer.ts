import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUser1MySuffix, defaultValue } from 'app/shared/model/user-1-my-suffix.model';

export const ACTION_TYPES = {
  SEARCH_USER1S: 'user1/SEARCH_USER1S',
  FETCH_USER1_LIST: 'user1/FETCH_USER1_LIST',
  FETCH_USER1: 'user1/FETCH_USER1',
  CREATE_USER1: 'user1/CREATE_USER1',
  UPDATE_USER1: 'user1/UPDATE_USER1',
  DELETE_USER1: 'user1/DELETE_USER1',
  RESET: 'user1/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUser1MySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type User1MySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: User1MySuffixState = initialState, action): User1MySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_USER1S):
    case REQUEST(ACTION_TYPES.FETCH_USER1_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USER1):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USER1):
    case REQUEST(ACTION_TYPES.UPDATE_USER1):
    case REQUEST(ACTION_TYPES.DELETE_USER1):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_USER1S):
    case FAILURE(ACTION_TYPES.FETCH_USER1_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USER1):
    case FAILURE(ACTION_TYPES.CREATE_USER1):
    case FAILURE(ACTION_TYPES.UPDATE_USER1):
    case FAILURE(ACTION_TYPES.DELETE_USER1):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_USER1S):
    case SUCCESS(ACTION_TYPES.FETCH_USER1_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USER1):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USER1):
    case SUCCESS(ACTION_TYPES.UPDATE_USER1):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USER1):
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

const apiUrl = 'api/user-1-s';
const apiSearchUrl = 'api/_search/user-1-s';

// Actions

export const getSearchEntities: ICrudSearchAction<IUser1MySuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_USER1S,
  payload: axios.get<IUser1MySuffix>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IUser1MySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USER1_LIST,
  payload: axios.get<IUser1MySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IUser1MySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USER1,
    payload: axios.get<IUser1MySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUser1MySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USER1,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUser1MySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USER1,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUser1MySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USER1,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
