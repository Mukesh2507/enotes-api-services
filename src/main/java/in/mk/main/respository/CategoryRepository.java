package in.mk.main.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer>{

}
