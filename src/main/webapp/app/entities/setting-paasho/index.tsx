import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SettingPaasho from './setting-paasho';
import SettingPaashoDetail from './setting-paasho-detail';
import SettingPaashoUpdate from './setting-paasho-update';
import SettingPaashoDeleteDialog from './setting-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SettingPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SettingPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SettingPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={SettingPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SettingPaashoDeleteDialog} />
  </>
);

export default Routes;
