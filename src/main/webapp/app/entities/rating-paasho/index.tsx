import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RatingPaasho from './rating-paasho';
import RatingPaashoDetail from './rating-paasho-detail';
import RatingPaashoUpdate from './rating-paasho-update';
import RatingPaashoDeleteDialog from './rating-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RatingPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RatingPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RatingPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={RatingPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RatingPaashoDeleteDialog} />
  </>
);

export default Routes;
