package cn.com.taiji.mongoweb.dao;

import cn.com.taiji.mongoweb.model.User;
import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;


import java.util.List;

/**
 *
 */
public interface UserDao {

	List<User> findAll();

	User getUser(Integer id);

	void update(User user);

	void insert(User user);

	void insertAll(List<User> users);

	void remove(Integer id);

	List<User> findByPage(User user, Pageable pageable);

	List<Document> geoNear(String collection, DBObject query, com.mongodb.client.model.geojson.Point point, int limit, long maxDistance);

	List<Document> withinPolygon(String collection, String locationField,
								 List<List<Double>> polygon, DBObject fields, DBObject query, int limit);
 
	List<Document> withinCircle(String collection, String locationField,
			Point center,long redius, DBObject fields, DBObject query, int limit);
	
	

	}
