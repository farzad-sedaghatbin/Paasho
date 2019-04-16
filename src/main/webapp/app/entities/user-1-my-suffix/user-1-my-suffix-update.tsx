import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICategoryMySuffix } from 'app/shared/model/category-my-suffix.model';
import { getEntities as getCategories } from 'app/entities/category-my-suffix/category-my-suffix.reducer';
import { IEventMySuffix } from 'app/shared/model/event-my-suffix.model';
import { getEntities as getEvents } from 'app/entities/event-my-suffix/event-my-suffix.reducer';
import { INotificationMySuffix } from 'app/shared/model/notification-my-suffix.model';
import { getEntities as getNotifications } from 'app/entities/notification-my-suffix/notification-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-1-my-suffix.reducer';
import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUser1MySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUser1MySuffixUpdateState {
  isNew: boolean;
  idsfavorits: any[];
  userId: string;
  userId: string;
  userId: string;
}

export class User1MySuffixUpdate extends React.Component<IUser1MySuffixUpdateProps, IUser1MySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsfavorits: [],
      userId: '0',
      userId: '0',
      userId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCategories();
    this.props.getEvents();
    this.props.getNotifications();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { user1Entity } = this.props;
      const entity = {
        ...user1Entity,
        ...values,
        favorits: mapIdList(values.favorits)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/user-1-my-suffix');
  };

  render() {
    const { user1Entity, categories, events, notifications, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="paashoApp.user1.home.createOrEditLabel">Create or edit a User1</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : user1Entity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="user-1-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="user-1-my-suffix-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    Last Name
                  </Label>
                  <AvField id="user-1-my-suffix-lastName" type="text" name="lastName" />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel">Gender</Label>
                  <AvInput
                    id="user-1-my-suffix-gender"
                    type="select"
                    className="form-control"
                    name="gender"
                    value={(!isNew && user1Entity.gender) || 'FEMALE'}
                  >
                    <option value="FEMALE">FEMALE</option>
                    <option value="MALE">MALE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="birthDateLabel" for="birthDate">
                    Birth Date
                  </Label>
                  <AvField id="user-1-my-suffix-birthDate" type="text" name="birthDate" />
                </AvGroup>
                <AvGroup>
                  <Label for="categories">Favorits</Label>
                  <AvInput
                    id="user-1-my-suffix-favorits"
                    type="select"
                    multiple
                    className="form-control"
                    name="favorits"
                    value={user1Entity.favorits && user1Entity.favorits.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {categories
                      ? categories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="user.id">User</Label>
                  <AvInput id="user-1-my-suffix-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {events
                      ? events.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="user.id">User</Label>
                  <AvInput id="user-1-my-suffix-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {notifications
                      ? notifications.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-1-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  categories: storeState.category.entities,
  events: storeState.event.entities,
  notifications: storeState.notification.entities,
  user1Entity: storeState.user1.entity,
  loading: storeState.user1.loading,
  updating: storeState.user1.updating,
  updateSuccess: storeState.user1.updateSuccess
});

const mapDispatchToProps = {
  getCategories,
  getEvents,
  getNotifications,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(User1MySuffixUpdate);
