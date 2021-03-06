/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/
package com.wavemaker.sampleapps.wavekart.eshopping.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;

import com.wavemaker.sampleapps.wavekart.eshopping.ProductInventory;

/**
 * Service object for domain model class {@link ProductInventory}.
 */
public interface ProductInventoryService {

    /**
     * Creates a new ProductInventory. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on ProductInventory if any.
     *
     * @param productInventory Details of the ProductInventory to be created; value cannot be null.
     * @return The newly created ProductInventory.
     */
	ProductInventory create(ProductInventory productInventory);


	/**
	 * Returns ProductInventory by given id if exists.
	 *
	 * @param productinventoryId The id of the ProductInventory to get; value cannot be null.
	 * @return ProductInventory associated with the given productinventoryId.
     * @throws EntityNotFoundException If no ProductInventory is found.
	 */
	ProductInventory getById(Integer productinventoryId) throws EntityNotFoundException;

    /**
	 * Find and return the ProductInventory by given id if exists, returns null otherwise.
	 *
	 * @param productinventoryId The id of the ProductInventory to get; value cannot be null.
	 * @return ProductInventory associated with the given productinventoryId.
	 */
	ProductInventory findById(Integer productinventoryId);


	/**
	 * Updates the details of an existing ProductInventory. It replaces all fields of the existing ProductInventory with the given productInventory.
	 *
     * This method overrides the input field values using Server side or database managed properties defined on ProductInventory if any.
     *
	 * @param productInventory The details of the ProductInventory to be updated; value cannot be null.
	 * @return The updated ProductInventory.
	 * @throws EntityNotFoundException if no ProductInventory is found with given input.
	 */
	ProductInventory update(ProductInventory productInventory) throws EntityNotFoundException;

    /**
	 * Deletes an existing ProductInventory with the given id.
	 *
	 * @param productinventoryId The id of the ProductInventory to be deleted; value cannot be null.
	 * @return The deleted ProductInventory.
	 * @throws EntityNotFoundException if no ProductInventory found with the given id.
	 */
	ProductInventory delete(Integer productinventoryId) throws EntityNotFoundException;

	/**
	 * Find all ProductInventories matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
	 *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
	 *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching ProductInventories.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
	 */
    @Deprecated
	Page<ProductInventory> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
	 * Find all ProductInventories matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching ProductInventories.
     *
     * @see Pageable
     * @see Page
	 */
    Page<ProductInventory> findAll(String query, Pageable pageable);

    /**
	 * Exports all ProductInventories matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
	 */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

	/**
	 * Retrieve the count of the ProductInventories in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
	 * @return The count of the ProductInventory.
	 */
	long count(String query);


}

