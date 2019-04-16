import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './category-paasho.reducer';
import { ICategoryPaasho } from 'app/shared/model/category-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICategoryPaashoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CategoryPaashoDetail extends React.Component<ICategoryPaashoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { categoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Category [<b>{categoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="icon">Icon</span>
            </dt>
            <dd>{categoryEntity.icon}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{categoryEntity.name}</dd>
            <dt>
              <span id="code">Code</span>
            </dt>
            <dd>{categoryEntity.code}</dd>
          </dl>
          <Button tag={Link} to="/entity/category-paasho" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/category-paasho/${categoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ category }: IRootState) => ({
  categoryEntity: category.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CategoryPaashoDetail);
