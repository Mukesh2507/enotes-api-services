package in.mk.main.respository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.mk.main.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	Page<Notes> findByCreatedBy(Integer userId, PageRequest pageableObj);
	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);
	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, PageRequest pageableObj);
	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOfDate);
	@Query("""
			SELECT n FROM Notes n 
			WHERE (
			    LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    OR LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    OR LOWER(n.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			)
			AND n.isDeleted = false
			AND n.createdBy = :userId
			""")
			Page<Notes> searchNotes(String keyword, Integer userId, Pageable pageable);
	
}
