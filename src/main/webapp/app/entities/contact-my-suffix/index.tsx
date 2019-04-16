import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContactMySuffix from './contact-my-suffix';
import ContactMySuffixDetail from './contact-my-suffix-detail';
import ContactMySuffixUpdate from './contact-my-suffix-update';
import ContactMySuffixDeleteDialog from './contact-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContactMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContactMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContactMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContactMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ContactMySuffixDeleteDialog} />
  </>
);

export default Routes;
