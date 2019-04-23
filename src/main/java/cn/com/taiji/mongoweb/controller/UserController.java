package cn.com.taiji.mongoweb.controller;


import cn.com.taiji.mongoweb.model.User;
import cn.com.taiji.mongoweb.service.UserService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

/**
 * 入口
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;


	@RequestMapping("/get/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUser(id);
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		userService.remove(id);
		return "delete sucess";
	}

	@RequestMapping("/add")
	public String insert() {
		User user =new User(16, ""+16, 16);
		userService.insert(user);
		return "sucess";
	}

	@RequestMapping("/insert")
	public String insertAll() {
		List<User> list = new ArrayList<>();
		for (int i = 10; i < 15; i++) {
			list.add(new User(i, "" + i, i));
		}
		userService.insertAll(list);
		return "sucess";
	}
	
	@RequestMapping("/find/all")
	public List<User> find(){
		return userService.findAll();
	}
	
	@RequestMapping("/find/{start}")
	public List<User> findByPage(@PathVariable int start,User user){
		Pageable pageable=new PageRequest(start, 2);
		return userService.findByPage(user, pageable);
	} 
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable int id){
		User user =new User(id, ""+1, 1);
		userService.update(user);
		return "sucess";
	}

	@RequestMapping("/geo")
	public List<Document> geo(){
		DBObject query = new BasicDBObject();
		Position Position = new Position((double)50,(double)50);
		com.mongodb.client.model.geojson.Point point = new com.mongodb.client.model.geojson.Point(Position);
		int limit = 5;
		Long maxDistance = 50L; // 米
		List<Document> list = userService.geo("location", query, point, limit, maxDistance);
		for(Document obj : list)
			System.out.println(obj);
		return list;
	}
       @RequestMapping("/withinCircle")
       public List<Document> withinCircle(){
    	   DBObject query = new BasicDBObject();
   		DBObject field = new BasicDBObject();
   		Point point=new Point(50, 50);
   	   Long redius=50L;
   		int limit = 3;
   		List<Document> listfinal = userService.withinCircle("location",  "loc", point, redius, field, query, limit);
		for(Document obj : listfinal)
			System.out.println(obj);
		return listfinal;
       }
	
	@RequestMapping("/withinPolygon")
	public List<Document> withinPolygon(){
		DBObject query = new BasicDBObject();
		DBObject field = new BasicDBObject();
		int limit = 3;//
		List<List<Double>> listDouble = new ArrayList<>(5);
		List<Double> p1 = new ArrayList<>();
		List<Double> p2 = new ArrayList<>();
		List<Double> p3 = new ArrayList<>();
		List<Double> p4 = new ArrayList<>();
		List<Double> p5 = new ArrayList<>();
		p1.add(0d);
		p1.add(0d);
		p2.add(0d);
		p2.add(30d);
		p3.add(30d);
		p3.add(30d);
		p4.add(30d);
		p4.add(0d);
		p5.add(0d);
		p5.add(0d);
		listDouble.add(p1);
		listDouble.add(p2);
		listDouble.add(p3);
		listDouble.add(p4);
		listDouble.add(p5);

		List<Document> listfinal = userService.withinPolygon("location",  "loc",
				listDouble,  field, query, limit);
		for(Document obj : listfinal)
			System.out.println(obj);
		return listfinal;
	}

}
