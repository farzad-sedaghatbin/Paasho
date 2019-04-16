import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEventMySuffix } from 'app/shared/model/event-my-suffix.model';
import { getEntities as getEvents } from 'app/entities/event-my-suffix/event-my-suffix.reducer';
import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';
import { getEntities as getUser1S } from 'app/entities/user-1-my-suffix/user-1-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rating-my-suffix.reducer';
import { IRatingMySuffix } from 'app/shared/model/rating-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRatingMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRatingMySuffixUpdateState {
  isNew: boolean;
  eventId: string;
  userId: string;
}

export class RatingMySuffixUpdate extends React.Component<IRatingMySuffixUpdateProps, IRatingMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      eventId: '0',
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

    this.props.getEvents();
    this.props.getUser1S();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ratingEntity } = this.props;
      const entity = {
        ...ratingEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/rating-my-suffix');
  };

  render() {
    const { ratingEntity, events, user1S, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="paashoApp.rating.home.createOrEditLabel">Create or edit a Rating</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ratingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="rating-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="rateLabel" for="rate">
                    Rate
                  </Label>
                  <AvField id="rating-my-suffix-rate" type="string" className="form-control" name="rate" />
                </AvGroup>
                <AvGroup>
                  <Label for="event.id">Event</Label>
                  <AvInput id="rating-my-suffix-event" type="select" className="form-control" name="eventId">
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
                  <AvInput id="rating-my-suffix-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {user1S
                      ? user1S.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/rating-my-suffix" replace color="info">
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
  events: storeState.event.entities,
  user1S: storeState.user1.entities,
  ratingEntity: storeState.rating.entity,
  loading: storeState.rating.loading,
  updating: storeState.rating.updating,
  updateSuccess: storeState.rating.updateSuccess
});

const mapDispatchToProps = {
  getEvents,
  getUser1S,
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
)(RatingMySuffixUpdate);
