import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './comment-paasho.reducer';
import { ICommentPaasho } from 'app/shared/model/comment-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICommentPaashoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CommentPaashoDetail extends React.Component<ICommentPaashoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { commentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Comment [<b>{commentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{commentEntity.description}</dd>
            <dt>User</dt>
            <dd>{commentEntity.userLogin ? commentEntity.userLogin : ''}</dd>
            <dt>Event</dt>
            <dd>{commentEntity.eventId ? commentEntity.eventId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/comment-paasho" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/comment-paasho/${commentEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ comment }: IRootState) => ({
  commentEntity: comment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CommentPaashoDetail);
