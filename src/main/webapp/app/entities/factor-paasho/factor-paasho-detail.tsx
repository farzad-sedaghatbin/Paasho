import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './factor-paasho.reducer';
import { IFactorPaasho } from 'app/shared/model/factor-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFactorPaashoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FactorPaashoDetail extends React.Component<IFactorPaashoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { factorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Factor [<b>{factorEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{factorEntity.price}</dd>
            <dt>
              <span id="code">Code</span>
            </dt>
            <dd>{factorEntity.code}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{factorEntity.status}</dd>
            <dt>
              <span id="completeDate">Complete Date</span>
            </dt>
            <dd>
              <TextFormat value={factorEntity.completeDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="issueDate">Issue Date</span>
            </dt>
            <dd>
              <TextFormat value={factorEntity.issueDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Event</dt>
            <dd>{factorEntity.eventId ? factorEntity.eventId : ''}</dd>
            <dt>User</dt>
            <dd>{factorEntity.userId ? factorEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/factor-paasho" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/factor-paasho/${factorEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ factor }: IRootState) => ({
  factorEntity: factor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FactorPaashoDetail);
