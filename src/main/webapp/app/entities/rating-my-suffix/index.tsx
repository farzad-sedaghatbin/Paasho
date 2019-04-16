import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RatingMySuffix from './rating-my-suffix';
import RatingMySuffixDetail from './rating-my-suffix-detail';
import RatingMySuffixUpdate from './rating-my-suffix-update';
import RatingMySuffixDeleteDialog from './rating-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RatingMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RatingMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RatingMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={RatingMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RatingMySuffixDeleteDialog} />
  </>
);

export default Routes;
