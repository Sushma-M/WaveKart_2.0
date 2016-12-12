/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/
package com.wavemaker.sampleapps.wavekart.eshopping.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;

import com.wavemaker.sampleapps.wavekart.eshopping.CartDetails;
import com.wavemaker.sampleapps.wavekart.eshopping.Orders;
import com.wavemaker.sampleapps.wavekart.eshopping.UserAddressDetails;
import com.wavemaker.sampleapps.wavekart.eshopping.UserDetails;


/**
 * ServiceImpl object for domain model class UserDetails.
 *
 * @see UserDetails
 */
@Service("eshopping.UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
	@Qualifier("eshopping.OrdersService")
	private OrdersService ordersService;

    @Autowired
	@Qualifier("eshopping.UserAddressDetailsService")
	private UserAddressDetailsService userAddressDetailsService;

    @Autowired
	@Qualifier("eshopping.CartDetailsService")
	private CartDetailsService cartDetailsService;

    @Autowired
    @Qualifier("eshopping.UserDetailsDao")
    private WMGenericDao<UserDetails, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<UserDetails, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "eshoppingTransactionManager")
    @Override
	public UserDetails create(UserDetails userDetails) {
        LOGGER.debug("Creating a new UserDetails with information: {}", userDetails);
        UserDetails userDetailsCreated = this.wmGenericDao.create(userDetails);
        if(userDetailsCreated.getCartDetails() != null) {
            CartDetails cartDetails = userDetailsCreated.getCartDetails();
            LOGGER.debug("Creating a new child CartDetails with information: {}", cartDetails);
            cartDetails.setUserDetails(userDetailsCreated);
            cartDetailsService.create(cartDetails);
        }

        if(userDetailsCreated.getUserAddressDetailses() != null) {
            for(UserAddressDetails userAddressDetailse : userDetailsCreated.getUserAddressDetailses()) {
                userAddressDetailse.setUserDetails(userDetailsCreated);
                LOGGER.debug("Creating a new child UserAddressDetails with information: {}", userAddressDetailse);
                userAddressDetailsService.create(userAddressDetailse);
            }
        }

        if(userDetailsCreated.getOrderses() != null) {
            for(Orders orderse : userDetailsCreated.getOrderses()) {
                orderse.setUserDetails(userDetailsCreated);
                LOGGER.debug("Creating a new child Orders with information: {}", orderse);
                ordersService.create(orderse);
            }
        }
        return userDetailsCreated;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public UserDetails getById(Integer userdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Finding UserDetails by id: {}", userdetailsId);
        UserDetails userDetails = this.wmGenericDao.findById(userdetailsId);
        if (userDetails == null){
            LOGGER.debug("No UserDetails found with id: {}", userdetailsId);
            throw new EntityNotFoundException(String.valueOf(userdetailsId));
        }
        return userDetails;
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public UserDetails findById(Integer userdetailsId) {
        LOGGER.debug("Finding UserDetails by id: {}", userdetailsId);
        return this.wmGenericDao.findById(userdetailsId);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public UserDetails getByEmailAddress(String emailAddress) {
        Map<String, Object> emailAddressMap = new HashMap<>();
        emailAddressMap.put("emailAddress", emailAddress);

        LOGGER.debug("Finding UserDetails by unique keys: {}", emailAddressMap);
        UserDetails userDetails = this.wmGenericDao.findByUniqueKey(emailAddressMap);

        if (userDetails == null){
            LOGGER.debug("No UserDetails found with given unique key values: {}", emailAddressMap);
            throw new EntityNotFoundException(String.valueOf(emailAddressMap));
        }

        return userDetails;
    }

	@Transactional(rollbackFor = EntityNotFoundException.class, value = "eshoppingTransactionManager")
	@Override
	public UserDetails update(UserDetails userDetails) throws EntityNotFoundException {
        LOGGER.debug("Updating UserDetails with information: {}", userDetails);
        this.wmGenericDao.update(userDetails);

        Integer userdetailsId = userDetails.getUserId();

        return this.wmGenericDao.findById(userdetailsId);
    }

    @Transactional(value = "eshoppingTransactionManager")
	@Override
	public UserDetails delete(Integer userdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting UserDetails with id: {}", userdetailsId);
        UserDetails deleted = this.wmGenericDao.findById(userdetailsId);
        if (deleted == null) {
            LOGGER.debug("No UserDetails found with id: {}", userdetailsId);
            throw new EntityNotFoundException(String.valueOf(userdetailsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public Page<UserDetails> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all UserDetails");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<UserDetails> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all UserDetails");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service eshopping for table UserDetails to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<UserAddressDetails> findAssociatedUserAddressDetailses(Integer userId, Pageable pageable) {
        LOGGER.debug("Fetching all associated userAddressDetailses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("userDetails.userId = '" + userId + "'");

        return userAddressDetailsService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<Orders> findAssociatedOrderses(Integer userId, Pageable pageable) {
        LOGGER.debug("Fetching all associated orderses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("userDetails.userId = '" + userId + "'");

        return ordersService.findAll(queryBuilder.toString(), pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service OrdersService instance
	 */
	protected void setOrdersService(OrdersService service) {
        this.ordersService = service;
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service UserAddressDetailsService instance
	 */
	protected void setUserAddressDetailsService(UserAddressDetailsService service) {
        this.userAddressDetailsService = service;
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CartDetailsService instance
	 */
	protected void setCartDetailsService(CartDetailsService service) {
        this.cartDetailsService = service;
    }

}
