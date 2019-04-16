import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEventPaasho } from 'app/shared/model/event-paasho.model';
import { getEntities as getEvents } from 'app/entities/event-paasho/event-paasho.reducer';
import { getEntity, updateEntity, createEntity, reset } from './media-paasho.reducer';
import { IMediaPaasho } from 'app/shared/model/media-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMediaPaashoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMediaPaashoUpdateState {
  isNew: boolean;
  eventId: string;
}

export class MediaPaashoUpdate extends React.Component<IMediaPaashoUpdateProps, IMediaPaashoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      eventId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mediaEntity } = this.props;
      const entity = {
        ...mediaEntity,
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
    this.props.history.push('/entity/media-paasho');
  };

  render() {
    const { mediaEntity, events, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="paashoApp.media.home.createOrEditLabel">Create or edit a Media</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mediaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="media-paasho-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="pathLabel" for="path">
                    Path
                  </Label>
                  <AvField id="media-paasho-path" type="text" name="path" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel">Type</Label>
                  <AvInput
                    id="media-paasho-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && mediaEntity.type) || 'VIDEO'}
                  >
                    <option value="VIDEO">VIDEO</option>
                    <option value="PHOTO">PHOTO</option>
                    <option value="AUDIO">AUDIO</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="event.id">Event</Label>
                  <AvInput id="media-paasho-event" type="select" className="form-control" name="eventId">
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
                <Button tag={Link} id="cancel-save" to="/entity/media-paasho" replace color="info">
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
  mediaEntity: storeState.media.entity,
  loading: storeState.media.loading,
  updating: storeState.media.updating,
  updateSuccess: storeState.media.updateSuccess
});

const mapDispatchToProps = {
  getEvents,
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
)(MediaPaashoUpdate);
