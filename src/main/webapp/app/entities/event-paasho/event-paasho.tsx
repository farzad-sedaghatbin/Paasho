import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, ICrudSearchAction, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './event-paasho.reducer';
import { IEventPaasho } from 'app/shared/model/event-paasho.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IEventPaashoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IEventPaashoState extends IPaginationBaseState {
  search: string;
}

export class EventPaasho extends React.Component<IEventPaashoProps, IEventPaashoState> {
  state: IEventPaashoState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  search = () => {
    if (this.state.search) {
      this.props.reset();
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  };

  clear = () => {
    this.props.reset();
    this.setState({ search: '', activePage: 1 }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    if (search) {
      this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  render() {
    const { eventList, match } = this.props;
    return (
      <div>
        <h2 id="event-paasho-heading">
          Events
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Event
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
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('eventTime')}>
                    Event Time <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('description')}>
                    Description <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('code')}>
                    Code <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('title')}>
                    Title <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('maxAge')}>
                    Max Age <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('minAge')}>
                    Min Age <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('priceType')}>
                    Price Type <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('status')}>
                    Status <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('address')}>
                    Address <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('visitCount')}>
                    Visit Count <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('latitude')}>
                    Latitude <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('longitude')}>
                    Longitude <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('likes')}>
                    Likes <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('files')}>
                    Files <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('tel')}>
                    Tel <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('instagram')}>
                    Instagram <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('telegram')}>
                    Telegram <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('capacity')}>
                    Capacity <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('customTitle')}>
                    Custom Title <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('dateString')}>
                    Date String <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('timeString')}>
                    Time String <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Titles <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Creator <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {eventList.map((event, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${event.id}`} color="link" size="sm">
                        {event.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={event.eventTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{event.description}</td>
                    <td>{event.code}</td>
                    <td>{event.title}</td>
                    <td>{event.maxAge}</td>
                    <td>{event.minAge}</td>
                    <td>{event.priceType}</td>
                    <td>{event.status}</td>
                    <td>{event.address}</td>
                    <td>{event.visitCount}</td>
                    <td>{event.latitude}</td>
                    <td>{event.longitude}</td>
                    <td>{event.likes}</td>
                    <td>
                      {event.files ? (
                        <div>
                          <a onClick={openFile(event.filesContentType, event.files)}>
                            <img src={`data:${event.filesContentType};base64,${event.files}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {event.filesContentType}, {byteSize(event.files)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{event.tel}</td>
                    <td>{event.instagram}</td>
                    <td>{event.telegram}</td>
                    <td>{event.capacity}</td>
                    <td>{event.customTitle}</td>
                    <td>{event.dateString}</td>
                    <td>{event.timeString}</td>
                    <td>{event.titlesId ? <Link to={`titles/${event.titlesId}`}>{event.titlesId}</Link> : ''}</td>
                    <td>{event.creatorLogin ? event.creatorLogin : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${event.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${event.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${event.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>

                        <Button tag={Link} to={`${match.url}/${event.id}/approved`} color="green" size="sm">
                          <FontAwesomeIcon icon="check" /> <span className="d-none d-md-inline">Approved</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ event }: IRootState) => ({
  eventList: event.entities,
  totalItems: event.totalItems,
  links: event.links,
  entity: event.entity,
  updateSuccess: event.updateSuccess
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventPaasho);
