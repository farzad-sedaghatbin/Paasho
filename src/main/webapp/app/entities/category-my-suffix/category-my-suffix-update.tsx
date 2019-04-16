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
import { IEventMySuffix } from 'app/shared/model/event-my-suffix.model';
import { getEntities as getEvents } from 'app/entities/event-my-suffix/event-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './category-my-suffix.reducer';
import { ICategoryMySuffix } from 'app/shared/model/category-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICategoryMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICategoryMySuffixUpdateState {
  isNew: boolean;
  idId: string;
  idId: string;
}

export class CategoryMySuffixUpdate extends React.Component<ICategoryMySuffixUpdateProps, ICategoryMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idId: '0',
      idId: '0',
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
    this.props.getEvents();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { categoryEntity } = this.props;
      const entity = {
        ...categoryEntity,
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
    this.props.history.push('/entity/category-my-suffix');
  };

  render() {
    const { categoryEntity, user1S, events, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="paashoApp.category.home.createOrEditLabel">Create or edit a Category</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : categoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="category-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="iconLabel" for="icon">
                    Icon
                  </Label>
                  <AvField id="category-my-suffix-icon" type="text" name="icon" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="category-my-suffix-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    Code
                  </Label>
                  <AvField id="category-my-suffix-code" type="text" name="code" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/category-my-suffix" replace color="info">
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
  events: storeState.event.entities,
  categoryEntity: storeState.category.entity,
  loading: storeState.category.loading,
  updating: storeState.category.updating,
  updateSuccess: storeState.category.updateSuccess
});

const mapDispatchToProps = {
  getUser1S,
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
)(CategoryMySuffixUpdate);
