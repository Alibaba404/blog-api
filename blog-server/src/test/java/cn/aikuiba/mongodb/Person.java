package cn.aikuiba.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by 蛮小满Sama at 2023/11/25 09:09
 *
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "t_person")
public class Person {

    @Id
    private String _id;

    private Long id;

    private String name;

    private Boolean sex;

    private Integer age;
}
