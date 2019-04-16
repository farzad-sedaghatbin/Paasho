import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEventPaasho, defaultValue } from 'app/shared/model/event-paasho.model';

export const ACTION_TYPES = {
  SEARCH_EVENTS: 'event/SEARCH_EVENTS',
  FETCH_EVENT_LIST: 'event/FETCH_EVENT_LIST',
  FETCH_EVENT: 'event/FETCH_EVENT',
  CREATE_EVENT: 'event/CREATE_EVENT',
  UPDATE_EVENT: 'event/UPDATE_EVENT',
  DELETE_EVENT: 'event/DELETE_EVENT',
  RESET: 'event/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEventPaasho>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EventPaashoState = Readonly<typeof initialState>;

// Reducer

export default (state: EventPaashoState = initialState, action): EventPaashoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_EVENTS):
    case REQUEST(ACTION_TYPES.FETCH_EVENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EVENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EVENT):
    case REQUEST(ACTION_TYPES.UPDATE_EVENT):
    case REQUEST(ACTION_TYPES.DELETE_EVENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_EVENTS):
    case FAILURE(ACTION_TYPES.FETCH_EVENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EVENT):
    case FAILURE(ACTION_TYPES.CREATE_EVENT):
    case FAILURE(ACTION_TYPES.UPDATE_EVENT):
    case FAILURE(ACTION_TYPES.DELETE_EVENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_EVENTS):
    case SUCCESS(ACTION_TYPES.FETCH_EVENT_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_EVENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EVENT):
    case SUCCESS(ACTION_TYPES.UPDATE_EVENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EVENT):
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

const apiUrl = 'api/events';
const apiSearchUrl = 'api/_search/events';

// Actions

export const getSearchEntities: ICrudSearchAction<IEventPaasho> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_EVENTS,
  payload: axios.get<IEventPaasho>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IEventPaasho> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EVENT_LIST,
    payload: axios.get<IEventPaasho>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEventPaasho> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EVENT,
    payload: axios.get<IEventPaasho>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEventPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EVENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IEventPaasho> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EVENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEventPaasho> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EVENT,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
