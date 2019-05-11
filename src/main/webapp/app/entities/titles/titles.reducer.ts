import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITitles, defaultValue } from 'app/shared/model/titles.model';

export const ACTION_TYPES = {
  SEARCH_TITLES: 'titles/SEARCH_TITLES',
  FETCH_TITLES_LIST: 'titles/FETCH_TITLES_LIST',
  FETCH_TITLES: 'titles/FETCH_TITLES',
  CREATE_TITLES: 'titles/CREATE_TITLES',
  UPDATE_TITLES: 'titles/UPDATE_TITLES',
  DELETE_TITLES: 'titles/DELETE_TITLES',
  RESET: 'titles/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITitles>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TitlesState = Readonly<typeof initialState>;

// Reducer

export default (state: TitlesState = initialState, action): TitlesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TITLES):
    case REQUEST(ACTION_TYPES.FETCH_TITLES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TITLES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TITLES):
    case REQUEST(ACTION_TYPES.UPDATE_TITLES):
    case REQUEST(ACTION_TYPES.DELETE_TITLES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TITLES):
    case FAILURE(ACTION_TYPES.FETCH_TITLES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TITLES):
    case FAILURE(ACTION_TYPES.CREATE_TITLES):
    case FAILURE(ACTION_TYPES.UPDATE_TITLES):
    case FAILURE(ACTION_TYPES.DELETE_TITLES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TITLES):
    case SUCCESS(ACTION_TYPES.FETCH_TITLES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TITLES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TITLES):
    case SUCCESS(ACTION_TYPES.UPDATE_TITLES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TITLES):
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

const apiUrl = 'api/titles';
const apiSearchUrl = 'api/_search/titles';

// Actions

export const getSearchEntities: ICrudSearchAction<ITitles> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TITLES,
  payload: axios.get<ITitles>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITitles> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TITLES_LIST,
  payload: axios.get<ITitles>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITitles> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TITLES,
    payload: axios.get<ITitles>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITitles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TITLES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITitles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TITLES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITitles> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TITLES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
