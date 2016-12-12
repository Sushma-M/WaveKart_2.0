/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/

package com.wavemaker.sampleapps.wavekart.eshopping.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.model.CustomQuery;
import com.wavemaker.runtime.data.exception.QueryParameterMismatchException;

public interface EshoppingQueryExecutorService {
	int executeDeleteCartItems_LoggedUser(  java.lang.Integer LoggedUserCartId) throws QueryParameterMismatchException;
    Page<Object> executeGet_CartIdByUserid(Pageable pageable) throws QueryParameterMismatchException;
    Page<Object> executeGet_CartItems_id(Pageable pageable, java.lang.Integer Cid) throws QueryParameterMismatchException;
    Page<Object> executeGetCount_OrderLineItems(Pageable pageable, java.lang.Integer OrderId) throws QueryParameterMismatchException;
    Page<Object> executeMyCart_Items_Count(Pageable pageable) throws QueryParameterMismatchException;
    Page<Object> executeTotal_OrderedQty(Pageable pageable, java.lang.Integer orderid) throws QueryParameterMismatchException;
    Page<Object> executeTotalItemsPrice_CartProd(Pageable pageable, java.lang.Integer cartId) throws QueryParameterMismatchException;
    Page<Object> executeTotalPrice_CartProd(Pageable pageable, java.lang.Integer cartid) throws QueryParameterMismatchException;
    Page<Object> executeTotalPrice_Orders(Pageable pageable, java.lang.Integer Oid) throws QueryParameterMismatchException;

	
	Page<Object> executeWMCustomQuerySelect(CustomQuery query, Pageable pageable) ;

	int executeWMCustomQueryUpdate(CustomQuery query) ;
}

