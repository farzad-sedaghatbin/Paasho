import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser1Paasho } from 'app/shared/model/user-1-paasho.model';
import { getEntities as getUser1S } from 'app/entities/user-1-paasho/user-1-paasho.reducer';
import { ICategoryPaasho } from 'app/shared/model/category-paasho.model';
import { getEntities as getCategories } from 'app/entities/category-paasho/category-paasho.reducer';
import { ITitles } from 'app/shared/model/titles.model';
import { getEntities as getTitles } from 'app/entities/titles/titles.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './event-paasho.reducer';
import { IEventPaasho } from 'app/shared/model/event-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEventPaashoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEventPaashoUpdateState {
  isNew: boolean;
  idsparticipants: any[];
  idscategories: any[];
  creatorId: string;
  titlesId: string;
}

export class EventPaashoUpdate extends React.Component<IEventPaashoUpdateProps, IEventPaashoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsparticipants: [],
      idscategories: [],
      creatorId: '0',
      titlesId: '0',
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
    this.props.getTitles();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    values.eventTime = convertDateTimeToServer(values.eventTime);

    if (errors.length === 0) {
      const { eventEntity } = this.props;
      const entity = {
        ...eventEntity,
        ...values,
        participants: mapIdList(values.participants),
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
    this.props.history.push('/entity/event-paasho');
  };

  render() {
    const { eventEntity, user1S, categories, titles, loading, updating } = this.props;
    const { isNew } = this.state;

    const { files, filesContentType } = eventEntity;

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
                    <AvInput id="event-paasho-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="eventTimeLabel" for="eventTime">
                    Event Time
                  </Label>
                  <AvInput
                    id="event-paasho-eventTime"
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
                  <AvField id="event-paasho-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    Code
                  </Label>
                  <AvField id="event-paasho-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    Title
                  </Label>
                  <AvField id="event-paasho-title" type="text" name="title" />
                </AvGroup>
                <AvGroup>
                  <Label id="maxAgeLabel" for="maxAge">
                    Max Age
                  </Label>
                  <AvField id="event-paasho-maxAge" type="string" className="form-control" name="maxAge" />
                </AvGroup>
                <AvGroup>
                  <Label id="minAgeLabel" for="minAge">
                    Min Age
                  </Label>
                  <AvField id="event-paasho-minAge" type="string" className="form-control" name="minAge" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceTypeLabel">Price Type</Label>
                  <AvInput
                    id="event-paasho-priceType"
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
                    id="event-paasho-status"
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
                  <AvField id="event-paasho-address" type="text" name="address" />
                </AvGroup>
                <AvGroup>
                  <Label id="visitCountLabel" for="visitCount">
                    Visit Count
                  </Label>
                  <AvField id="event-paasho-visitCount" type="string" className="form-control" name="visitCount" />
                </AvGroup>
                <AvGroup>
                  <Label id="latitudeLabel" for="latitude">
                    Latitude
                  </Label>
                  <AvField id="event-paasho-latitude" type="string" className="form-control" name="latitude" />
                </AvGroup>
                <AvGroup>
                  <Label id="longitudeLabel" for="longitude">
                    Longitude
                  </Label>
                  <AvField id="event-paasho-longitude" type="string" className="form-control" name="longitude" />
                </AvGroup>
                <AvGroup>
                  <Label id="likesLabel" for="likes">
                    Likes
                  </Label>
                  <AvField id="event-paasho-likes" type="string" className="form-control" name="likes" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="filesLabel" for="files">
                      Files
                    </Label>
                    <br />
                    {files ? (
                      <div>
                        <a onClick={openFile(filesContentType, files)}>
                          <img src={`data:${filesContentType};base64,${files}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {filesContentType}, {byteSize(files)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('files')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_files" type="file" onChange={this.onBlobChange(true, 'files')} accept="image/*" />
                    <AvInput type="hidden" name="files" value={files} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="telLabel" for="tel">
                    Tel
                  </Label>
                  <AvField id="event-paasho-tel" type="text" name="tel" />
                </AvGroup>
                <AvGroup>
                  <Label id="instagramLabel" for="instagram">
                    Instagram
                  </Label>
                  <AvField id="event-paasho-instagram" type="text" name="instagram" />
                </AvGroup>
                <AvGroup>
                  <Label id="telegramLabel" for="telegram">
                    Telegram
                  </Label>
                  <AvField id="event-paasho-telegram" type="text" name="telegram" />
                </AvGroup>
                <AvGroup>
                  <Label id="capacityLabel" for="capacity">
                    Capacity
                  </Label>
                  <AvField id="event-paasho-capacity" type="string" className="form-control" name="capacity" />
                </AvGroup>
                <AvGroup>
                  <Label id="customTitleLabel" for="customTitle">
                    Custom Title
                  </Label>
                  <AvField id="event-paasho-customTitle" type="text" name="customTitle" />
                </AvGroup>
                <AvGroup>
                  <Label for="creator.id">Creator</Label>
                  <AvInput id="event-paasho-creator" type="select" className="form-control" name="creatorId">
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
                  <Label for="user1S">Participants</Label>
                  <AvInput
                    id="event-paasho-participants"
                    type="select"
                    multiple
                    className="form-control"
                    name="participants"
                    value={eventEntity.participants && eventEntity.participants.map(e => e.id)}
                  >
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
                    id="event-paasho-categories"
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
                <AvGroup>
                  <Label for="titles.id">Titles</Label>
                  <AvInput id="event-paasho-titles" type="select" className="form-control" name="titlesId">
                    <option value="" key="0" />
                    {titles
                      ? titles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/event-paasho" replace color="info">
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
  titles: storeState.titles.entities,
  eventEntity: storeState.event.entity,
  loading: storeState.event.loading,
  updating: storeState.event.updating,
  updateSuccess: storeState.event.updateSuccess
});

const mapDispatchToProps = {
  getUser1S,
  getCategories,
  getTitles,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventPaashoUpdate);
