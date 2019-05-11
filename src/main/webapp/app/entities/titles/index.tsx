import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Titles from './titles';
import TitlesDetail from './titles-detail';
import TitlesUpdate from './titles-update';
import TitlesDeleteDialog from './titles-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TitlesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TitlesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TitlesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Titles} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TitlesDeleteDialog} />
  </>
);

export default Routes;
