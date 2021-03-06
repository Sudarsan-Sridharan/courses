import * as types from "../constants/actionTypes";
import initialState from "../constants/initialState";

const authorReducer = (state = initialState.authors, action) => {
  switch(action.type) {
    case types.LOAD_AUTHORS_SUCCESS:
      return action.authors;

    case types.CREATE_AUTHOR_SUCCESS:
      return [
        ...state,
        Object.assign({}, action.author)
      ];

    case types.UPDATE_AUTHOR_SUCCESS:
      return [
        ...state.filter(author => author.id !== action.author.id),
        Object.assign({}, action.author)
      ];

    case types.DELETE_AUTHOR_SUCCESS:
      return [
        ...state.filter(author => author.id !== action.author.id)
      ];

    default:
      return state;
  }
};

export default authorReducer;
