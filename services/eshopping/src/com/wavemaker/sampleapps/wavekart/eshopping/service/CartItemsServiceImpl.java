/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/
package com.wavemaker.sampleapps.wavekart.eshopping.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


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

import com.wavemaker.sampleapps.wavekart.eshopping.CartItems;
import com.wavemaker.sampleapps.wavekart.eshopping.CartItemsId;


/**
 * ServiceImpl object for domain model class CartItems.
 *
 * @see CartItems
 */
@Service("eshopping.CartItemsService")
public class CartItemsServiceImpl implements CartItemsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartItemsServiceImpl.class);


    @Autowired
    @Qualifier("eshopping.CartItemsDao")
    private WMGenericDao<CartItems, CartItemsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<CartItems, CartItemsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "eshoppingTransactionManager")
    @Override
	public CartItems create(CartItems cartItems) {
        LOGGER.debug("Creating a new CartItems with information: {}", cartItems);
        CartItems cartItemsCreated = this.wmGenericDao.create(cartItems);
        return cartItemsCreated;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public CartItems getById(CartItemsId cartitemsId) throws EntityNotFoundException {
        LOGGER.debug("Finding CartItems by id: {}", cartitemsId);
        CartItems cartItems = this.wmGenericDao.findById(cartitemsId);
        if (cartItems == null){
            LOGGER.debug("No CartItems found with id: {}", cartitemsId);
            throw new EntityNotFoundException(String.valueOf(cartitemsId));
        }
        return cartItems;
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public CartItems findById(CartItemsId cartitemsId) {
        LOGGER.debug("Finding CartItems by id: {}", cartitemsId);
        return this.wmGenericDao.findById(cartitemsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "eshoppingTransactionManager")
	@Override
	public CartItems update(CartItems cartItems) throws EntityNotFoundException {
        LOGGER.debug("Updating CartItems with information: {}", cartItems);
        this.wmGenericDao.update(cartItems);

        CartItemsId cartitemsId = new CartItemsId();
        cartitemsId.setCartId(cartItems.getCartId());
        cartitemsId.setProductId(cartItems.getProductId());

        return this.wmGenericDao.findById(cartitemsId);
    }

    @Transactional(value = "eshoppingTransactionManager")
	@Override
	public CartItems delete(CartItemsId cartitemsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting CartItems with id: {}", cartitemsId);
        CartItems deleted = this.wmGenericDao.findById(cartitemsId);
        if (deleted == null) {
            LOGGER.debug("No CartItems found with id: {}", cartitemsId);
            throw new EntityNotFoundException(String.valueOf(cartitemsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public Page<CartItems> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all CartItems");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<CartItems> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all CartItems");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service eshopping for table CartItems to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }



}
