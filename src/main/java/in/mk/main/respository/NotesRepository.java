package in.mk.main.respository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	Page<Notes> findByCreatedBy(Integer userId, PageRequest pageableObj);
	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);
	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, PageRequest pageableObj);
	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOfDate);

}
