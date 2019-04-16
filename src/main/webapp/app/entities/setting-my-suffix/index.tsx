import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SettingMySuffix from './setting-my-suffix';
import SettingMySuffixDetail from './setting-my-suffix-detail';
import SettingMySuffixUpdate from './setting-my-suffix-update';
import SettingMySuffixDeleteDialog from './setting-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SettingMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SettingMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SettingMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={SettingMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SettingMySuffixDeleteDialog} />
  </>
);

export default Routes;
