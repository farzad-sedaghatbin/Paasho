import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-1-my-suffix.reducer';
import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUser1MySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class User1MySuffixDetail extends React.Component<IUser1MySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { user1Entity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            User1 [<b>{user1Entity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{user1Entity.name}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{user1Entity.lastName}</dd>
            <dt>
              <span id="gender">Gender</span>
            </dt>
            <dd>{user1Entity.gender}</dd>
            <dt>
              <span id="birthDate">Birth Date</span>
            </dt>
            <dd>{user1Entity.birthDate}</dd>
            <dt>Favorits</dt>
            <dd>
              {user1Entity.favorits
                ? user1Entity.favorits.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === user1Entity.favorits.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>User</dt>
            <dd>{user1Entity.userId ? user1Entity.userId : ''}</dd>
            <dt>User</dt>
            <dd>{user1Entity.userId ? user1Entity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-1-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/user-1-my-suffix/${user1Entity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ user1 }: IRootState) => ({
  user1Entity: user1.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(User1MySuffixDetail);
