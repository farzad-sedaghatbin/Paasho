import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './setting-my-suffix.reducer';
import { ISettingMySuffix } from 'app/shared/model/setting-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISettingMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SettingMySuffixDetail extends React.Component<ISettingMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { settingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Setting [<b>{settingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="key">Key</span>
            </dt>
            <dd>{settingEntity.key}</dd>
            <dt>
              <span id="value">Value</span>
            </dt>
            <dd>{settingEntity.value}</dd>
          </dl>
          <Button tag={Link} to="/entity/setting-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/setting-my-suffix/${settingEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ setting }: IRootState) => ({
  settingEntity: setting.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SettingMySuffixDetail);
