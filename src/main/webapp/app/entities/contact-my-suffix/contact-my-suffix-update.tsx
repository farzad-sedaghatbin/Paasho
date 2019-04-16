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
import { getEntity, updateEntity, createEntity, reset } from './contact-my-suffix.reducer';
import { IContactMySuffix } from 'app/shared/model/contact-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IContactMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IContactMySuffixUpdateState {
  isNew: boolean;
  userId: string;
}

export class ContactMySuffixUpdate extends React.Component<IContactMySuffixUpdateProps, IContactMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getUser1S();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { contactEntity } = this.props;
      const entity = {
        ...contactEntity,
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
    this.props.history.push('/entity/contact-my-suffix');
  };

  render() {
    const { contactEntity, user1S, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="paashoApp.contact.home.createOrEditLabel">Create or edit a Contact</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : contactEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="contact-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="typeLabel">Type</Label>
                  <AvInput
                    id="contact-my-suffix-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && contactEntity.type) || 'TELEGRAM'}
                  >
                    <option value="TELEGRAM">TELEGRAM</option>
                    <option value="INSTAGRAM">INSTAGRAM</option>
                    <option value="WHATSAPP">WHATSAPP</option>
                    <option value="EMAIL">EMAIL</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="valueLabel" for="value">
                    Value
                  </Label>
                  <AvField id="contact-my-suffix-value" type="text" name="value" />
                </AvGroup>
                <AvGroup>
                  <Label for="user.id">User</Label>
                  <AvInput id="contact-my-suffix-user" type="select" className="form-control" name="userId">
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
                <Button tag={Link} id="cancel-save" to="/entity/contact-my-suffix" replace color="info">
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
  contactEntity: storeState.contact.entity,
  loading: storeState.contact.loading,
  updating: storeState.contact.updating,
  updateSuccess: storeState.contact.updateSuccess
});

const mapDispatchToProps = {
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
)(ContactMySuffixUpdate);
