import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICategoryPaasho } from 'app/shared/model/category-paasho.model';
import { getEntities as getCategories } from 'app/entities/category-paasho/category-paasho.reducer';
import { IEventPaasho } from 'app/shared/model/event-paasho.model';
import { getEntities as getEvents } from 'app/entities/event-paasho/event-paasho.reducer';
import { INotificationPaasho } from 'app/shared/model/notification-paasho.model';
import { getEntities as getNotifications } from 'app/entities/notification-paasho/notification-paasho.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-1-paasho.reducer';
import { IUser1Paasho } from 'app/shared/model/user-1-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUser1PaashoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUser1PaashoUpdateState {
  isNew: boolean;
  idsfavorits: any[];
  eventId: string;
  eventsId: string;
  notificationId: string;
}

export class User1PaashoUpdate extends React.Component<IUser1PaashoUpdateProps, IUser1PaashoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsfavorits: [],
      eventId: '0',
      eventsId: '0',
      notificationId: '0',
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
    this.props.history.push('/entity/user-1-paasho');
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
                    <AvInput id="user-1-paasho-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="user-1-paasho-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    Last Name
                  </Label>
                  <AvField id="user-1-paasho-lastName" type="text" name="lastName" />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel">Gender</Label>
                  <AvInput
                    id="user-1-paasho-gender"
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
                  <AvField id="user-1-paasho-birthDate" type="text" name="birthDate" />
                </AvGroup>
                <AvGroup>
                  <Label for="categories">Favorits</Label>
                  <AvInput
                    id="user-1-paasho-favorits"
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
                  <Label for="notification.id">Notification</Label>
                  <AvInput id="user-1-paasho-notification" type="select" className="form-control" name="notificationId">
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
                <Button tag={Link} id="cancel-save" to="/entity/user-1-paasho" replace color="info">
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
)(User1PaashoUpdate);
