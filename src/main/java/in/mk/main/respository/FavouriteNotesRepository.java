package in.mk.main.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.FavouritNotes;

public interface FavouriteNotesRepository extends JpaRepository<FavouritNotes, Integer> {

	List<FavouritNotes> findByUserId(int userId);

}
