import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './contact-my-suffix.reducer';
import { IContactMySuffix } from 'app/shared/model/contact-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IContactMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ContactMySuffixDetail extends React.Component<IContactMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { contactEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Contact [<b>{contactEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{contactEntity.type}</dd>
            <dt>
              <span id="value">Value</span>
            </dt>
            <dd>{contactEntity.value}</dd>
            <dt>User</dt>
            <dd>{contactEntity.userId ? contactEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/contact-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/contact-my-suffix/${contactEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ contact }: IRootState) => ({
  contactEntity: contact.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ContactMySuffixDetail);
