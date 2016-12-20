package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Tag;

/**
 * Created by 1234 on 16.12.2016 г..
 */
public interface TagRepository extends JpaRepository<Tag,Integer> {

    Tag findByName(String name);
}
