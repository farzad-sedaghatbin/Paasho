import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';
import { getEntities as getUser1S } from 'app/entities/user-1-my-suffix/user-1-my-suffix.reducer';
import { ICategoryMySuffix } from 'app/shared/model/category-my-suffix.model';
import { getEntities as getCategories } from 'app/entities/category-my-suffix/category-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './event-my-suffix.reducer';
import { IEventMySuffix } from 'app/shared/model/event-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEventMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEventMySuffixUpdateState {
  isNew: boolean;
  idscategories: any[];
  creatorId: string;
  participantsId: string;
}

export class EventMySuffixUpdate extends React.Component<IEventMySuffixUpdateProps, IEventMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idscategories: [],
      creatorId: '0',
      participantsId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUser1S();
    this.props.getCategories();
  }

  saveEntity = (event, errors, values) => {
    values.eventTime = convertDateTimeToServer(values.eventTime);

    if (errors.length === 0) {
      const { eventEntity } = this.props;
      const entity = {
        ...eventEntity,
        ...values,
        categories: mapIdList(values.categories)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/event-my-suffix');
  };

  render() {
    const { eventEntity, user1S, categories, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="paashoApp.event.home.createOrEditLabel">Create or edit a Event</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : eventEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="event-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="eventTimeLabel" for="eventTime">
                    Event Time
                  </Label>
                  <AvInput
                    id="event-my-suffix-eventTime"
                    type="datetime-local"
                    className="form-control"
                    name="eventTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.eventEntity.eventTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    Description
                  </Label>
                  <AvField id="event-my-suffix-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    Code
                  </Label>
                  <AvField id="event-my-suffix-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    Title
                  </Label>
                  <AvField id="event-my-suffix-title" type="text" name="title" />
                </AvGroup>
                <AvGroup>
                  <Label id="maxAgeLabel" for="maxAge">
                    Max Age
                  </Label>
                  <AvField id="event-my-suffix-maxAge" type="string" className="form-control" name="maxAge" />
                </AvGroup>
                <AvGroup>
                  <Label id="minAgeLabel" for="minAge">
                    Min Age
                  </Label>
                  <AvField id="event-my-suffix-minAge" type="string" className="form-control" name="minAge" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceTypeLabel">Price Type</Label>
                  <AvInput
                    id="event-my-suffix-priceType"
                    type="select"
                    className="form-control"
                    name="priceType"
                    value={(!isNew && eventEntity.priceType) || 'FREE'}
                  >
                    <option value="FREE">FREE</option>
                    <option value="DUTCH">DUTCH</option>
                    <option value="MONETARY">MONETARY</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="event-my-suffix-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && eventEntity.status) || 'OPEN'}
                  >
                    <option value="OPEN">OPEN</option>
                    <option value="CLOSE">CLOSE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="address">
                    Address
                  </Label>
                  <AvField id="event-my-suffix-address" type="text" name="address" />
                </AvGroup>
                <AvGroup>
                  <Label id="visitCountLabel" for="visitCount">
                    Visit Count
                  </Label>
                  <AvField id="event-my-suffix-visitCount" type="string" className="form-control" name="visitCount" />
                </AvGroup>
                <AvGroup>
                  <Label id="latitudeLabel" for="latitude">
                    Latitude
                  </Label>
                  <AvField id="event-my-suffix-latitude" type="string" className="form-control" name="latitude" />
                </AvGroup>
                <AvGroup>
                  <Label id="longitudeLabel" for="longitude">
                    Longitude
                  </Label>
                  <AvField id="event-my-suffix-longitude" type="string" className="form-control" name="longitude" />
                </AvGroup>
                <AvGroup>
                  <Label id="likesLabel" for="likes">
                    Likes
                  </Label>
                  <AvField id="event-my-suffix-likes" type="string" className="form-control" name="likes" />
                </AvGroup>
                <AvGroup>
                  <Label for="creator.id">Creator</Label>
                  <AvInput id="event-my-suffix-creator" type="select" className="form-control" name="creatorId">
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
                <AvGroup>
                  <Label for="categories">Categories</Label>
                  <AvInput
                    id="event-my-suffix-categories"
                    type="select"
                    multiple
                    className="form-control"
                    name="categories"
                    value={eventEntity.categories && eventEntity.categories.map(e => e.id)}
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
                <Button tag={Link} id="cancel-save" to="/entity/event-my-suffix" replace color="info">
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
  user1S: storeState.user1.entities,
  categories: storeState.category.entities,
  eventEntity: storeState.event.entity,
  loading: storeState.event.loading,
  updating: storeState.event.updating,
  updateSuccess: storeState.event.updateSuccess
});

const mapDispatchToProps = {
  getUser1S,
  getCategories,
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
)(EventMySuffixUpdate);
