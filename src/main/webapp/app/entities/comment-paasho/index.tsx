import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommentPaasho from './comment-paasho';
import CommentPaashoDetail from './comment-paasho-detail';
import CommentPaashoUpdate from './comment-paasho-update';
import CommentPaashoDeleteDialog from './comment-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommentPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommentPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommentPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommentPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CommentPaashoDeleteDialog} />
  </>
);

export default Routes;
