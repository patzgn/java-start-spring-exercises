package com.patzgn.equipy.components.assignment;

import com.patzgn.equipy.components.inventory.asset.Asset;
import com.patzgn.equipy.components.inventory.asset.AssetRepository;
import com.patzgn.equipy.components.user.User;
import com.patzgn.equipy.components.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
class AssignmentService {

    private AssignmentRepository assignmentRepository;
    private AssetRepository assetRepository;
    private UserRepository userRepository;

    AssignmentDto createAssignment(AssignmentDto assignmentDto) {
        Optional<Assignment> activeAssignmentForAsset = assignmentRepository
                .findByAsset_IdAndEndIsNull(assignmentDto.getAssetId());
        activeAssignmentForAsset.ifPresent(a -> {
            throw new InvalidAssignmentException("To wyposażenie jest aktualnie do kogoś przypisane");
        });
        Long userId = assignmentDto.getUserId();
        Long assetId = assignmentDto.getAssetId();
        Optional<User> user = userRepository.findById(userId);
        Optional<Asset> asset = assetRepository.findById(assetId);
        Assignment assignment = new Assignment();
        assignment.setUser(user.orElseThrow(() ->
                new InvalidAssignmentException("Brak użytkownika z id: " + userId)));
        assignment.setAsset(asset.orElseThrow(() ->
                new InvalidAssignmentException("Brak wyposażenia z id: " + assetId)));
        assignment.setStart(LocalDateTime.now());
        return AssignmentMapper.toDto(assignmentRepository.save(assignment));
    }

    @Transactional
    LocalDateTime finishAssignment(Long assignmentId) {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        Assignment assignmentEntity = assignment.orElseThrow(AssignmentNotFoundException::new);
        if (assignmentEntity.getEnds() != null) {
            throw new AssignmentAlreadyFinishedException();
        } else {
            assignmentEntity.setEnds(LocalDateTime.now());
        }
        return assignmentEntity.getEnds();
    }

}
