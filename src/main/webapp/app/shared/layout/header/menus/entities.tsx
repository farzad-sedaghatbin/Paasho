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
    <DropdownItem tag={Link} to="/entity/event-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Event Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/user-1-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;User 1 Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/contact-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Contact Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/category-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Category Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/notification-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Notification Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/setting-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Setting Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/factor-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Factor Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/comment-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Comment Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/media-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Media Paasho
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/rating-paasho">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Rating Paasho
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
