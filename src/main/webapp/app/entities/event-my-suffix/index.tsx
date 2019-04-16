import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventMySuffix from './event-my-suffix';
import EventMySuffixDetail from './event-my-suffix-detail';
import EventMySuffixUpdate from './event-my-suffix-update';
import EventMySuffixDeleteDialog from './event-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EventMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EventMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EventMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={EventMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EventMySuffixDeleteDialog} />
  </>
);

export default Routes;
