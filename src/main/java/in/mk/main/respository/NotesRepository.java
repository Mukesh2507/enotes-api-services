package in.mk.main.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

}
