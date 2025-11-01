import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import ru.Bodyaaaa.dto.AnimalDTO;
import ru.Bodyaaaa.models.Animal;
import ru.Bodyaaaa.models.Master;
import ru.Bodyaaaa.repository.AnimalRepository;
import ru.Bodyaaaa.repository.MasterRepository;
import ru.Bodyaaaa.service.AnimalService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private MasterRepository masterRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    void search_shouldReturnFilteredResults() {
        Pageable pageable = PageRequest.of(0, 10);
        Animal animal = new Animal();
        animal.setBreed("cat");

        when(animalRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(animal)));

        Page<Animal> result = animalService.search("cat", 3, "Murzik", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("cat", result.getContent().get(0).getBreed());
    }

    @Test
    void convertToDto_shouldMapCorrectly() {
        Animal animal = new Animal();
        animal.setId(1);
        animal.setName("Murzik");
        animal.setAge(3);
        animal.setBreed("cat");
        animal.setTailLength(20);

        Master master = new Master();
        master.setId(5);
        animal.setMaster(master);

        Animal friend = new Animal();
        friend.setId(2);
        animal.setFriends(List.of(friend));

        AnimalDTO dto = animalService.convertToDto(animal);

        assertEquals(1, dto.getId());
        assertEquals(5, dto.getMasterId());
        assertTrue(dto.getFriendIds().contains(2));
    }

    @Test
    void save_shouldThrowWhenMasterNotFound() {
        AnimalDTO dto = new AnimalDTO();
        dto.setMasterId(999);

        when(masterRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.save(dto));
    }
}


