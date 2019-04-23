package cn.com.taiji.mongoweb.daoimpl;


import cn.com.taiji.mongoweb.dao.UserDao;
import cn.com.taiji.mongoweb.model.User;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import org.springframework.data.geo.Point;

import java.util.*;

/**
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	/**
	 * 由springboot自动注入，默认配置会产生mongoTemplate这个bean
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 查找全部
	 */
	@Override
	public List<User> findAll() {

		return mongoTemplate.findAll(User.class);
	}

	/**
	 * 根据id得到对象
	 */
	@Override
	public User getUser(Integer id) {

		// mongoTemplate.geoNear()

		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), User.class);
	}

	/**
	 * 插入一个用户
	 */
	@Override
	public void insert(User user) {
		mongoTemplate.insert(user);
	}

	/**
	 * 根据id删除一个用户
	 */
	@Override
	public void remove(Integer id) {
		Criteria criteria = Criteria.where("id").is(id);  
        Query query = new Query(criteria);
		mongoTemplate.remove(query,User.class);
	}

	/**
	 * 分页查找
	 * 
	 * user代表过滤条件
	 * 
	 * pageable代表分页bean
	 */
	@Override
	public List<User> findByPage(User user, Pageable pageable) {
		Query query = new Query();
		if (user != null && user.getName() != null) {
			//模糊查询
			query = new Query(Criteria.where("name").regex("^" + user.getName()));
		}
		List<User> list = mongoTemplate.find(query.with(pageable), User.class);
		return list;
	}

	/**
	 * 根据id更新
	 */
	@Override
	public void update(User user) {
		Criteria criteria = Criteria.where("id").is(user.getId());
		Query query = new Query(criteria);
		Update update = Update.update("name", user.getName()).set("age", user.getAge());
		mongoTemplate.updateMulti(query, update, User.class);
	}

	/**
	 * 插入一个集合
	 */
	@Override
	public void insertAll(List<User> users) {
		mongoTemplate.insertAll(users);
	}


	@Override
	public List<Document> geoNear(String collection, DBObject query, com.mongodb.client.model.geojson.Point point, int limit, long maxDistance) {
		if(query==null)
			query = new BasicDBObject();

		System.out.println("withinPolygon:{}"+query.toString());
		//Bson bson = Filters.geoWithinCenter("loc",0d,0d,(double)40);
		//Bson bsonCenterSphere = Filters.geoWithinCenterSphere("loc", 110.29239, 30.2323, 120 / 6371000); // 120米除以6371000转换成弧度
		//Bson bsonCenter = Filters.geoWithinCenter("loc", 110.29239, 30.2323, 120 / 6371000); // 120米除以6371000转换成弧度
		//FindIterable findIterable = mongoTemplate.getCollection("test").find(bson);
		List<Document> result = new ArrayList<Document>();
		//MongoCursor<Document> mongoCursor = mongoTemplate.getCollection(collection).find(bson).limit(limit).iterator();

		//MongoCursor<Document> mongoCursor1 = findIterable.iterator();
		//while(mongoCursor.hasNext()){
		//	result.add(mongoCursor.next());
		//}
		return result;
	}

	@Override
	public List<Document> withinPolygon(String collection, String locationField,
										List<List<Double>> polygon, DBObject fields, DBObject query, int limit) {
		if(query==null)
			query = new BasicDBObject();

		System.out.println("withinPolygon:{}"+query.toString());
		Bson bson = Filters.and(Arrays.asList(
				Filters.geoWithinPolygon(locationField, polygon)
				/*Filters.gte("apis.count", 1)*/));
		//Bson bsonCenterSphere = Filters.geoWithinCenterSphere("loc", 110.29239, 30.2323, 120 / 6371000); // 120米除以6371000转换成弧度
		//Bson bsonCenter = Filters.geoWithinCenter("loc", 110.29239, 30.2323, 120 / 6371000); // 120米除以6371000转换成弧度
		FindIterable findIterable = mongoTemplate.getCollection("test").find(bson);
		List<Document> result = new ArrayList<Document>();
		MongoCursor<Document> mongoCursor = mongoTemplate.getCollection(collection).find(bson).limit(limit).iterator();

		MongoCursor<Document> mongoCursor1 = findIterable.iterator();
		while(mongoCursor.hasNext()){
			result.add(mongoCursor.next());
		}
		return result;
	}

	@Override
	public List<Document> withinCircle(String collection, String locationField, Point center, long redius,
			DBObject fields, DBObject query, int limit) {
		// TODO Auto-generated method stub
		if(query==null)
			query = new BasicDBObject();

		System.out.println("withinCircle:{}"+query.toString());
		Bson bson = Filters.and(Arrays.asList(
				Filters.geoWithinCenter(locationField, center.getX(), center.getY(), redius)));
		FindIterable findIterable = mongoTemplate.getCollection("test").find(bson);
		List<Document> result = new ArrayList<Document>();
		MongoCursor<Document> mongoCursor = mongoTemplate.getCollection(collection).find(bson).limit(limit).iterator();

		MongoCursor<Document> mongoCursor1 = findIterable.iterator();
		while(mongoCursor.hasNext()){
			result.add(mongoCursor.next());
		}
		return result;
	}
}
