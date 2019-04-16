import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './comment-my-suffix.reducer';
import { ICommentMySuffix } from 'app/shared/model/comment-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICommentMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CommentMySuffixDetail extends React.Component<ICommentMySuffixDetailProps> {
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
            <dd>{commentEntity.userId ? commentEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/comment-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/comment-my-suffix/${commentEntity.id}/edit`} replace color="primary">
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
)(CommentMySuffixDetail);
