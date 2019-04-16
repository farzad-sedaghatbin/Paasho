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
import { IUser1Paasho } from 'app/shared/model/user-1-paasho.model';
import { getEntities as getUser1S } from 'app/entities/user-1-paasho/user-1-paasho.reducer';
import { getEntity, updateEntity, createEntity, reset } from './factor-paasho.reducer';
import { IFactorPaasho } from 'app/shared/model/factor-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFactorPaashoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFactorPaashoUpdateState {
  isNew: boolean;
  eventId: string;
  userId: string;
}

export class FactorPaashoUpdate extends React.Component<IFactorPaashoUpdateProps, IFactorPaashoUpdateState> {
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
    values.completeDate = convertDateTimeToServer(values.completeDate);
    values.issueDate = convertDateTimeToServer(values.issueDate);

    if (errors.length === 0) {
      const { factorEntity } = this.props;
      const entity = {
        ...factorEntity,
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
    this.props.history.push('/entity/factor-paasho');
  };

  render() {
    const { factorEntity, events, user1S, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="paashoApp.factor.home.createOrEditLabel">Create or edit a Factor</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : factorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="factor-paasho-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    Price
                  </Label>
                  <AvField id="factor-paasho-price" type="text" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    Code
                  </Label>
                  <AvField id="factor-paasho-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="factor-paasho-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && factorEntity.status) || 'PAID'}
                  >
                    <option value="PAID">PAID</option>
                    <option value="INIT">INIT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="completeDateLabel" for="completeDate">
                    Complete Date
                  </Label>
                  <AvInput
                    id="factor-paasho-completeDate"
                    type="datetime-local"
                    className="form-control"
                    name="completeDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.factorEntity.completeDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="issueDateLabel" for="issueDate">
                    Issue Date
                  </Label>
                  <AvInput
                    id="factor-paasho-issueDate"
                    type="datetime-local"
                    className="form-control"
                    name="issueDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.factorEntity.issueDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="event.id">Event</Label>
                  <AvInput id="factor-paasho-event" type="select" className="form-control" name="eventId">
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
                  <AvInput id="factor-paasho-user" type="select" className="form-control" name="userId">
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
                <Button tag={Link} id="cancel-save" to="/entity/factor-paasho" replace color="info">
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
  factorEntity: storeState.factor.entity,
  loading: storeState.factor.loading,
  updating: storeState.factor.updating,
  updateSuccess: storeState.factor.updateSuccess
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
)(FactorPaashoUpdate);
