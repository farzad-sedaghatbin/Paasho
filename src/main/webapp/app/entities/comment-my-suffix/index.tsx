import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommentMySuffix from './comment-my-suffix';
import CommentMySuffixDetail from './comment-my-suffix-detail';
import CommentMySuffixUpdate from './comment-my-suffix-update';
import CommentMySuffixDeleteDialog from './comment-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommentMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommentMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CommentMySuffixDeleteDialog} />
  </>
);

export default Routes;
