import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IEventPaasho } from 'app/shared/model/event-paasho.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, ApprovedEntity } from './event-paasho.reducer';

export interface IEventPaashoApproveDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EventPaashoApproveDialog extends React.Component<IEventPaashoApproveDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmApprove = event => {
    this.props.ApprovedEntity(this.props.eventEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { eventEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Confirm Approve operation</ModalHeader>
        <ModalBody id="paashoApp.event.approve.question">Are you sure you want to Approve this Event?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Cancel
          </Button>
          <Button id="jhi-confirm-approve-event" color="danger" onClick={this.confirmApprove}>
            <FontAwesomeIcon icon="trash" />
            &nbsp; Approve
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ event }: IRootState) => ({
  eventEntity: event.entity
});

const mapDispatchToProps = { getEntity, ApprovedEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventPaashoApproveDialog);
