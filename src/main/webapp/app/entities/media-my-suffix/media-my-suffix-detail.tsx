import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './media-my-suffix.reducer';
import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MediaMySuffixDetail extends React.Component<IMediaMySuffixDetailProps> {
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
            <dt>Media</dt>
            <dd>{mediaEntity.mediaId ? mediaEntity.mediaId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/media-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/media-my-suffix/${mediaEntity.id}/edit`} replace color="primary">
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
)(MediaMySuffixDetail);
