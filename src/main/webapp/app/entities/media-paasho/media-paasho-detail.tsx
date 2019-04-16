import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './media-paasho.reducer';
import { IMediaPaasho } from 'app/shared/model/media-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaPaashoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MediaPaashoDetail extends React.Component<IMediaPaashoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mediaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Media [<b>{mediaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="path">Path</span>
            </dt>
            <dd>{mediaEntity.path}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{mediaEntity.type}</dd>
            <dt>Event</dt>
            <dd>{mediaEntity.eventId ? mediaEntity.eventId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/media-paasho" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/media-paasho/${mediaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ media }: IRootState) => ({
  mediaEntity: media.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MediaPaashoDetail);
