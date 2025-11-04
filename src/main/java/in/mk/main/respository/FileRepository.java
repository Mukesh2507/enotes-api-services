package in.mk.main.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.FilesDetails;

public interface FileRepository extends JpaRepository<FilesDetails, Integer> {

}
