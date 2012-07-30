/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.chutaum.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 * 
 */


public class Util {


	
  private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private static MemcacheService keycache = MemcacheServiceFactory.getMemcacheService();

	/**
	 * Add the entity to cache and also to the datastore
	 * @param entity
	 *          : entity to be persisted
	 */
  
public  static Key persistEntity(Entity entity) {
      return datastore.put(entity);
  }

	/**
	 * Delete the entity from persistent store represented by the key
	 * Also delete it from cache
	 * 
	 * @param key
	 *          : key to delete the entity from the persistent store
	 */
  public static void deleteEntity(Key key) {
	Transaction txn = datastore.beginTransaction();
	
	try {
	  datastore.delete(key);
	  txn.commit();
	} finally {
	  if (txn.isActive()) {
	    txn.rollback();
	  } else {
		deleteFromCache(key);
	  }
    }
  }

	/**
	 * Delete the entities represented by a list of keys
	 * Delete the entitites from cache also
	 * 
	 * @param keys : keys for the entities that are to be deleted
	 */
  public static void deleteEntity(final List<Key> keys) {
    datastore.delete(new Iterable<Key>() {
    	public Iterator<Key> iterator() {
		  return keys.iterator();
	  }
	});
    deleteFromCache(keys);
  }

	/**
	 * Search and return the entity from the cache . If absent , search the
	 * datastore.
	 * 
	 * @param key
	 *          : key to find the entity
	 * @return entity
	 */
  public static Entity findEntityAndAddCache(Key key) {
	try {
	  Entity entity = getFromCache(key);
	  
	  if (entity != null) {
		return entity;
	  }
	  
	  entity= datastore.get(key);
	  //se pesquisar uma entidade coloque ela em cache � bom p user mas pode lotar o cache se for 
	  if (entity!=null) {
		   addToCache(key, entity);
	   }
	  return entity;
	   
	} catch (EntityNotFoundException e) {
	  return null;
	}
  }
  
  public static Entity findEntity(Key key) {
		try {
		  Entity entity = getFromCache(key);
		  if (entity != null) {
			return entity;
		  }
		  return datastore.get(key);				   
		} catch (EntityNotFoundException e) {
		  return null;
		}
	  }

	/***
	 * Search entities based on search criteria
	 * 
	 * @param kind
	 * @param searchBy
	 *          : Searching Criteria (Property)
	 * @param searchFor
	 *          : Searching Value (Property Value)
	 * @return List all entities of a kind from the cache or datastore (if not in
	 *         cache) with the specified properties
	 */
  public static Iterable<Entity> listEntities(String kind, String searchBy, String searchFor) {
    Query query = new Query(kind);
    if (searchFor != null && !"".equals(searchFor)) {
      query.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
    }
    PreparedQuery pq = datastore.prepare(query);
    return pq.asIterable();
  }


	/**
	 * Get the list of children from a parent key in the entity group
	 *  
	 * @param kind : the entity kind of the children that is to be searched for 
	 * @param ancestor : the parent key of the entity group where we need to search
	 * @return iterable with all children of the parent of the specified kind
	 */
  public static Iterable<Entity> listChildren(String kind, Key ancestor ) {
	  Query query = new Query(kind);
	  query.setAncestor(ancestor);
	  query.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
	  PreparedQuery pq = datastore.prepare(query);
	  return pq.asIterable();
  }



/**
 * Get the list of children from a parent key in the entity group
 *  
 * @param kind : the entity kind of the children that is to be searched for 
 * @param ancestor : the parent key of the entity group where we need to search
 * @return iterable with all children of the parent of the specified kind
 */
  public static Iterable<Entity> listChildren(String kind, Key ancestor,int sizePage, int offset ) {
		Query query = new Query(kind);
		query.setAncestor(ancestor);
		query.addSort("DateMs",SortDirection.DESCENDING);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(sizePage);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asQueryResultIterable(fetchOptions.offset(offset));
  }
  
  public static  List<Entity> listChildrenAndOrderby(String kind, Key ancestor,int sizePage, int offset, String orderBy, SortDirection sort, Boolean cache ) {
	  List<Entity> entitys = null;
	  String cacheKey = "";
	  
	  if (cache) {
		cacheKey = kind+"_"+ancestor.getId()+"_"+sizePage+"_"+offset+"_"+orderBy+"_"+sort.toString();
		entitys = getFromCacheIterable(cacheKey);
	  }	
	  
		if (entitys==null) { 
			Query query = new Query(kind);
			query.setAncestor(ancestor);
			query.addSort(orderBy,sort);
			FetchOptions fetchOptions = FetchOptions.Builder.withLimit(sizePage);
			PreparedQuery pq = datastore.prepare(query);
			entitys =  pq.asList(fetchOptions.offset(offset));

			if (cache) keycache.put(cacheKey, entitys);
			
		}
		
		return entitys;
  }

  

	/**
	 * Get the list of keys of all children for a given entity kind in a given entity group 
	 * represented by the parent key
	 * @param kind : Entity kind of the children that needs to be searched for
	 * @param ancestor : Parent key of the entity group that needs to be searched for
	 * @return an iterable with keys of children
	 */
  public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
    Query query = new Query(kind);
    query.setAncestor(ancestor).setKeysOnly();
    
    query.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN,ancestor);
    PreparedQuery pq = datastore.prepare(query);
    return pq.asIterable();
  }

	/**
	 * List the entities in JSON format
	 * 
	 * @param entities
	 *          entities to return as JSON strings
	 */
  public static String writeJSON(Iterable<Entity> entities) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    sb.append("{\"data\": [");
    for (Entity result : entities) {
      Map<String, Object> properties = result.getProperties();
      sb.append("{");
      if (result.getKey().getName() == null)
        sb.append("\"ID\" : \"" + result.getKey().getId() + "\",");
      else
        sb.append("\"ID\" : \"" + result.getKey().getName() + "\",");

      for (String key : properties.keySet()) {
        sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
      }
      sb.deleteCharAt(sb.lastIndexOf(","));
      sb.append("},");
      i++;
    }
    if (i > 0) {
      sb.deleteCharAt(sb.lastIndexOf(","));
    }
    sb.append("]}");
    return sb.toString();
  }

	/**
	 * Retrieves Parent and Child entities into JSON String
	 * 
	 * @param entities
	 *          : List of parent entities
	 * @param childKind
	 *          : Entity type for Child
	 * @param fkName
	 *          : foreign-key to the parent in the child entity
	 * @return JSON string
	 */
  public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    sb.append("{\"data\": [");
    for (Entity result : entities) {
      Map<String, Object> properties = result.getProperties();
      sb.append("{");
      if (result.getKey().getName() == null)
        sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
      else
        sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
      for (String key : properties.keySet()) {
        sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
      }
      Iterable<Entity> child = listEntities(childKind, fkName,String.valueOf(result.getKey().getId()));
      for (Entity en : child) {
        for (String key : en.getProperties().keySet()) {
          sb.append("\"" + key + "\" : \"" + en.getProperties().get(key)+ "\",");
        }
      }
      sb.deleteCharAt(sb.lastIndexOf(","));
      sb.append("},");
      i++;
    }
    if (i > 0) {
      sb.deleteCharAt(sb.lastIndexOf(","));
    }
    sb.append("]}");
    return sb.toString();
  }

	/**
	 * Adds the entity to cache
	 * 
	 * @param key
	 *          : key of the entity
	 * @param entity
	 *          : Entity being added
	 */
  public static void addToCache(Key key, Entity entity) {
    keycache.put(key, entity);
  }

	/**
	 * Delete the entity from cache
	 * 
	 * @param key : Key of the entity that needs to be deleted
	 */
  public static void deleteFromCache(Key key) {
    keycache.delete(key);
  }

	/**
	 * Delete entities based on a set of keys
	 * 
	 * @param keys : list of keys for the entities that are to be deleted
	 */
  public static void deleteFromCache(List<Key> keys) {
    keycache.deleteAll(keys);
  }

	/**
	 * Search for an entity based on key in the cache
	 * 
	 * @param key : key of the entity that is searched for
	 * @return the entity
	 */
  public static Entity getFromCache(Key key) {
    return (Entity) keycache.get(key);
  }
  
  	@SuppressWarnings("unchecked")
	public static List<Entity> getFromCacheIterable(String key) {
		    return (List<Entity>) keycache.get(key);
  	}
  

	/**
	 * Utility method to send the error back to UI
	 * 
	 * @param data
	 * @param resp
	 * @throws IOException
	 */
  public static String getErrorResponse(Exception ex) throws IOException {
    return "Error:" + ex.toString();
  }

	/**
	 * Utility method to get the datastore service in entities
	 * 
	 * @return datastore
	 */
  public static DatastoreService getDatastoreServiceInstance() {
    return datastore;
  }
}