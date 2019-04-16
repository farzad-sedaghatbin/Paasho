import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICommentMySuffix, defaultValue } from 'app/shared/model/comment-my-suffix.model';

export const ACTION_TYPES = {
  SEARCH_COMMENTS: 'comment/SEARCH_COMMENTS',
  FETCH_COMMENT_LIST: 'comment/FETCH_COMMENT_LIST',
  FETCH_COMMENT: 'comment/FETCH_COMMENT',
  CREATE_COMMENT: 'comment/CREATE_COMMENT',
  UPDATE_COMMENT: 'comment/UPDATE_COMMENT',
  DELETE_COMMENT: 'comment/DELETE_COMMENT',
  RESET: 'comment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICommentMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CommentMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: CommentMySuffixState = initialState, action): CommentMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_COMMENTS):
    case REQUEST(ACTION_TYPES.FETCH_COMMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COMMENT):
    case REQUEST(ACTION_TYPES.UPDATE_COMMENT):
    case REQUEST(ACTION_TYPES.DELETE_COMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_COMMENTS):
    case FAILURE(ACTION_TYPES.FETCH_COMMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMMENT):
    case FAILURE(ACTION_TYPES.CREATE_COMMENT):
    case FAILURE(ACTION_TYPES.UPDATE_COMMENT):
    case FAILURE(ACTION_TYPES.DELETE_COMMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_COMMENTS):
    case SUCCESS(ACTION_TYPES.FETCH_COMMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_COMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMMENT):
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

const apiUrl = 'api/comments';
const apiSearchUrl = 'api/_search/comments';

// Actions

export const getSearchEntities: ICrudSearchAction<ICommentMySuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_COMMENTS,
  payload: axios.get<ICommentMySuffix>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ICommentMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMMENT_LIST,
  payload: axios.get<ICommentMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICommentMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMMENT,
    payload: axios.get<ICommentMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICommentMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICommentMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICommentMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
