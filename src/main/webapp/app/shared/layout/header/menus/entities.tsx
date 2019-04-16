import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/event-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Event My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/user-1-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;User 1 My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/contact-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Contact My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/category-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Category My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/notification-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Notification My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/setting-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Setting My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/factor-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Factor My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/comment-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Comment My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/media-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Media My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/rating-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Rating My Suffix
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
