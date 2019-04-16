import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './user-1-my-suffix.reducer';
import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUser1MySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IUser1MySuffixState {
  search: string;
}

export class User1MySuffix extends React.Component<IUser1MySuffixProps, IUser1MySuffixState> {
  state: IUser1MySuffixState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.setState({ search: '' }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { user1List, match } = this.props;
    return (
      <div>
        <h2 id="user-1-my-suffix-heading">
          User 1 S
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new User 1
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput type="text" name="search" value={this.state.search} onChange={this.handleSearch} placeholder="Search" />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Last Name</th>
                <th>Gender</th>
                <th>Birth Date</th>
                <th>Favorits</th>
                <th>Id</th>
                <th>Id</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {user1List.map((user1, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${user1.id}`} color="link" size="sm">
                      {user1.id}
                    </Button>
                  </td>
                  <td>{user1.name}</td>
                  <td>{user1.lastName}</td>
                  <td>{user1.gender}</td>
                  <td>{user1.birthDate}</td>
                  <td>
                    {user1.favorits
                      ? user1.favorits.map((val, j) => (
                          <span key={j}>
                            <Link to={`category-my-suffix/${val.id}`}>{val.id}</Link>
                            {j === user1.favorits.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{user1.idId ? <Link to={`event-my-suffix/${user1.idId}`}>{user1.idId}</Link> : ''}</td>
                  <td>{user1.idId ? <Link to={`notification-my-suffix/${user1.idId}`}>{user1.idId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${user1.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${user1.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${user1.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ user1 }: IRootState) => ({
  user1List: user1.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(User1MySuffix);
