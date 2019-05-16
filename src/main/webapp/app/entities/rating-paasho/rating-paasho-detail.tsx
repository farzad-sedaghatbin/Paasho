import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rating-paasho.reducer';
import { IRatingPaasho } from 'app/shared/model/rating-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRatingPaashoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RatingPaashoDetail extends React.Component<IRatingPaashoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ratingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Rating [<b>{ratingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="rate">Rate</span>
            </dt>
            <dd>{ratingEntity.rate}</dd>
            <dt>Event</dt>
            <dd>{ratingEntity.eventId ? ratingEntity.eventId : ''}</dd>
            <dt>User</dt>
            <dd>{ratingEntity.userLogin ? ratingEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/rating-paasho" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/rating-paasho/${ratingEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ rating }: IRootState) => ({
  ratingEntity: rating.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RatingPaashoDetail);
