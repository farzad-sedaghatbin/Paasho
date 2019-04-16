import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CategoryMySuffix from './category-my-suffix';
import CategoryMySuffixDetail from './category-my-suffix-detail';
import CategoryMySuffixUpdate from './category-my-suffix-update';
import CategoryMySuffixDeleteDialog from './category-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CategoryMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CategoryMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CategoryMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CategoryMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CategoryMySuffixDeleteDialog} />
  </>
);

export default Routes;
