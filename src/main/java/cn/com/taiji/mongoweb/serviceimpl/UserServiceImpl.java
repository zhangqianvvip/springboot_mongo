package cn.com.taiji.mongoweb.serviceimpl;


import cn.com.taiji.mongoweb.dao.UserDao;
import cn.com.taiji.mongoweb.model.User;
import cn.com.taiji.mongoweb.service.UserService;
import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import org.springframework.data.geo.Point;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User getUser(Integer id) {
		return userDao.getUser(id);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void insert(User user) {
		userDao.insert(user);
	}

	@Override
	public void insertAll(List<User> users) {
		userDao.insertAll(users);
	}

	@Override
	public void remove(Integer id) {
		userDao.remove(id);
	}

	@Override
	public List<User> findByPage(User user, Pageable pageable) {
		return userDao.findByPage(user, pageable);
	}

	@Override
	public List<Document> geo(String collection, DBObject query, com.mongodb.client.model.geojson.Point point, int limit, long maxDistance) {
		 return userDao.geoNear( collection,  query,  point,  limit,  maxDistance);
	}

	@Override
	public List<Document> withinPolygon(String collection, String locationField,
										List<List<Double>> polygon, DBObject fields, DBObject query, int limit){
		return userDao.withinPolygon( collection,  locationField,  polygon,  fields,  query,  limit);
	}

	@Override
	public List<Document> withinCircle(String collection, String locationField, Point center, long redius,
			DBObject fields, DBObject query, int limit) {
		// TODO Auto-generated method stub
		return userDao.withinCircle(collection, locationField, center, redius, fields, query, limit);
	}
}
