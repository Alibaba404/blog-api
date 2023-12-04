package cn.aikuiba.mongodb;

import cn.aikuiba.AikuibaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by 蛮小满Sama at 2023/11/25 09:07
 *
 * @description
 */
@SpringBootTest(classes = AikuibaApplication.class)
public class MongodbTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test1() {
        // 添加到Mongodb
        mongoTemplate.save(new Person(null, 2L, "蛮吉-1", true, 122));
    }

    @Test
    public void test2() {
        mongoTemplate.findAll(Person.class).forEach(System.out::println);
        Query query = new Query(Criteria.where("id").is(1));
        Person person = mongoTemplate.findOne(query, Person.class);
        if (null != person) {
            person.setSex(false);
            person.setAge(27);
            // _id 不为空则是修改
            mongoTemplate.save(person);
        }
    }

    @Test
    public void test3() {
        // 删除
        Query query = new Query(Criteria.where("name").is("蛮吉"));
        mongoTemplate.remove(query, Person.class);

        mongoTemplate.findAll(Person.class).forEach(System.out::println);
    }


}
