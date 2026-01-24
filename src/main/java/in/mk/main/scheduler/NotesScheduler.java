package in.mk.main.scheduler;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import in.mk.main.entity.Notes;
import in.mk.main.respository.NotesRepository;

@Component
public class NotesScheduler {

	@Autowired
	 private NotesRepository notesRepo;
	
	
	
	
@Scheduled(cron="0 0 0 * *  ?")	
public void	 deleteNotesScheduler() {
	
LocalDateTime cutOfDate=LocalDateTime.now().minusDays(7);
List<Notes> deletedNotes =notesRepo.findAllByIsDeletedAndDeletedOnBefore(true,cutOfDate);
notesRepo.deleteAll(deletedNotes);
}
	
	

}
