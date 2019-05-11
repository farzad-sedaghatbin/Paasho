import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './event-paasho.reducer';
import { IEventPaasho } from 'app/shared/model/event-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventPaashoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EventPaashoDetail extends React.Component<IEventPaashoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { eventEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Event [<b>{eventEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="eventTime">Event Time</span>
            </dt>
            <dd>
              <TextFormat value={eventEntity.eventTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{eventEntity.description}</dd>
            <dt>
              <span id="code">Code</span>
            </dt>
            <dd>{eventEntity.code}</dd>
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{eventEntity.title}</dd>
            <dt>
              <span id="maxAge">Max Age</span>
            </dt>
            <dd>{eventEntity.maxAge}</dd>
            <dt>
              <span id="minAge">Min Age</span>
            </dt>
            <dd>{eventEntity.minAge}</dd>
            <dt>
              <span id="priceType">Price Type</span>
            </dt>
            <dd>{eventEntity.priceType}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{eventEntity.status}</dd>
            <dt>
              <span id="address">Address</span>
            </dt>
            <dd>{eventEntity.address}</dd>
            <dt>
              <span id="visitCount">Visit Count</span>
            </dt>
            <dd>{eventEntity.visitCount}</dd>
            <dt>
              <span id="latitude">Latitude</span>
            </dt>
            <dd>{eventEntity.latitude}</dd>
            <dt>
              <span id="longitude">Longitude</span>
            </dt>
            <dd>{eventEntity.longitude}</dd>
            <dt>
              <span id="likes">Likes</span>
            </dt>
            <dd>{eventEntity.likes}</dd>
            <dt>
              <span id="files">Files</span>
            </dt>
            <dd>
              {eventEntity.files ? (
                <div>
                  <a onClick={openFile(eventEntity.filesContentType, eventEntity.files)}>
                    <img src={`data:${eventEntity.filesContentType};base64,${eventEntity.files}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {eventEntity.filesContentType}, {byteSize(eventEntity.files)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="tel">Tel</span>
            </dt>
            <dd>{eventEntity.tel}</dd>
            <dt>
              <span id="instagram">Instagram</span>
            </dt>
            <dd>{eventEntity.instagram}</dd>
            <dt>
              <span id="telegram">Telegram</span>
            </dt>
            <dd>{eventEntity.telegram}</dd>
            <dt>
              <span id="capacity">Capacity</span>
            </dt>
            <dd>{eventEntity.capacity}</dd>
            <dt>
              <span id="customTitle">Custom Title</span>
            </dt>
            <dd>{eventEntity.customTitle}</dd>
            <dt>Creator</dt>
            <dd>{eventEntity.creatorId ? eventEntity.creatorId : ''}</dd>
            <dt>Participants</dt>
            <dd>
              {eventEntity.participants
                ? eventEntity.participants.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === eventEntity.participants.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Categories</dt>
            <dd>
              {eventEntity.categories
                ? eventEntity.categories.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === eventEntity.categories.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Titles</dt>
            <dd>{eventEntity.titlesId ? eventEntity.titlesId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/event-paasho" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/event-paasho/${eventEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ event }: IRootState) => ({
  eventEntity: event.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventPaashoDetail);
